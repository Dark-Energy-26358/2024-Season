package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class Pathfinding {
    private static double constrain(double num) {
        return Math.max(Math.min(num, 1), -1);
    }

    private static Vector pushAway(double xMovement, double yMovement, double robotX, double robotY) {
        Obstacle[] obstacles = FindObstacles.findObstacles();
        Vector direction = new Vector(constrain(xMovement), constrain(yMovement));
        for (Obstacle obstacle: obstacles) {
            direction = direction.add(obstacle.relativeVector(robotX, robotY).normalize(obstacle.height)); // probably change the height depending on other stuff
        }

        return new Vector(constrain(direction.x), constrain(direction.y));
    }

    public static Vector pathfind(Position robotPosition, double targetX, double targetY) {
        return pushAway(lerp(targetX-robotPosition.x,0), lerp(targetY-robotPosition.y, 0), robotPosition.x, robotPosition.y);
    }

    private static double lerp(double a, double b)
    {
        return (a * (1.0 - b/a)) + (b * b/a);
    }
}
