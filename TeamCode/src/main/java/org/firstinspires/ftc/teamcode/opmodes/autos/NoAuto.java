package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;



@Autonomous(name = "No Auto", preselectTeleOp = "TeleOp", group = "Red/Blue")
public class NoAuto extends AutoBase {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        robot.updatePosition();

        position = robot.getPosition();
        angles = robot.getOrientation();

        telemetry.addData("Robot says Position", position.toString());
        telemetry.addData("Robot says Orientation", angles.toString());
        telemetry.addData("Camera says Live", robot.camera.isLive());
        telemetry.addData("Robot says Arm Extension", robot.manipulatorArm.getCurrentArmExtension());
        telemetry.addData("Robot says Arm Rotation", robot.manipulatorArm.getCurrentArmRotation());
    }
}