package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private DcMotor intake;
    private static final double intakeSpeed = 0.1;

    public void init(HardwareMap hardwareMap){
        intake = hardwareMap.dcMotor.get("intake");
    }
    public void takeIn(){
        intake.setPower(intakeSpeed);
    }

}
