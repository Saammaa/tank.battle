package engine.entity.block;

import mvc.Entity;
import engine.view.block.GrassView;

public class Grass extends Entity<GrassView> {
	public Grass(double x, double y) {
		this.blockType = "grass";
		this.view = new GrassView(x, y);
	}
}
