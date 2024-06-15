package MVC.Model;

public enum Directions {
    UP(0),
    DOWN(180),
    LEFT(270),
    RIGHT(90);

    private final double angleValue;

    Directions(double angleValue) {
        this.angleValue = angleValue;
    }

    public double getAngleValue() {
        return angleValue;
    }
}
