package org.firstinspires.ftc.teamcode.classes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MoveableArm implements org.firstinspires.ftc.teamcode.Interfaces.MoveableArm {

    public int currentArmExtension = 0;
    public int targetArmExtension = 0;

    public int currentArmRotation = 0;
    public int targetArmRotation = 0;

    public String extensionMotorName = "";
    public String rotationMotorName = "";

    DcMotor extensionMotor;
    DcMotor rotationMotor;

    public void init(HardwareMap hardwareMap) {
        extensionMotor = hardwareMap.dcMotor.get(extensionMotorName);
        rotationMotor = hardwareMap.dcMotor.get(rotationMotorName);

        extensionMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rotationMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rotationMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }



    public void moveTowardsTargetArmExtension(int targetExtension) { //TODO dod dis
        targetArmExtension = targetExtension;

        if (-extensionMotor.getCurrentPosition() < 0){
            extensionMotor.setPower(0.01);
        }else{
            extensionMotor.setPower(-0.01);
        }
//        if (extensionMotor.getCurrentPosition() > targetArmExtension + 100) {
//            extensionMotor.setPower(-0.1);
//        }else {
//            extensionMotor.setPower(0);
//        }
    }

    public void setTargetArmRotation(int targetRotation) {
        rotationMotor.setTargetPosition(targetRotation);
        targetArmRotation = targetRotation;
    }



    public int getTargetArmExtension() {
        return targetArmExtension;
    }

    public int getTargetArmRotation() {
        return targetArmRotation;
    }


    public int getCurrentArmExtension() {
        currentArmExtension = extensionMotor.getCurrentPosition();
        return currentArmExtension;
    }

    public int getCurrentArmRotation() {
        currentArmRotation = rotationMotor.getCurrentPosition();
        return currentArmRotation;
    }



    public void setTargetManipulatorHandPosition(int targetHandPosition) {

    }
}
