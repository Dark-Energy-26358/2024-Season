package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    private DcMotor shooterFlywheel1;
    private DcMotor shooterFlywheel2;

    private Servo aimingServo;

    private Servo liftingPaddle;

    private static final double ballLiftedPosition = 0.5; //todo calibrate this

    public void init(HardwareMap hardwareMap){
        shooterFlywheel1 = hardwareMap.dcMotor.get("shooter1");
        shooterFlywheel2 = hardwareMap.dcMotor.get("shooter2");

        shooterFlywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        aimingServo = hardwareMap.servo.get("aim");

        liftingPaddle = hardwareMap.servo.get("liftingPaddle");
    }
    public void shoot(double aim, double speed){
        //aim is servo ticks
        //speed is the speed to spin the launching motors

        aimingServo.setPosition(aim);

        shooterFlywheel1.setPower(speed);
        shooterFlywheel2.setPower(speed);
        liftBall();
    }
    private void liftBall(){
        liftingPaddle.setPosition(ballLiftedPosition);
    }
    public void lowerPaddle(){
        liftingPaddle.setPosition(0);
    }
}
