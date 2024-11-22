package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.classes.MoveableArm;

public class ManipulatorArm extends MoveableArm {

    @Override
    public void init(HardwareMap hardwareMap) {
        extensionMotorName = "manipulatorExtensionMotor";
        rotationMotorName = "manipulatorRotationMotor";
        super.init(hardwareMap);
    }
    //TODO setTargetManipulatorWristPosition
    //TODO setTargetManipulatorElbowPosition
}
