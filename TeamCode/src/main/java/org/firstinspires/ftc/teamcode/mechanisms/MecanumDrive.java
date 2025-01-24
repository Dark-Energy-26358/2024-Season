package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

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

    public void moveFieldRelative(double forward, double right, double rotate, double yaw) {
        double rYaw = Math.toRadians(yaw);
        drive(forward*Math.sin(rYaw), right*Math.cos(rYaw), rotate);
    }
    public boolean driveToPosition(double x, double y, int yaw, Position currentPosition, YawPitchRollAngles currentAngles) {
        int forward = 0;
        int right = 0;
        int rotate = 0;
        boolean onSpot = true;
        if (Math.abs(y-currentPosition.y) < 3) {
            if (y < currentPosition.y)
                forward = 1;
            else
                forward = -1;
            onSpot = false;
        }
        if (Math.abs(x-currentPosition.x) < 3) {
            if (x < currentPosition.x)
                right = 1;
            else
                right = -1;
            onSpot = false;
        }
        if (Math.abs(yaw-currentAngles.getYaw()) < 5) {
            rotate = 1;
            onSpot = false;
        }
        moveFieldRelative(forward, right, rotate, yaw);
        return onSpot;
    }
}
