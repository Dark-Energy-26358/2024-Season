package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public double getMagnitude() {
        return Math.hypot(this.x, this.y);
    }

    public Vector scaleToMagnitude(double target) {
        double multiplier = target/this.getMagnitude();
        return new Vector(this.x*multiplier, this.y*multiplier);
    }

    public Vector normalize(double height) {
        double push = CalculateForce.calculateForce(this.getMagnitude(), height);
        return this.scaleToMagnitude(push);
    }
}
