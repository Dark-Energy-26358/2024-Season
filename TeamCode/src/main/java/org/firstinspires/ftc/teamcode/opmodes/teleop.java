package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
@TeleOp()
public class teleop extends OpMode {

    Robot robot = new Robot();
    @Override
    public void init() {
        robot.init(hardwareMap);
    }
    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y/3;
        double right = gamepad1.left_stick_x/3;
        double rotate = gamepad1.right_stick_x/3;

        robot.mecanumDrive.drive(forward, right, rotate);
        robot.chinUpArm.run(gamepad2.right_trigger - gamepad2.left_trigger); // These controls can be changed
    }
}