package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Drive to Observation Zone", group = "Blue")
public class DriveToObservationZoneBlue extends OpMode {

    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {
        // USING GLOBAL TO BE IDEMPOTENT
        if (!robot.globals.getManipArmAccurate()) {
            robot.manipulatorArm.setTargetArmRotation(40);
        } else {
            if (robot.manipulatorArm.getCurrentArmRotation() >= 40) {
                robot.manipulatorArm.rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.manipulatorArm.setTargetArmRotation(0);
                robot.globals.setManipArmAccurate(true);
            }
        }



        //TODO: DO DIS
        //plans:
        //wait a little bit to allow alliance to go
        //go left until near basket zone
        //turn to drop piece
        //drop piece
        //go to hang zone
    }
}
