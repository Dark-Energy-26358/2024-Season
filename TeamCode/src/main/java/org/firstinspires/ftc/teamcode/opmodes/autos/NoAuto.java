package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;


@Autonomous(name = "No Auto", preselectTeleOp = "TeleOp", group = "Red/Blue")
public class NoAuto extends OpMode {
    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        robot.updatePosition();

        telemetry.addData("Robot Position", robot.getPosition());
        telemetry.addData("Robot Rotation", robot.getOrientation());
        telemetry.addData("Camera Live", robot.camera.isLive());
    }
}