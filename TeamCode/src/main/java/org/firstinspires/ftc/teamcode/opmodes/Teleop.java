package org.firstinspires.ftc.teamcode.opmodes;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Robot robot = new Robot();
    Servo release;
    TouchSensor stopper;
    DcMotor winch;
    boolean releaseClosed = true;

    @Override
    public void init() {
        robot.init(hardwareMap);
        release = hardwareMap.servo.get("release");
        stopper = hardwareMap.touchSensor.get("stopper");
        winch = hardwareMap.dcMotor.get("winch");
    }

    @Override
    public void loop() {
        //robot.updatePosition();

        double forward = -gamepad1.left_stick_y / 3;
        double right = gamepad1.left_stick_x / 3;
        double rotate = (-gamepad1.right_stick_x / 3) *0.8;

        robot.mecanumDrive.drive(forward,right,rotate);

        if (gamepad2.a & releaseClosed) {
            release.setPosition(0.25);
        } else if (gamepad2.a & !releaseClosed) {
            release.setPosition(0);
        }
        if (gamepad2.b) {
            if (!stopper.isPressed()) {
                winch.setPower(0);
            } else {
                winch.setPower(1);
            }
        } else if (gamepad2.x){
            winch.setPower(-1);
        }
        else {
            winch.setPower(0);
        }

    }
}
