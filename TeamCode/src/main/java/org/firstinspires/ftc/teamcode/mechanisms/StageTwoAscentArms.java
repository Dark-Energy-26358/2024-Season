package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class StageTwoAscentArms implements MoveableArm {

    private DcMotor motor;

    final String motorName = "stageTwoAscentMotor";


    public void init(HardwareMap hardwareMap) {
        motor = hardwareMap.dcMotor.get(motorName);

        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void run(float power) {
        // Preferably -1 to 1, positive is forward, negative is backward. The motor should run through this function.
        motor.setPower(power);

        telemetry.addData(motorName, motor.getCurrentPosition());
    }
}
