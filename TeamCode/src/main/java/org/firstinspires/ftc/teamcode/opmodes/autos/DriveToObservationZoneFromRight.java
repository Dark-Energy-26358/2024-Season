package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Drive to Observation Zone", preselectTeleOp = "TeleOp", group = "Blue")
public class DriveToObservationZoneFromRight extends OpMode {

    Robot robot = new Robot();
    private long startTime;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
        super.start();
    }

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

        if (getAgeInSeconds() < 8) {
            robot.mecanumDrive.drive(0, 0.2, 0);
        }

        //TODO: DO DIS
        //plans:
        //wait a little bit to allow alliance to go
        //go left until near basket zone
        //turn to drop piece
        //drop piece
        //go to hang zone
    }

    public int getAgeInSeconds() {
        long nowMillis = System.currentTimeMillis();
        return (int) ((nowMillis - this.startTime) / 1000);
    }
}
