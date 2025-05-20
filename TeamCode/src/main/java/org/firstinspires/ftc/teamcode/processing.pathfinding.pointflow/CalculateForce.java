package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

public class CalculateForce {

    public static double calculateForce(double distance, double height) {
        if (distance >= 48) {
            return 0;
        }
        return (height/(distance + 1));
    }
}
