package cn.edu.ncepu.sa.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private static Map<String, BufferedImage> map = new HashMap<>();

    public static BufferedImage get(String key) {
        return map.get(key);
    }

    public static BufferedImage load(String key, String path) {
        BufferedImage buff = readImage(path);
        map.put(key, buff);
        return buff;
    }

    public static BufferedImage readImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            return null;
        }
    }


    static {
        load("tank_blue", "assets/tank/bodyu1.png");
        load("tank_red", "assets/tank/bodyu2.png");
        load("turret_blue", "assets/tank/turret1_1.png");
        load("turret_red", "assets/tank/turret2_1.png");
        load("shot", "assets/tank/ammo2.png");
    }
}
