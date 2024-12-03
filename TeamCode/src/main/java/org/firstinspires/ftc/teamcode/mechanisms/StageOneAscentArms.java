package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class StageOneAscentArms{

    private DcMotor motor;
    private Servo leftServo;
    private Servo rightServo;

    final String motorName = "stageOneAscentMotor";

    public void init(HardwareMap hardwareMap) {
        motor = hardwareMap.dcMotor.get(motorName);
        leftServo = hardwareMap.get(Servo.class, "stageOneLeftServo");
        rightServo = hardwareMap.get(Servo.class, "stageOneRightServo");

        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void run(float motorPower, float servoPower) {
        if (motorPower != 0) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPower(motorPower);
        }
        leftServo.setPosition(leftServo.getPosition()+servoPower/10);
        rightServo.setPosition(rightServo.getPosition()+servoPower/10);

        telemetry.addData(motorName, motor.getCurrentPosition());
        telemetry.addData(motorName + " left servo", leftServo.getPosition());
        telemetry.addData(motorName + " right servo", rightServo.getPosition());
    }

    //27.5 in = 3200 tick(s)
    public void setTargetArmExtension(int targetExtension) {
        motor.setTargetPosition(targetExtension*116);
        motor.setPower(0.5);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
