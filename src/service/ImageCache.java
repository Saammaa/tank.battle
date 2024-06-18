package service;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageCache {
	private static final Map<String, BufferedImage> map = new HashMap<>();

	public static BufferedImage get(String key) {
		return map.get(key);
	}

	public static void load(String key, String path) {
		BufferedImage buff = read(path);
		map.put(key, buff);
	}

	public static BufferedImage read(String path) {
		try {
			InputStream inputStream = ImageCache.class.getClassLoader().getResourceAsStream(path);

			assert inputStream != null;
			return ImageIO.read(inputStream);
		} catch (Exception e) {
			return null;
		}
	}

	static {
		load("tank_blue",	"Assets/tank_body_blue.png" );
		load("tank_red",	"Assets/tank_body_red.png" );
		load("turret_blue", "Assets/tank_turret_blue.png" );
		load("turret_red",	"Assets/tank_turret_red.png" );
		load("shot",		"Assets/ammo.png" );

		load("block_river","Assets/blocks/river.png");
	}
}