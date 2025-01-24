package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Drive to Observation Zone", group = "Blue")
public class DriveToObservationZoneBlue extends OpMode {

    Robot robot = new Robot();

    boolean manipArmAccurate = false;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    public void loop() {
        // BEGIN -- THIS IS NOT IDEMPOTENT and CAN ONLY BE RUN ONCE PER MATCH
        if (!manipArmAccurate) {
            robot.manipulatorArm.setTargetArmRotation(40);
        }

        if (robot.manipulatorArm.getCurrentArmRotation() >= 40 & !manipArmAccurate) {
            robot.manipulatorArm.rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            manipArmAccurate = true;
            robot.manipulatorArm.setTargetArmRotation(0);
        }
        // END -- THIS IS NOT IDEMPOTENT and CAN ONLY BE RUN ONCE PER MATCH



        //TODO: DO DIS
        //plans:
        //wait a little bit to allow alliance to go
        //go left until near basket zone
        //turn to drop piece
        //drop piece
        //go to hang zone
    }
}
