package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;
import org.firstinspires.ftc.teamcode.enums.SorterColorSensors;
import org.firstinspires.ftc.teamcode.enums.State;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Robot robot = new Robot();

    @Override
    public void init() {robot.init(hardwareMap);}

    @Override
    public void loop() {
//        robot.updatePosition();

        //making the robot drive
        double speed = 0.2 + gamepad1.left_trigger*0.3 + gamepad2.right_trigger*0.3;//turbo mode
        double forward = -gamepad1.left_stick_y * speed;
        double right = gamepad1.left_stick_x * speed;
        double rotate = (-gamepad1.right_stick_x) * 0.8;
        robot.mecanumDrive.drive(forward, right, rotate);

        //shoot
        if (gamepad1.aWasPressed()){
            robot.launchBall(DecodeColor.GREEN);//shooting green
        }
        else if (gamepad1.xWasPressed()) {
            robot.launchBall(DecodeColor.PURPLE);//shooting purple
        }

        //intake
        if (gamepad1.bWasPressed()){
            robot.intakeBall();
        }

        //smart aim
        if (gamepad1.yWasPressed()){
            robot.smartAim();
        }

        //aim
        if (gamepad1.dpadUpWasPressed()){
            robot.modifyAim(0.1,0);
        } else if (gamepad1.dpadDownWasPressed()){
            robot.modifyAim(-0.1,0);
        } else if (gamepad1.dpadRightWasPressed()){
            robot.modifyAim(0,0.1);
        } else if (gamepad1.dpadLeftWasPressed()){
            robot.modifyAim(0,-0.1);
        }

        //telemetry
        telemetry.addData("sensor value Intake", robot.getRawColors(SorterColorSensors.INTAKE));
        telemetry.addData("sensor value Right", robot.getRawColors(SorterColorSensors.RIGHT));
        telemetry.addData("sensor value Left", robot.getRawColors(SorterColorSensors.LEFT));
        telemetry.addData("raw encoder", robot.getRawEncoder());


    }
}
