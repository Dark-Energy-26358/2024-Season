package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class Pathfinding {
    private static Vector pushAway(double xMovement, double yMovement, double robotX, double robotY) {
        Obstacle[] obstacles = FindObstacles.findObstacles();
        Vector direction = new Vector(xMovement, yMovement);
        for (Obstacle obstacle: obstacles) {
            direction = direction.add(obstacle.relativeVector(robotX, robotY).normalize(obstacle.height)); // probably change the height depending on other stuff
        }

        return new Vector(Math.max(Math.min(direction.x, 1), -1), Math.max(Math.min(direction.y, 1), -1));
    }

    public static Vector pathfind(Position robotPosition, double targetX, double targetY) {
        return pushAway(robotPosition.x-targetX, robotPosition.x-targetY, robotPosition.x, robotPosition.y);
    }
}
