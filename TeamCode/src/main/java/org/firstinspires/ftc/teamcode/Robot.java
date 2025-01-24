package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.StageOneAscentArms;
import org.firstinspires.ftc.teamcode.mechanisms.ManipulatorArm;
import org.firstinspires.ftc.teamcode.mechanisms.StageTwoAscentArms;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

public class Robot {
    public MecanumDrive mecanumDrive = new MecanumDrive();
    public ManipulatorArm manipulatorArm = new ManipulatorArm();
//    public StageTwoAscentArms stageTwoAscentArms = new StageTwoAscentArms();
    public StageOneAscentArms stageOneAscentArms = new StageOneAscentArms();

    public void init(HardwareMap hardwareMap){
        mecanumDrive.init(hardwareMap);
        manipulatorArm.init(hardwareMap);
        //stageTwoAscentArms.init(hardwareMap);
        stageOneAscentArms.init(hardwareMap);
    }
}

