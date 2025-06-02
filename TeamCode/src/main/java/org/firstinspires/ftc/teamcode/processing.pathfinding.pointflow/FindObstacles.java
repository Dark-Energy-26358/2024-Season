package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

public class FindObstacles {
    static final Obstacle[] fieldObstacles = {
            // center
            new Obstacle( 24, 24, -24, -24,7),
            // walls
            new Obstacle(72,72,72,-72,7),
            new Obstacle(72,72,-72,72,7),
            new Obstacle(-72,-72,72,-72,7),
            new Obstacle(-72,-72,-72,72,7)
    };

    public static Obstacle[] findObstacles() {
        return fieldObstacles; // We can add more stuff here
    }
}
