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

        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        double maxSpeed = 0.5;
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

    public void moveFieldRelative(double forward, double right, double rotate, double yawRads)
    {
        double vx = forward * Math.sin(yawRads) + right * Math.cos(yawRads);
        double vy = -forward * Math.cos(yawRads) + right * Math.sin(yawRads);
        drive(vy, vx, rotate);
    }
    public boolean driveToPosition(double x, double y, int yaw, Position currentPosition, YawPitchRollAngles currentAngles) {
        double forward = 0;
        double right = 0;
        double rotate = 0;
        boolean onSpot = true;

        // Check and set forward movement proportionally
        if (Math.abs(y - currentPosition.y) >= 5) { // Move if outside threshold
            forward = Math.max(-1, Math.min(1, (y - currentPosition.y) / 10)); // Proportional control
            onSpot = false;
        }

        // Check and set right movement proportionally
        if (Math.abs(x - currentPosition.x) >= 5) { // Move if outside threshold
            right = Math.max(-1, Math.min(1, (x - currentPosition.x) / 10)); // Proportional control
            onSpot = false;
        }

        // Check and set rotation proportionally
        double yawDifference = yaw - currentAngles.getYaw(AngleUnit.DEGREES);
        if (Math.abs(yawDifference) >= 5) { // Rotate if outside threshold
            rotate = Math.max(-1, Math.min(1, yawDifference / 30)); // Proportional control
            onSpot = false;
        }

        // Move the robot
        moveFieldRelative(forward, right, rotate, currentAngles.getYaw(AngleUnit.RADIANS));
        return onSpot;
    }
}
