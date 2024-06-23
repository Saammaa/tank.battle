package engine.entity.block;

import mvc.Entity;
import engine.view.block.RiverView;

public class River extends Entity<RiverView> {
	public River(double x, double y) {
		this.blockType = "river";
		this.view = new RiverView(x, y);
	}
}
