package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp()
public class teleop extends OpMode {

    Robot robot = new Robot();
    boolean manipArmAccurate = false;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(!manipArmAccurate){
        robot.manipulatorArm.setTargetArmRotation(60);}

        if (robot.manipulatorArm.getCurrentArmRotation() >= 57 & !manipArmAccurate){
            robot.manipulatorArm.rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            manipArmAccurate = true;
            robot.manipulatorArm.setTargetArmRotation(0);
        }


        double forward = -gamepad1.left_stick_y / 3;
        double right = gamepad1.left_stick_x / 3;
        double rotate = gamepad1.right_stick_x / 3;

        robot.mecanumDrive.drive(forward, right, rotate);

        if (robot.manipulatorArm.getCurrentArmRotation() > 60){
            robot.manipulatorArm.setTargetArmExtension(0);
        }

        // Template controls - probably going to need to be changed
        if (gamepad2.dpad_down ) {
            robot.manipulatorArm.setTargetArmExtension(0);
        } else if (gamepad2.dpad_up & robot.manipulatorArm.getCurrentArmRotation() < 45) {
            robot.manipulatorArm.setTargetArmExtension(27);
        }

        if (gamepad2.right_bumper) {
            robot.manipulatorArm.setTargetArmRotation(0);
        } else if (gamepad2.left_bumper) {
            robot.manipulatorArm.setTargetArmRotation(90);
        }

        if (gamepad1.dpad_up){
            robot.stageOneAscentArms.setTargetArmExtension(13);
        }else if (gamepad1.dpad_down) {
            robot.stageOneAscentArms.setTargetArmExtension(0);
        }

        if (gamepad2.b){ robot.manipulatorArm.toggleManipulatorState();
        }
        double wristPos = 0.5;
        if (gamepad2.dpad_left){
            wristPos -= 0.01;
        } else if (gamepad2.right_bumper) {
            wristPos += 0.01;
        }
        robot.manipulatorArm.setTargetManipulatorWristPosition(wristPos);

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