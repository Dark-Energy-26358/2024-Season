package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

public class Obstacle {
    double x1;
    double y1;
    double x2;
    double y2;

    double height;

    public Obstacle(double x1, double y1,double x2, double y2, double height) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.height = height;
    }

    public Vector relativeVector(double robotX, double robotY) {
        return new Vector(Math.max(Math.min(Math.max(x1,x2),robotX),Math.min(x1,x2)), Math.max(Math.min(Math.max(y1,y2),robotY),Math.min(y1,y2)));
    }
}
