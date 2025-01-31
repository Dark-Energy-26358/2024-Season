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
    public double speed = 1;
    public boolean stopped;
    protected int maxExtension;
    protected int minExtension;
    protected int maxRotation;
    protected int minRotation;
    protected int EXTENSION_TICKS_TO_INCHES = 116;
    protected int rotationMultiplier; // TODO: Use this

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

    public int getTargetArmExtension() {
        return extensionMotor.getTargetPosition() / EXTENSION_TICKS_TO_INCHES;
    }

    //27.5 in = 3200 tick(s)
    public void setTargetArmExtension(int targetExtension) {
        if (targetExtension > minExtension && targetExtension < maxExtension) {
            extensionMotor.setTargetPosition(targetExtension * EXTENSION_TICKS_TO_INCHES);
            extensionMotor.setPower(speed);
            extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            targetArmExtension = extensionMotor.getTargetPosition();
        }
    }

    public double getTargetArmRotation() {
        return 0;
    }

    //1.5˚ = 1 tick(s)
    public void setTargetArmRotation(int targetRotation) {
    }

    public int getCurrentArmExtension() {
        currentArmExtension = extensionMotor.getCurrentPosition();
        return currentArmExtension / EXTENSION_TICKS_TO_INCHES;
    }

    public double getCurrentArmRotation() {
        return 0;
    }


    public void setTargetManipulatorHandPosition(int targetHandPosition) {

    }
}
