package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class MecanumDrive {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    public void init(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        double maxSpeed = 1;
        maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backRightPower));

        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;
        backLeftPower /= maxSpeed;
        backRightPower /= maxSpeed;

        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);
    }

    public void drive(double forward, double right, double rotate) {
        double frontLeftPower = forward + right - rotate;
        double frontRightPower = forward - right + rotate;
        double backLeftPower = forward - right - rotate;
        double backRightPower = forward + right + rotate;

        setPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }

    public void moveFieldRelative(double x, double y, double rotate, double yawRads)
    { // In normal trig, 0 deg is right, but here, 0 deg is up
        double forward = x * Math.sin(yawRads) + y * Math.cos(yawRads);
        double right = x * Math.cos(yawRads) + y * Math.sin(yawRads);
        drive(forward, right, rotate);
    }
    public boolean driveToPosition(double targetX, double targetY, int targetYawRads, Position currentPosition, YawPitchRollAngles currentAngles) {
        final int X_BUFFER = 5;
        final int Y_BUFFER = 5;
        final int TURN_BUFFER = 5;
        final int X_SLOW_DOWN = 10;
        final int Y_SLOW_DOWN = 10;
        final double TURN_SLOW_DOWN = Math.PI*2;

        double driveX = 0;
        double driveY = 0;
        double rotate = 0;
        boolean onSpot = true;

        // Check and set right movement proportionally
        double xDifference = targetX - currentPosition.x;
        if (Math.abs(xDifference) >= X_BUFFER) { // Move if outside threshold
            driveX = xDifference / X_SLOW_DOWN; // Proportional control
            onSpot = false;
        }

        // Check and set forward movement proportionally
        double yDifference = targetY - currentPosition.y;
        if (Math.abs(yDifference) >= Y_BUFFER) { // Move if outside threshold
            driveY = yDifference / Y_SLOW_DOWN; // Proportional control
            onSpot = false;
        }

        // Check and set rotation proportionally
        double yawDifference = targetYawRads - currentAngles.getYaw(AngleUnit.RADIANS);
        if (Math.abs(yawDifference) >= TURN_BUFFER) { // Rotate if outside threshold
            rotate = yawDifference / TURN_SLOW_DOWN; // Proportional control
            onSpot = false;
        }

        // Move the robot
        moveFieldRelative(driveX, driveY, rotate, currentAngles.getYaw(AngleUnit.RADIANS));
        return onSpot;
    }
}
