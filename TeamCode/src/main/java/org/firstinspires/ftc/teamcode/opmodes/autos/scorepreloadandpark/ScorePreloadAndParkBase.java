package org.firstinspires.ftc.teamcode.opmodes.autos.scorepreloadandpark;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Robot;

public class ScorePreloadAndParkBase extends OpMode {

    Robot robot = new Robot();

    int stage = 1;
    final double TILE = 24;

    double NET_ZONE_X = 0.0; // CHANGE_ME!!
    double NET_ZONE_Y = 0.0; // CHANGE_ME!!
    double NET_ZONE_YAW_RAD = 0;
    double PARK_X = 0.0; // CHANGE_ME!!
    double PARK_Y = 0.0; // CHANGE_ME!!
    double PARK_YAW_RAD = 0;

    public final int HIGH_BASKET_EXTENSION = 28;
    public final int RETRACTED_EXTENSION = 0;

    final double ARM_EXTENSION_RANGE = 1.0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.manipulatorArm.toggleManipulatorState();
    }

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

        robot.updatePosition();

        Position position = robot.getPosition();
        YawPitchRollAngles angles = robot.getOrientation();
        telemetry.addData("Robot says Position", position.toString());
        telemetry.addData("Robot says Orientation", angles.toString());
        telemetry.addData("Camera says Live", robot.camera.isLive());
        telemetry.addData("Robot says Arm Extension", robot.manipulatorArm.getCurrentArmExtension());
        telemetry.addData("Robot says Arm Rotation", robot.manipulatorArm.getCurrentArmRotation());

        switch (stage) {
            case 1:
                if (robot.mecanumDrive.driveToPosition(NET_ZONE_X, NET_ZONE_Y, NET_ZONE_YAW_RAD, position, angles))
                    stage++;
                break;
            case 2:
                robot.manipulatorArm.setTargetArmExtension(HIGH_BASKET_EXTENSION);
                if (manipulatorArmWithinArmExtension(HIGH_BASKET_EXTENSION))
                    stage++;
                break;
            case 3:
                robot.manipulatorArm.toggleManipulatorState();
                stage++;
                break;
            case 4:
                robot.manipulatorArm.setTargetArmExtension(RETRACTED_EXTENSION);
                if (manipulatorArmWithinArmExtension(RETRACTED_EXTENSION))
                    stage++;
                break;
            case 5:
                if (robot.mecanumDrive.driveToPosition(PARK_X, PARK_Y, PARK_YAW_RAD, position, angles))
                    stage++;
                break;
        }

        //TODO: DO DIS
        //plans:
        //wait a little bit to allow alliance to go
        //go left until near basket zone
        //turn to drop piece
        //drop piece
        //go to hang zone
    }

    public boolean manipulatorArmWithinArmExtension(int target) {
        return (Math.abs(robot.manipulatorArm.getCurrentArmExtension()-target) < ARM_EXTENSION_RANGE);
    }
}
