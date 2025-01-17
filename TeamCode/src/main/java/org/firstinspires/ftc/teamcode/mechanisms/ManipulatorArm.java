package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.classes.MoveableArm;

public class ManipulatorArm extends MoveableArm {
    public String rotationMotorName = "manipulatorRotationMotor";
    public int manipulatorPort = 1;

    public boolean manipulatorClosed = false;


    DcMotor rotationMotor;
    Servo manipulator;

    @Override
    public void init(HardwareMap hardwareMap) {
        extensionMotorName = "manipulatorExtensionMotor";

        rotationMotor = hardwareMap.dcMotor.get(rotationMotorName);

        rotationMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rotationMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotationMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        super.init(hardwareMap);
    }

    @Override
    public void stop() {
        rotationMotor.setPower(0);
        super.stop();
    }

    @Override
    public void setTargetArmRotation(int targetRotation) {
        stopped = false;
        rotationMotor.setTargetPosition(targetRotation*27);
        rotationMotor.setPower(0.5);
        rotationMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetArmRotation = rotationMotor.getTargetPosition();
    }

    @Override
    public double getTargetArmRotation() {
        return targetArmRotation/26.66666667;
    }

    @Override
    public double getCurrentArmRotation() {
        currentArmRotation = rotationMotor.getCurrentPosition();
        return currentArmRotation/26.66666667;
        //2400 : 90
    }

    public void toggleManipulatorState(){
        if (!manipulatorClosed){
            manipulator.close();
        }else {
            manipulator.setPosition(10);
        }
    }
    //TODO setTargetManipulatorWristPosition
    //TODO setTargetManipulatorElbowPosition
}
