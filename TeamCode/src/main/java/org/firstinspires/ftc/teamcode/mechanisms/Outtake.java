package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    private DcMotor shooterFlywheel1;
    private DcMotor shooterFlywheel2;

    private Servo aimingServo;

    private Servo fork;

    // Configuration constants
    private static final double forkLiftedPosition = 0.3;
    private static final double forkRetractedPosition = 1.0;

    private static final double aimLiftedPosition = 1;
    private static final double aimRetractedPosition = 0;

    public void init(HardwareMap hardwareMap){
        shooterFlywheel1 = hardwareMap.dcMotor.get("shooterFlywheel1");
        shooterFlywheel2 = hardwareMap.dcMotor.get("shooterFlywheel2");

        shooterFlywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        aimingServo = hardwareMap.servo.get("aim");

        fork = hardwareMap.servo.get("liftingPaddle");

//        fork.setPosition(forkRetractedPosition);
//        aimingServo.setPosition(aimRetractedPosition);
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

    public void smartAim(){
        //todo
        return;
    }

    public double getAim(){
        return aimingServo.getPosition();
    }

    public void liftBall(){
        fork.setPosition(forkLiftedPosition);
    }

    public void lowerBall(){
        fork.setPosition(forkRetractedPosition);}
}
