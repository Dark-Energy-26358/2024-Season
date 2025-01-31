package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "TeleOp")
public class Teleop extends OpMode {

    final double FORWARD_COEFFICENT = 0.4;
    final double RIGHT_COEFFICENT = 0.4;
    final double TURN_COEFFICENT = 0.3;
    final double MANIPULATOR_SPEED = 0.5;
    final double ASCENT_SPEED = 0.5;
    Robot robot = new Robot();
    boolean manipulatorJustToggled = false;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        // USING GLOBAL TO BE IDEMPOTENT
        if (!robot.globals.getManipArmAccurate()) {
            robot.manipulatorArm.setTargetArmRotation(40);
            if (robot.manipulatorArm.getCurrentArmRotation() >= 40) {
                robot.manipulatorArm.rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.manipulatorArm.setTargetArmRotation(0);
                robot.globals.setManipArmAccurate(true);
            }
        }

        robot.updatePosition();

        double forward = -gamepad1.left_stick_y * FORWARD_COEFFICENT;
        double right = gamepad1.left_stick_x * RIGHT_COEFFICENT;
        double rotate = gamepad1.right_stick_x * TURN_COEFFICENT;

        robot.mecanumDrive.drive(forward, right, rotate);

        robot.manipulatorArm.speed = MANIPULATOR_SPEED;
        robot.stageOneAscentArms.speed = ASCENT_SPEED;

        if (robot.manipulatorArm.getCurrentArmRotation() > 30 || robot.manipulatorArm.getCurrentArmRotation() < -30) {
            robot.manipulatorArm.setTargetArmExtension(0);
        }

        // Template controls - probably going to need to be changed
        if (gamepad2.dpad_down) {
            robot.manipulatorArm.setTargetArmExtension(0);
        } else if (gamepad2.dpad_up & robot.manipulatorArm.getCurrentArmRotation() < 45) {
            robot.manipulatorArm.setTargetArmExtension(27);
        }

        if (gamepad2.right_bumper) {
            robot.manipulatorArm.setTargetArmRotation(0);
        } else if (gamepad2.left_bumper) {
            robot.manipulatorArm.setTargetArmRotation(90);
        }

        if (gamepad1.dpad_up) {
            robot.stageOneAscentArms.setTargetArmExtension(robot.stageOneAscentArms.getCurrentArmExtension() + 1);
        } else if (gamepad1.dpad_down) {
            robot.stageOneAscentArms.setTargetArmExtension(robot.stageOneAscentArms.getCurrentArmExtension() - 1);
        }

        if (gamepad1.dpad_left) {
            robot.stageOneAscentArms.setTargetArmRotation(robot.stageOneAscentArms.getTargetArmRotation() - 0.01);
        } else if (gamepad1.dpad_right) {
            robot.stageOneAscentArms.setTargetArmRotation(robot.stageOneAscentArms.getTargetArmRotation() + 0.01);
        }

        if (gamepad2.b & !manipulatorJustToggled) {
            robot.manipulatorArm.toggleManipulatorState();
            manipulatorJustToggled = true;
        } else if (!gamepad2.b) {
            manipulatorJustToggled = false;
        }

        if (gamepad2.right_stick_y < 0) {
            robot.manipulatorArm.setTargetManipulatorWristPosition(-gamepad2.right_stick_y / 10 + 0.5);
        } else if (gamepad2.right_stick_y > 0) {
            robot.manipulatorArm.setTargetManipulatorWristPosition(-gamepad2.right_stick_y / 10 + 0.5);
        } else if (gamepad2.right_stick_y == 0) {
            robot.manipulatorArm.setTargetManipulatorWristPosition(0.5);
        }

        if (gamepad2.left_stick_y != 0) {
            robot.manipulatorArm.setTargetArmExtension((int) (robot.manipulatorArm.getTargetArmExtension() - gamepad2.left_stick_y));
        }
        if (gamepad2.left_stick_x != 0) {
            robot.manipulatorArm.setTargetArmRotation((int) (robot.manipulatorArm.getTargetArmRotation() - gamepad2.left_stick_x));
        }
        telemetry.addData("extension", robot.manipulatorArm.getCurrentArmExtension());
        telemetry.addData("Target extension", robot.manipulatorArm.getTargetArmExtension());
        telemetry.addData("rotation", robot.manipulatorArm.getCurrentArmRotation());
        telemetry.addData("Target rotation", robot.manipulatorArm.getTargetArmRotation());
        telemetry.addData("stopped", robot.manipulatorArm.stopped);
    }

    public void stop() {
        robot.globals.setManipArmAccurate(false);
    }
}
