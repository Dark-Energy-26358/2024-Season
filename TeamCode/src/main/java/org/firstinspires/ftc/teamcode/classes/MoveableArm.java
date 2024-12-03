package org.firstinspires.ftc.teamcode.classes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MoveableArm implements org.firstinspires.ftc.teamcode.Interfaces.MoveableArm {

    public int currentArmExtension = 0;
    public int targetArmExtension = 0;

    public double currentArmRotation = 0;
    public double targetArmRotation = 0;

    public String extensionMotorName = "";
    public String rotationMotorName = "";

    DcMotor extensionMotor;
    DcMotor rotationMotor;

    public boolean stopped;

    public void init(HardwareMap hardwareMap) {
        extensionMotor = hardwareMap.dcMotor.get(extensionMotorName);
        rotationMotor = hardwareMap.dcMotor.get(rotationMotorName);

        extensionMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rotationMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotationMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotationMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stop() {
        stopped = true;
        extensionMotor.setPower(0);
        rotationMotor.setPower(0);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

//27.5 in = 3200 tick(s)
    public void setTargetArmExtension(int targetExtension) {
        extensionMotor.setTargetPosition(targetExtension*116);
        extensionMotor.setPower(0.5);
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //1.5˚ = 1 tick(s)
    public void setTargetArmRotation(int targetRotation) {
        stopped = false;
        targetArmRotation = targetRotation*1.5;
        extensionMotor.setPower(0.5);
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



    public int getTargetArmExtension() {
        return targetArmExtension/116;
    }

    public double getTargetArmRotation() {
        return targetArmRotation/1.5;
    }


    public int getCurrentArmExtension() {
        currentArmExtension = extensionMotor.getCurrentPosition();
        return currentArmExtension/116;
    }

    public double getCurrentArmRotation() {
        currentArmRotation = rotationMotor.getCurrentPosition();
        return currentArmRotation/1.5;
    }



    public void setTargetManipulatorHandPosition(int targetHandPosition) {

    }
}
