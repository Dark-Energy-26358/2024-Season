package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

public class Obstacle {
    double x;
    double y;
    double radius;

    double height;

    public Obstacle(double x, double y, double radius, double height) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.height = height;
    }

    public Vector relativeVector(double robotX, double robotY) { // TODO: come from closest point on the circle
        return new Vector(this.x - robotX, this.y - robotY);
    }
}
