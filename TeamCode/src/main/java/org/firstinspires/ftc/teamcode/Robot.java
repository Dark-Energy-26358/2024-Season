package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

public class Robot {
    public MecanumDrive mecanumDrive = new MecanumDrive();

    public void init(HardwareMap hardwareMap){
        mecanumDrive.init(hardwareMap);
    }
}

