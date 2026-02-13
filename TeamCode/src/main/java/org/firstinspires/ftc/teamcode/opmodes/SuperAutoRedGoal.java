package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;

@Autonomous(name = "Red: Super Auto Goal Side", group = "Red")
public class SuperAutoRedGoal extends OpMode {

    // Number of inches in a tile
    final double TILE_SIZE = 24.0;

    final double INVERT_SIDE = 1;

    final double START_X = INVERT_SIDE * TILE_SIZE * 2;
    final double START_Y = TILE_SIZE * 2;
    final double START_H = INVERT_SIDE * 45;

    final double OBELISK_READ_X = INVERT_SIDE * TILE_SIZE * 0.5;
    final double OBELISK_READ_Y = TILE_SIZE * 2;

    final double LAUNCH_X = INVERT_SIDE * TILE_SIZE * 0.5;
    final double LAUNCH_Y = TILE_SIZE * 2;

    int sorterCooldown = 0;

    int patternIndex = 0;

    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.opticalOdometry.setPosition(new SparkFunOTOS.Pose2D(START_X, START_Y, START_H));
    }

    @Override
    public void loop() {
        sorterCooldown--;

        if (robot.getObeliskPattern() == null) {
//            boolean onSpot = robot.mecanumDrive.driveToPosition(OBELISK_READ_X, OBELISK_READ_Y,
//                    45,
//                    robot.getPosition(), robot.getOrientation()
//            );
        } else {
            boolean activePatternBit = ((robot.getObeliskPattern().getPatternBits() >> (patternIndex % 3)) & 1) == 1;

            DecodeColor activeColor = activePatternBit ? DecodeColor.PURPLE : DecodeColor.GREEN;

            if (robot.hasBallColor(activeColor)) {
                boolean onSpot = robot.mecanumDrive.driveToPosition(LAUNCH_X, LAUNCH_Y,
                        45,
                        robot.getPosition(), robot.getOrientation()
                );

                if (onSpot && !robot.isLaunchingBall()) {
                    robot.launchBall(activeColor);
                    patternIndex++;
                }
            }
        }

        telemetry.addData("Position detected", robot.getPosition());
        telemetry.addData("Obelisk detected", robot.getObeliskPattern());

        robot.updatePosition();
//        else {
//            boolean onSpot = robot.mecanumDrive.driveToPosition(TILE_SIZE*0.5, TILE_SIZE*2,
//                    90,
//                    robot.getPosition(), robot.getOrientation()
//            );
//
//            // TODO: Intake and move until we're full
//        }
    }
}
