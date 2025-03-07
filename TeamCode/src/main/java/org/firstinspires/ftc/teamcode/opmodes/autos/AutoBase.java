package org.firstinspires.ftc.teamcode.opmodes.autos;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Robot;

public class AutoBase extends OpMode {
    protected Robot robot = new Robot();

    protected Position position;
    protected YawPitchRollAngles angles;


    @Override
    public void init() {
        robot.init(hardwareMap);

        robot.manipulatorArm.toggleManipulatorState();

        position = robot.getPosition();
        angles = robot.getOrientation();
    }

    @Override
    public void loop() {
        // USING GLOBAL TO BE IDEMPOTENT
        if (!robot.globals.getManipArmAccurate()) {
            robot.manipulatorArm.setTargetArmRotation(40);
            if (robot.manipulatorArm.getCurrentArmRotation() >= 40) {
                robot.manipulatorArm.rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.manipulatorArm.setTargetArmRotation(0);
                robot.globals.setManipArmAccurate(true);
            }
        }
        // END -- THIS IS IDEMPOTENT and CAN BE RUN MORE THAN ONCE PER MATCH

        if (abs(robot.getPosition().x) < 12 && abs(robot.getPosition().y) < 12) {
            robot.mecanumDrive.drive(0,0,0.1);
        }

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
