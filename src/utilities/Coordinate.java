package utilities;

public class Coordinate {
    /**
     * 计算两点之间的角度。
     */
    public static double getDirectionBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2
    ) {
        double len_x = x2 - x1;
        double len_y = y2 - y1;

        double radian = Math.atan2(len_y, len_x);
        return radian * 180 / Math.PI;
    }
}