package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.classes.MoveableArm;

public class ManipulatorArm extends MoveableArm {
    public String rotationMotorName = "manipulatorRotationMotor";

    public boolean manipulatorClosed = false;

    public DcMotor rotationMotor;
    Servo manipulator;
    Servo wrist;

    @Override
    public void init(HardwareMap hardwareMap) {
        manipulator = hardwareMap.servo.get("manipulator");
        manipulator.resetDeviceConfigurationForOpMode();

        wrist = hardwareMap.servo.get("manipulatorWrist");
        wrist.resetDeviceConfigurationForOpMode();



        extensionMotorName = "manipulatorExtensionMotor";

        rotationMotor = hardwareMap.dcMotor.get(rotationMotorName);

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

    public void setTargetArmRotation(double targetRotation) {
        stopped = false;
        rotationMotor.setTargetPosition((int) (targetRotation*27));
        rotationMotor.setPower(0.5);
        rotationMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetArmRotation = rotationMotor.getTargetPosition();
    }

    @Override
    public double getTargetArmRotation() {
        targetArmRotation = rotationMotor.getTargetPosition();
        return targetArmRotation/27;
    }

    @Override
    public double getCurrentArmRotation() {
        currentArmRotation = rotationMotor.getCurrentPosition();
        return currentArmRotation/27;
        //2400 : 90
    }

    public void toggleManipulatorState(){
        if (!manipulatorClosed){
            // closing manipulator
            manipulator.setPosition(0.95);
            manipulatorClosed = true;
        } else {
            //opening manipulator
            manipulator.setPosition(0.75);
            manipulatorClosed = false;
        }
    }
    public void setTargetManipulatorWristPosition(double degrees){
        wrist.setPosition(degrees);
    }

    public double getTargetManipulatorWristPosition(){
        return wrist.getPosition();
    }
}
