package org.firstinspires.ftc.teamcode.opmodes;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        //robot.updatePosition();

        double forward = -gamepad1.left_stick_y / 3;
        double right = gamepad1.left_stick_x / 3;
        double rotate = (-gamepad1.right_stick_x / 3) *0.8;

        robot.mecanumDrive.drive(forward,right,rotate);
    }
}
