package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.classes.MoveableArm;

public class ManipulatorArm extends MoveableArm {
    public String rotationMotorName = "manipulatorRotationMotor";

    public boolean manipulatorClosed = false;

    public DcMotor rotationMotor;

    public final int HIGH_BASKET_EXTENSION = 38;
    public final int RETRACTED = 0;

    // TODO: Add LOW_BASKET_EXTENSION
    Servo manipulator;
    Servo wrist;

    @Override
    public void init(HardwareMap hardwareMap) {
        maxExtension = 40;
        minExtension = 0;

        maxRotation = 100;
        minRotation = -45;

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

    public void setTargetArmRotation(int targetRotation) {
        if (targetRotation > minRotation && targetRotation < maxRotation){
            stopped = false;
            rotationMotor.setTargetPosition(targetRotation * 27);
            rotationMotor.setPower(speed);
            rotationMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            targetArmRotation = rotationMotor.getTargetPosition();
        }
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
}
