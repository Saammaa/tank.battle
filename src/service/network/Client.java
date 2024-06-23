package service.network;

import java.util.LinkedList;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Client extends Thread {
	private QueueConnection connection = null;

	private final String host;

	private final int port;

	private final String user;

	private final String password;

	private final String queueName;

	Boolean toWork = false;

	QueueSession session;

	QueueReceiver receiver;

	javax.jms.Queue queue;

	public java.util.Queue<TextMessage> msgList = new LinkedList<>();

	public Client(String host, int port, String user, String password, String queue) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.queueName = queue;
	}

	public void connect() {
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					user, password, "tcp://" + host + ":" + port
			);
			connection = factory.createQueueConnection();
		} catch (Exception e) {
			e.printStackTrace();
			connection = null;
		}
	}

	public void startListen() {
		toWork = true;

		try {
			if (null == connection) connect();

			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
			receiver = session.createReceiver(queue);

			receiver.setMessageListener(message -> {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					msgList.offer(textMessage);

				}
			});

			connection.start();

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			msgList.clear();
		} catch (JMSException e1) {
			e1.printStackTrace();
		}

		this.start();
	}

	public void stopListen() {
		toWork = false;
	}

	@Override
	public void run() {
		while (toWork) {
			try { sleep(100); } catch (Exception e) {}
		}

		if (connection != null) {
			try {
				connection.stop();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			connection = null;
		}
	}
}