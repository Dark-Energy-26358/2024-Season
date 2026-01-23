package org.firstinspires.ftc.teamcode.opmodes;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Robot robot = new Robot();
    ColorSensor sensor;
    boolean yPressed = false;
    boolean bPressed = false;

    @Override
    public void init() {
        robot.init(hardwareMap);
        sensor = hardwareMap.colorSensor.get("colorSensor0");

    }

    @Override
    public void loop() {
        //robot.updatePosition();

        double forward = -gamepad1.left_stick_y / 3;
        double right = gamepad1.left_stick_x / 3;
        double rotate = (-gamepad1.right_stick_x / 3) *0.8;

        robot.mecanumDrive.drive(forward,right,rotate);

        if (gamepad1.aWasPressed()){
//            //if (robot.sorter.sort(DecodeColor.green) == 1){
//                telemetry.addData("ball ready", DecodeColor.green);
//            }
            robot.sorter.increasePos(1);
        } else if (gamepad1.x){
            if (robot.sorter.sort(DecodeColor.purple) == 1){
                telemetry.addData("ball ready", DecodeColor.purple);
            }
        }
        if (gamepad1.yWasPressed()) {
            if (!yPressed) {
                robot.shooter.shoot(1);
                yPressed = true;
            }else {
                robot.shooter.reset();
                yPressed = false;
            }
        }
        if (gamepad1.bWasPressed()) {
            if (!bPressed) {
                robot.intake.start();
                bPressed = true;
            }else {
                robot.intake.stop();
                bPressed = false;
            }
        }
        if (gamepad1.dpadUpWasPressed()){
            robot.shooter.aim(robot.shooter.getAim()+0.1);
        } else if (gamepad1.dpadDownWasPressed()){
            robot.shooter.aim(robot.shooter.getAim()-0.1);
        }
        telemetry.addData("sorter pos", robot.sorter.getPos());


        telemetry.addData("sensor red:",sensor.red());
        telemetry.addData("sensor green:",sensor.green());
        telemetry.addData("sensor blue:",sensor.blue());
    }
}
