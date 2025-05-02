package org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow;

public class CalculateForce {

    public static double calculateForce(double distance, double height) {
        return (height/(distance + 1));
    }
}
