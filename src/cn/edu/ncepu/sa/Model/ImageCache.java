package cn.edu.ncepu.sa.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片缓存类，将图片缓存到内存中，降低IO占用
 * 静态类设计，作为外部共享资源直接使用
 */
public class ImageCache {

    /**
     * 哈希存储，检索快
     */
    private static Map<String, BufferedImage> map = new HashMap<>();

    /**
     * 获取图片资源
     *
     * @param key 关键字
     * @return 图片
     */
    public static BufferedImage get(String key) {
        return map.get(key);
    }

    /**
     * 加载资源
     *
     * @param key  key
     * @param path 文件路径
     * @return 图片
     */
    public static BufferedImage load(String key, String path) {
        BufferedImage buff = readImage(path);
        map.put(key, buff);
        return buff;
    }

    /**
     * 从文件读取文件
     *
     * @param path 文件路径
     * @return 图片
     */
    public static BufferedImage readImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加载图片资源
     */
    static {
        load("tank_blue", "assets/tank/bodyu1.png");
        load("tank_red", "assets/tank/bodyu2.png");
        load("turret_blue", "assets/tank/turret1_1.png");
        load("turret_red", "assets/tank/turret2_1.png");
        load("shot", "assets/tank/ammo2.png");
    }
}
