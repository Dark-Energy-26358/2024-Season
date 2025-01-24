package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;


@Autonomous
public class DriveRight extends OpMode {
    int loops = 0;
    Robot robot = new Robot();
    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        loops ++;
        if ( loops <= 5000){
            robot.mecanumDrive.drive(0,0.1,0);
        }





















































































    }
}
