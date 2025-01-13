package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.classes.MoveableArm;

public class ManipulatorArm extends MoveableArm {
    public String rotationMotorName = "manipulatorRotationMotor";

    DcMotor rotationMotor;

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
        rotationMotor.setTargetPosition(targetRotation*116);
        rotationMotor.setPower(0.5);
        rotationMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetArmRotation = rotationMotor.getTargetPosition();
    }

    @Override
    public double getTargetArmRotation() {
        return targetArmRotation/1.5;
    }

    @Override
    public double getCurrentArmRotation() {
        currentArmRotation = rotationMotor.getCurrentPosition();
        return currentArmRotation/1.5;
    }
    //TODO setTargetManipulatorWristPosition
    //TODO setTargetManipulatorElbowPosition
}
