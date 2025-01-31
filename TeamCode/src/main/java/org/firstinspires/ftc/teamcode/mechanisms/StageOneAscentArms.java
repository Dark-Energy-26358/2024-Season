package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.classes.MoveableArm;

public class StageOneAscentArms extends MoveableArm {

    Servo rotationServo1;
    Servo rotationServo2;

    @Override
    public void init(HardwareMap hardwareMap) {
        maxExtension = 13;
        minExtension = 0;

        maxRotation = 1;
        minRotation = -1;

        extensionMotorName = "stageOneExtensionMotor";
        rotationServo1 = hardwareMap.servo.get("stageOneServo1");
        rotationServo2 = hardwareMap.servo.get("stageOneServo2");

        super.init(hardwareMap);
    }

    public double getTargetArmRotation() {
        return (rotationServo1.getPosition() + rotationServo2.getPosition()) / 2;
    }

    public void setTargetArmRotation(double targetRotation) {
        if (targetRotation > minRotation && targetRotation < maxRotation) {
            rotationServo1.setPosition(targetRotation);
            rotationServo2.setPosition(targetRotation);
        }
    }

    public double getCurrentArmRotation() {
        return (rotationServo1.getPosition() + rotationServo2.getPosition()) / 2;
    }
}
