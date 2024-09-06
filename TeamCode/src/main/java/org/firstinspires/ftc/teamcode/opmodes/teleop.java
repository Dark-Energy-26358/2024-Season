package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;
@TeleOp()
public class teleop extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    @Override
    public void init() {
        drive.init(hardwareMap);
    }
    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y/3;
        double right = gamepad1.left_stick_x/3;
        double rotate = gamepad1.right_stick_x/3;

        drive.drive(forward, right, rotate);
    }
}