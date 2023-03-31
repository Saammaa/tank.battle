package cn.edu.ncepu.sa.Model;

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
