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

    public void init(HardwareMap hardwareMap){
        shooterFlywheel1 = hardwareMap.dcMotor.get("shooterFlywheel1");
        shooterFlywheel2 = hardwareMap.dcMotor.get("shooterFlywheel2");

        shooterFlywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        aimingServo = hardwareMap.servo.get("aim");

        liftingPaddle = hardwareMap.servo.get("liftingPaddle");
    }

    public void spinUp(double speed){
        //speed is the speed to spin the launching motors
        shooterFlywheel1.setPower(speed);
        shooterFlywheel2.setPower(speed);
    }

    public void spinDown(){
        shooterFlywheel2.setPower(0);
        shooterFlywheel1.setPower(0);
    }

    public void aim(double aim){
        //aim is servo ticks
        aimingServo.setPosition(aim);
    }

    public double getAim(){
        return aimingServo.getPosition();
    }

    public void liftBall(){
        liftingPaddle.setPosition(ballLiftedPosition);
    }

    public void lowerBall(){liftingPaddle.setPosition(1);}
}
