import MVC.Controller.Battle;
import MVC.View.Content;
import MVC.Model.BattleData;

public class Main {
	public static void main(String[] args) {
		BattleData battleData	= new BattleData();
		Content content			= new Content(battleData);
		Battle Battle			= new Battle();

		Battle.start(content, battleData);
	}
}