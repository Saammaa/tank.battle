package cn.edu.ncepu.sa.utils;

import cn.edu.ncepu.sa.Model.Element;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

    /**
     * 旋转图片为指定角度
     *
     * @param bufferedimage 目标图像
     * @param degree        旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 变更图像为指定大小
     *
     * @param bufferedimage 目标图像
     * @param w             宽
     * @param h             高
     * @return
     */
    public static BufferedImage resizeImage(final BufferedImage bufferedimage,
                                            final int w, final int h) {
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage
                .getWidth(), bufferedimage.getHeight(), null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 水平翻转图像
     *
     * @param bufferedimage 目标图像
     * @return
     */
    public static BufferedImage flipImage(final BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, bufferedimage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(bufferedimage, 0, 0, w, h, w, 0, 0, h, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 计算两点之间角度
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double ppDir(double x1, double y1, double x2, double y2) {
        double len_x = x2 - x1;
        double len_y = y2 - y1;
        double radian = Math.atan2(len_y, len_x);//弧度
        return radian * 180 / Math.PI;//角度

    }
}
