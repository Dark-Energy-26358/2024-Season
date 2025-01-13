package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.classes.MoveableArm;

public class StageOneAscentArms extends MoveableArm {

    @Override
    public void init(HardwareMap hardwareMap) {
        extensionMotorName = "stageOneExtensionMotor";

        super.init(hardwareMap);
    }
}
