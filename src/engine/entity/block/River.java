package engine.entity.block;

import mvc.Entity;
import engine.view.block.RiverView;

public class River extends Entity<RiverView> {
	public River(double x, double y) {
		this.type = "river";
		this.view = new RiverView(x, y);
	}
}
