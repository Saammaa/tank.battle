package cn.edu.ncepu.sa.Model;

/**
 * 我方坦克移动方向枚举，注意用法
 */
public enum Directions {
    UP("UP", 0), DOWN("DOWN", 180), LEFT("LEFT", 270), RIGHT("RIGHT", 90);
    private String name;
    private double angleValue;

    Directions(String name, double angleValue) {
        this.name = name;
        this.angleValue = angleValue;
    }


    public String getName() {
        return name;
    }

    public double getAngleValue() {
        return angleValue;
    }
}
