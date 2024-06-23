package service.network;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.LinkedList;

public class Server extends Thread{
	private QueueConnection connection = null;

	private final String host;

	private final int port;

	private final String user;

	private final String password;

	private boolean toWork = false;

	java.util.Queue<TextMessage> msgList = new LinkedList<>();

	public Server(String host, int port, String user, String password) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	public Boolean OpenConn() {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					user, password, "tcp://"+host+":"+ port
			);
			
			connection = factory.createQueueConnection();
			return true;
		} catch (Exception e) {
			connection = null;
			return false;
		}
	}

	public Boolean SendAMsgByQueue(String desQueueName, String message, String ReplyTo) {
		return true;
	}

	public Boolean SendAMsg(String desQueueName, String msg, String ReplyTo) {
		if (null == connection) {
			if (!OpenConn()) return false;
		}

		try {
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(desQueueName);
			QueueSender sender = session.createSender(queue);
			TextMessage message = session.createTextMessage();

			message.setText(msg);
			
			Destination destination = session.createQueue(ReplyTo);
			message.setJMSReplyTo(destination);
			
			sender.send(message);
			
			sender.close();
			session.close();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();

			if (null != connection) {
				try {
					connection.close();
					connection = null;
				} catch (JMSException ee) {
					ee.printStackTrace();
				}
			}

			return false;
		}

	}

	public void startWork() {
		msgList.clear();
		toWork  = true;

		try {
			if (null == connection) OpenConn();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		this.start();
	}
	
	public void StopWork(){
		if (null != connection) {
			try {
				connection.close();
				connection = null;
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		toWork = false;
	}

	@Override
	public void run() {
		while (toWork) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}