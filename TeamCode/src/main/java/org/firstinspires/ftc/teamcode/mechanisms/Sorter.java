package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.enums.DecodeColor;

public class Sorter {
    public Servo tripaddle;

    private DcMotor intake;

    ColorSensor colorSensor0;
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;

    private final double TOTAL_NUMBER_OF_POSITIONS = 30.2;
    private final int NUMBER_OF_REACHABLE_POSITIONS = 12;
    private final double intakeSpeed = 0.5;

    public void init(HardwareMap hardwareMap) {
        tripaddle = hardwareMap.servo.get("tripaddle");

        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        colorSensor0 = hardwareMap.colorSensor.get("colorSensor0");
        colorSensor1 = hardwareMap.colorSensor.get("colorSensor1");
        colorSensor2 = hardwareMap.colorSensor.get("colorSensor2");

        // TODO: keep track of what ball spots are filled and their color

        tripaddle.setPosition(0);
    }

    private void startIntake(){
        intake.setPower(intakeSpeed);
    }

    private void stopIntake(){
        intake.setPower(0);
    }

    private void gotoPos(int pos) {
        // Sets the position of the sorter to one of 6 positions, 
        // 0 and even numbers have the intake open the odd leave it blocked
        double targetPos = ((double) (pos % NUMBER_OF_REACHABLE_POSITIONS))/TOTAL_NUMBER_OF_POSITIONS;
        tripaddle.setPosition(targetPos);
    }

    private int getPos() {
        return (int) Math.round(tripaddle.getPosition()*TOTAL_NUMBER_OF_POSITIONS);
    }

    public boolean moveToEmpty() { // todo do dis
        return false;
    }

    public void increasePos(int amount) {
        gotoPos(getPos()+amount);
    }

    private ColorSensor getSensor(int sensorNumber) {
        switch (sensorNumber) {
            case 0:
                return colorSensor0;
            case 1:
                return colorSensor1;
            case 2:
                return colorSensor2;
            default:
                return null;
        }
    }

    public int getRawRed(int sensorNumber) {
        if(getSensor(sensorNumber) != null) {
            return getSensor(sensorNumber).red();
        }
        else {
            return 0;
        }
    }

    public int getRawGreen(int sensorNumber) {
        if(getSensor(sensorNumber) != null) {
            return getSensor(sensorNumber).green();
        }
        else {
            return 0;
        }
    }

    public int getRawBlue(int sensorNumber) {
        if(getSensor(sensorNumber) != null) {
            return getSensor(sensorNumber).blue();
        }
        else {
            return 0;
        }
    }

    private DecodeColor getColor(int sensorNumber) {
        ColorSensor sensor = getSensor(sensorNumber);
        if (sensor == null) {
            return null;
        }

        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();
        if (red + blue + green < 300){
            return DecodeColor.EMPTY;
        } else {
            if (blue > 150 && blue > red+green) {
                return DecodeColor.BLUE;
            } else if (green > 150 && green > red && green > blue) {
                return DecodeColor.GREEN;
            } else {
                return DecodeColor.PURPLE;
            }
        }
    }

    private DecodeColor getSensorColor(ColorSensor sensor) {

    }

    public int getNumberOfBalls() {
        return (
                (getColor(0).equals(DecodeColor.EMPTY) ? 0 : 1) +
                (getColor(1).equals(DecodeColor.EMPTY) ? 0 : 1) +
                (getColor(2).equals(DecodeColor.EMPTY) ? 0 : 1)
        );
    }
}
