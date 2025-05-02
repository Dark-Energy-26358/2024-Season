package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

public class FindObstacles {
    static final Obstacle[] fieldObstacles = {new Obstacle(0, 0, 48*Math.sqrt(2), 1)};

    public static Obstacle[] findObstacles() {
        return fieldObstacles; // We can add more stuff here
    }
}
