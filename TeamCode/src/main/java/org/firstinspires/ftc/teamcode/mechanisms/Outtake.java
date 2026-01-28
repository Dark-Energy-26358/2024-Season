package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    private DcMotor shooterFlywheel1;
    private DcMotor shooterFlywheel2;

    private Servo aimingServo;

    private Servo liftingPaddle;

    private static final double ballLiftedPosition = 0.3;
    private static final double ballRetractedPosition = 1;

    public double launchSpeed = 1;

    public void init(HardwareMap hardwareMap){
        shooterFlywheel1 = hardwareMap.dcMotor.get("shooterFlywheel1");
        shooterFlywheel2 = hardwareMap.dcMotor.get("shooterFlywheel2");

        shooterFlywheel1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterFlywheel1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shooterFlywheel2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterFlywheel2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shooterFlywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        aimingServo = hardwareMap.servo.get("aim");

        liftingPaddle = hardwareMap.servo.get("liftingPaddle");

        liftingPaddle.setPosition(ballRetractedPosition);
    }

    public void spinUp(){
        //speed is the speed to spin the launching motors
        shooterFlywheel1.setPower(launchSpeed);
        shooterFlywheel2.setPower(launchSpeed);
    }

    public void spinDown(){
        shooterFlywheel2.setPower(0);
        shooterFlywheel1.setPower(0);
    }

    public void aim(double angle, double speed){
        //aim is servo ticks
        aimingServo.setPosition(angle);//needs something to convert degrees into servo ticks
        launchSpeed = speed;
    }

    public double getAimAngle(){
        return aimingServo.getPosition();
    }

    public double getAimSpeed(){
        return launchSpeed;
    }

    public void liftBall(){
        liftingPaddle.setPosition(ballLiftedPosition);
    }

    public void lowerBall(){liftingPaddle.setPosition(ballRetractedPosition);}

    public void waitForLaunch(){
        int previousPositionFlywheel1 = shooterFlywheel1.getCurrentPosition();
        int positionFlywheel1 = shooterFlywheel1.getCurrentPosition();
        int previousSpeedFlywheel1 = 0;
        int speedFlywheel1 = 0;

        int previousPositionFlywheel2 = shooterFlywheel2.getCurrentPosition();
        int positionFlywheel2 = shooterFlywheel2.getCurrentPosition();
        int previousSpeedFlywheel2 = 0;
        int speedFlywheel2 = 0;

        while (speedFlywheel1 > previousSpeedFlywheel1 - 16 && speedFlywheel2 > previousSpeedFlywheel2 - 16){
            previousSpeedFlywheel1 = speedFlywheel1;
            speedFlywheel1 = positionFlywheel1 - previousPositionFlywheel1;
            previousPositionFlywheel1 = positionFlywheel1;
            positionFlywheel1 = shooterFlywheel1.getCurrentPosition();

            previousSpeedFlywheel2 = speedFlywheel2;
            speedFlywheel2 = positionFlywheel2 - previousPositionFlywheel2;
            previousPositionFlywheel2 = positionFlywheel2;
            positionFlywheel2 = shooterFlywheel2.getCurrentPosition();
        }
    }

    public int getRawEncoder(){
        return shooterFlywheel1.getCurrentPosition();
    }
}

