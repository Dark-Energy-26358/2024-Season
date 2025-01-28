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

    public DcMotor extensionMotor;

    public boolean stopped;

    public void init(HardwareMap hardwareMap) {
        extensionMotor = hardwareMap.dcMotor.get(extensionMotorName);

        extensionMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stop() {
        stopped = true;
        extensionMotor.setPower(0);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

//27.5 in = 3200 tick(s)
    public void setTargetArmExtension(int targetExtension) {
        final double SPEED = 0.5;
        extensionMotor.setTargetPosition(targetExtension*116);
        extensionMotor.setPower(SPEED);
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetArmExtension = extensionMotor.getTargetPosition();
    }
    //1.5˚ = 1 tick(s)
    public void setTargetArmRotation(int targetRotation) {

    }



    public int getTargetArmExtension() {
        return extensionMotor.getTargetPosition()/116;
    }

    public double getTargetArmRotation() {
        return 0;
    }


    public int getCurrentArmExtension() {
        currentArmExtension = extensionMotor.getCurrentPosition();
        return currentArmExtension/116;
    }

    public double getCurrentArmRotation() {
        return 0;
    }



    public void setTargetManipulatorHandPosition(int targetHandPosition) {

    }
}
