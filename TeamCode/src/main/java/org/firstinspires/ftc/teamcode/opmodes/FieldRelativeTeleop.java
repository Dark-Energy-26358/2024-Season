package org.firstinspires.ftc.teamcode.opmodes;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="DEV ONLY, DO NOT USE", group = "z/dev")
public class FieldRelativeTeleop extends OpMode {

    Robot robot = new Robot();
    boolean manipulatorJustToggled = false;

    boolean active = false;
    boolean justInput = false;

    String password = "";

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void init_loop(){
        if (gamepad1.dpad_up){if (!justInput){password += "u"; justInput = true;}}
        else if (gamepad1.dpad_down){if (!justInput){password += "d"; justInput = true;}}
        else if (gamepad1.dpad_left){if (!justInput){password += "l"; justInput = true;}}
        else if (gamepad1.dpad_right){if (!justInput){password += "r"; justInput = true;}}
        else if (gamepad1.a){if (!justInput){password += "a"; justInput = true;}}
        else if (gamepad1.b){if (!justInput){password += "b"; justInput = true;}}
        else if (gamepad1.x){if (!justInput){password += "x"; justInput = true;}}
        else if (gamepad1.y){if (!justInput){password += "y"; justInput = true;}}
        else if (gamepad1.start){if (!justInput){password += "s"; justInput = true;}}
        else {justInput = false;}

        active = password.equals("uuddlrlrbas");
        telemetry.addData("password:", password);
        telemetry.addData("active:", active);

    }

    @Override
    public void loop() {
        if (active) {
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

            double forward = -gamepad1.left_stick_y / 3;
            double right = -gamepad1.left_stick_x / 3;
            double rotate = (-gamepad1.right_stick_x / 3) * 0.8;

            robot.mecanumDrive.moveFieldRelative(right, forward, rotate, robot.getOrientation().getYaw(AngleUnit.RADIANS));

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
//        if (gamepad2.)
//        robot.stageOneAscentArms.run((gamepad2.left_bumper ? 0 : 1) - gamepad2.left_trigger, (gamepad2.right_bumper ? 0 : 1) - gamepad2.right_trigger);

            telemetry.addData("extension", robot.manipulatorArm.getCurrentArmExtension());
            telemetry.addData("Target extension", robot.manipulatorArm.getTargetArmExtension());
            telemetry.addData("rotation", robot.manipulatorArm.getCurrentArmRotation());
            telemetry.addData("Target rotation", robot.manipulatorArm.getTargetArmRotation());
            telemetry.addData("stopped", robot.manipulatorArm.stopped);
            //robot.stageTwoAscentArms.run(gamepad2.right_stick_y);
            //robot.stageOneAscentArms.run(gamepad2.left_stick_y);
        }
    }

    public void stop() {
        robot.globals.setManipArmAccurate(false);
    }
}
