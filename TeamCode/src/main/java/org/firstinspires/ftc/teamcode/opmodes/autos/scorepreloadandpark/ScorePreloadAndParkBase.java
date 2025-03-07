package org.firstinspires.ftc.teamcode.opmodes.autos.scorepreloadandpark;

import org.firstinspires.ftc.teamcode.opmodes.autos.AutoBase;

public class ScorePreloadAndParkBase extends AutoBase {

    public final int HIGH_BASKET_EXTENSION = 25;
    public final int HIGH_BASKET_ROTATION = 20;
    public final int RETRACTED_EXTENSION = 0;
    final double TILE = 24;
    final double ARM_EXTENSION_ACCURACY_RANGE = 2.0;
    final double ARM_ROTATION_ACCURACY_RANGE = 0.2;

    int stage = 1;
    double NET_ZONE_X = 0.0; // CHANGE_ME!!
    double NET_ZONE_Y = 0.0; // CHANGE_ME!!
    double NET_ZONE_YAW_RAD = 0;
    double PARK_X = 0.0; // CHANGE_ME!!
    double PARK_Y = 0.0; // CHANGE_ME!!
    double PARK_YAW_RAD = 0;

    @Override
    public void init() {
        super.init();
    }

    public void loop() {
        switch (stage) {
            case 1:
                if (robot.mecanumDrive.driveToPosition(NET_ZONE_X, NET_ZONE_Y, NET_ZONE_YAW_RAD, position, angles))
                    stage++;
                break;
            case 2:
                robot.manipulatorArm.setTargetArmExtension(HIGH_BASKET_EXTENSION);
                if (manipulatorArmWithinArmExtension(HIGH_BASKET_EXTENSION)) {
                    stage++;
                }
                break;
            case 3:
                robot.manipulatorArm.setTargetArmRotation(HIGH_BASKET_ROTATION);
                if (manipulatorArmWithinArmRotation(HIGH_BASKET_ROTATION)) {
                    robot.manipulatorArm.toggleManipulatorState();
                    stage++;
                }
                break;
            case 4:
                robot.manipulatorArm.setTargetArmRotation(0);
                if (manipulatorArmWithinArmRotation(0)) {
                    stage++;
                }
                break;
            case 5:
                robot.manipulatorArm.setTargetArmExtension(RETRACTED_EXTENSION);
                if (manipulatorArmWithinArmExtension(RETRACTED_EXTENSION))
                    stage++;
                break;
            case 6:
                if (robot.mecanumDrive.driveToPosition(PARK_X, PARK_Y, PARK_YAW_RAD, position, angles))
                    stage++;
                break;
        }

        super.loop();
    }

    public boolean manipulatorArmWithinArmExtension(int target) {
        return (Math.abs(robot.manipulatorArm.getCurrentArmExtension() - target) < ARM_EXTENSION_ACCURACY_RANGE);
    }

    public boolean manipulatorArmWithinArmRotation(int target) {
        return (Math.abs(robot.manipulatorArm.getCurrentArmRotation()-target) < ARM_ROTATION_ACCURACY_RANGE);
    }
}
