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
        double forward = -gamepad1.left_stick_y / 3;
        double right = gamepad1.left_stick_x / 3;
        double rotate = gamepad1.right_stick_x / 3;

        robot.mecanumDrive.drive(forward, right, rotate);

        // Template controls - probably going to need to be changed
        if (gamepad2.a) {
            robot.manipulatorArm.setTargetArmExtension(0);
        } else if (gamepad2.x) {
            robot.manipulatorArm.setTargetArmExtension(27);
        }
        if (gamepad2.right_bumper) {
            robot.manipulatorArm.setTargetArmRotation(0);
        } else if (gamepad2.left_bumper) {
            robot.manipulatorArm.setTargetArmRotation(90);
        }if (gamepad2.dpad_up){
            robot.stageOneAscentArms.setTargetArmExtension(13);
        } else if (gamepad2.dpad_down) {
            robot.stageOneAscentArms.setTargetArmExtension(0);
        }
        if (gamepad2.b) {
            robot.manipulatorArm.stop();
        }

        telemetry.addData("extension", robot.manipulatorArm.getCurrentArmExtension());
        telemetry.addData("Target extension", robot.manipulatorArm.getTargetArmExtension());
        telemetry.addData("rotation", robot.manipulatorArm.getCurrentArmRotation());
        telemetry.addData("Target rotation", robot.manipulatorArm.getTargetArmRotation());
        telemetry.addData("stopped", robot.manipulatorArm.stopped);
        //robot.stageTwoAscentArms.run(gamepad2.right_stick_y);
        //robot.stageOneAscentArms.run(gamepad2.left_stick_y);

        telemetry.update();
    }
}