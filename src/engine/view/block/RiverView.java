package engine.view.block;

import mvc.View;
import service.ImageCache;

import java.awt.*;

public class RiverView extends View {
	public RiverView(double x, double y) {
		super(x, y, 90, 0);
		this.width = this.height = 64;
	}

	public void draw(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2.create();

		g.translate(this.x, this.y);
		g.drawImage(ImageCache.get("block_river"), 0, 0, this.width, this.height, null);
	}
}