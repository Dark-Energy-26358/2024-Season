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

    public double launchSpeed = 1;

    public void init(HardwareMap hardwareMap){
        shooterFlywheel1 = hardwareMap.dcMotor.get("shooterFlywheel1");
        shooterFlywheel2 = hardwareMap.dcMotor.get("shooterFlywheel2");

        shooterFlywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        aimingServo = hardwareMap.servo.get("aim");

        liftingPaddle = hardwareMap.servo.get("liftingPaddle");
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

    public void lowerBall(){liftingPaddle.setPosition(1);}

    public void waitForLaunch(){
        int previousPosition = shooterFlywheel1.getCurrentPosition();
        int position = shooterFlywheel1.getCurrentPosition();
        int previousSpeed = 0;
        int speed = 0;
        while (speed > previousSpeed - 100){
            previousSpeed = speed;
            speed = position - previousPosition;
            previousPosition = position;
            position = shooterFlywheel1.getCurrentPosition();
        }
    }
}

