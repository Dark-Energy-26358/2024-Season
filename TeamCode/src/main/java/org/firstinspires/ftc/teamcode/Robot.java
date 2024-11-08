package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.ChinUpArm;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

public class Robot {
    public MecanumDrive mecanumDrive = new MecanumDrive();
    public ChinUpArm chinUpArm = new ChinUpArm();

    public void init(HardwareMap hardwareMap){
        mecanumDrive.init(hardwareMap);
        chinUpArm.init(hardwareMap);
    }
}

