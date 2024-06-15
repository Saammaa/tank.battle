import MVC.Model.Battle;
import MVC.Renderer.Content;
import MVC.Controller;

public class Main {
	public static void main(String[] args) {
		Battle battle = new Battle();
		Content content = new Content(battle);
		Controller Controller = new Controller();

		Controller.start(content, battle);
	}
}