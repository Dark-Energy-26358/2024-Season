package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;

@Autonomous(name="Red: Super Auto", group="Red")
public class SuperAutoRed extends OpMode {

    // Number of inches in a tile
    final double TILE_SIZE = 24.0;

    int sorterCooldown = 0;

    int patternIndex = 0;

    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        sorterCooldown--;

        boolean activePatternBit = ((robot.getObeliskPattern().getPatternBits() >> (patternIndex % 3)) & 1) == 1;

        DecodeColor activeColor = activePatternBit ? DecodeColor.PURPLE : DecodeColor.GREEN;

        if (robot.hasBallColor(activeColor)) {
            boolean onSpot = robot.mecanumDrive.driveToPosition(TILE_SIZE*0.5, TILE_SIZE*2,
                    45,
                    robot.getPosition(), robot.getOrientation()
            );

            if (onSpot && !robot.isLaunchingBall()) {
                robot.launchBall(activeColor);
                patternIndex++;
            }
        }

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
