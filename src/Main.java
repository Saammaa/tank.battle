import MVC.Model.Battleground;
import MVC.Renderer.Content;
import MVC.Controller;

public class Main {
	public static void main(String[] args) {
		Battleground battle = new Battleground();
		Content content = new Content(battle);
		Controller Controller = new Controller();

		Controller.start(content, battle);
	}
}