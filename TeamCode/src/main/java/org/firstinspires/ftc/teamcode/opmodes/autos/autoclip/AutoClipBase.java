package org.firstinspires.ftc.teamcode.opmodes.autos.autoclip;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.opmodes.autos.AutoBase;
import org.firstinspires.ftc.teamcode.opmodes.autos.autopark.AutoParkBase;

public class AutoClipBase extends AutoBase {
    int stage = 1;
    boolean parking = false;
    final double TILE = 24;

    double CLIP_X = 0.0; // CHANGE_ME!!
    double CLIP_Y = 0.0; // CHANGE_ME!!
    int CLIP_YAW = 0;// CHANGE_ME!!
    double PICKUP_X = 0.0;// CHANGE_ME!!
    double PICKUP_Y = 0.0;// CHANGE_ME!!
    int PICKUP_YAW = 0;// CHANGE_ME!!
    double NET_CORNER_X = 0;// CHANGE_ME!!
    double NET_CORNER_Y = 0; // CHANGE_ME!!
    int NET_CORNER_YAW = 0; // CHANGE_ME!!
    double PARK_X = 0.0; // CHANGE_ME!!
    double PARK_Y = 0.0; // CHANGE_ME!!
    int PARK_YAW = 0;// CHANGE_ME!!

    final int HIGH_BASKET_EXTENSION = 38;
    final int HIGH_SPECIMEN_BAR_EXTENSION = 30;
    final int HIGH_SPECIMEN_CLIPPING_EXTENSION = HIGH_SPECIMEN_BAR_EXTENSION-2;
    final int RETRACTED_EXTENSION = 0;
    final int PICKUP_ROTATION = 125;

    public final double ARM_EXTENSION_RANGE = 1.0;

    private long startTime;

    @Override
    public void init() {
        super.init();
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void loop() {
        if (getAgeInSeconds() < 25) {
            switch (stage) {
                case 1:
                    if (robot.mecanumDrive.driveToPosition(CLIP_X, CLIP_Y, CLIP_YAW, position, angles))
                        stage++;
                    break;
                case 2:
                    robot.manipulatorArm.setTargetArmExtension(HIGH_SPECIMEN_BAR_EXTENSION);
                    if (manipulatorArmWithinArmExtension(HIGH_SPECIMEN_BAR_EXTENSION))
                        stage++;
                    break;
                case 3:
                    robot.manipulatorArm.setTargetArmExtension(HIGH_SPECIMEN_CLIPPING_EXTENSION);
                    if (manipulatorArmWithinArmExtension(HIGH_SPECIMEN_CLIPPING_EXTENSION)) {
                        robot.manipulatorArm.toggleManipulatorState();
                        stage++;
                    }
                    break;
                case 4:
                case 7:
                    robot.manipulatorArm.setTargetArmExtension(RETRACTED_EXTENSION);
                    if (manipulatorArmWithinArmExtension(RETRACTED_EXTENSION))
                        stage++;
                    break;
                case 5:
                    if (robot.mecanumDrive.driveToPosition(PICKUP_X, PICKUP_Y, PICKUP_YAW, position, angles))
                        stage++;
                    break;
                case 6:
                    robot.manipulatorArm.setTargetArmRotation(PICKUP_ROTATION);
                    if (manipulatorArmWithinArmRotation(PICKUP_ROTATION)) {
                        robot.manipulatorArm.toggleManipulatorState();
                        stage++;
                    }
                    break;
                // Case 7 is the same as case 4
            }
        } else {
            if (!parking) {
                stage = 1;
            }
            switch (stage) {
                case 1:
                    if (robot.mecanumDrive.driveToPosition(NET_CORNER_X, NET_CORNER_Y, NET_CORNER_YAW, position, angles))
                        stage++;
                    break;
                case 2:
                    if (robot.mecanumDrive.driveToPosition(CLIP_X, CLIP_Y, CLIP_YAW, position, angles))
                        stage++;
                    break;
            }
            parking = true;
        }

        super.loop();
    }

    public int getAgeInSeconds() {
        long nowMillis = System.currentTimeMillis();
        return (int)((nowMillis - this.startTime) / 1000);
    }

    public boolean manipulatorArmWithinArmExtension(int target) {
        return (Math.abs(robot.manipulatorArm.getCurrentArmExtension()-target) < ARM_EXTENSION_RANGE);
    }

    public boolean manipulatorArmWithinArmRotation(int target) {
        return (Math.abs(robot.manipulatorArm.getCurrentArmRotation()-target) < ARM_EXTENSION_RANGE);
    }
}
