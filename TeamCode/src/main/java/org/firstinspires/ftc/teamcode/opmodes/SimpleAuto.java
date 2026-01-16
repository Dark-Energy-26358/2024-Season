package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Simple Auto")
public class SimpleAuto extends OpMode {

    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        boolean onSpot = robot.mecanumDrive.driveToPosition(0, 0,
                (int) robot.getOrientation().getYaw(AngleUnit.DEGREES),
                robot.getPosition(), robot.getOrientation()
        );

        if (onSpot) {
            // Launch the ball
        }
    }
}
