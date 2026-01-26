package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.enums.DecodeColor;
import org.firstinspires.ftc.teamcode.enums.SorterColorSensors;

public class Sorter {
    public Servo tripaddle;

    private DcMotor intake;

    ColorSensor colorSensor0;
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;

    private final double TOTAL_NUMBER_OF_POSITIONS = 30.2;
    private final int NUMBER_OF_REACHABLE_POSITIONS = 12;
    private final double intakeSpeed = 0.5;

    private static final int timeout = 10000;//milliseconds
    private static final int waitTime = 100;//wait time per iteration. milliseconds
    private static final int intakeSensor = 0;

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

    public void startIntake(){
        intake.setPower(intakeSpeed);
    }

    public void stopIntake(){
        intake.setPower(0);
    }


    public synchronized void waitForIntake(){
        int timer = 0;
        while(getColorAtSensor(SorterColorSensors.INTAKE) == DecodeColor.EMPTY && timer < timeout){
            try {
                wait(waitTime);
            }
            catch (InterruptedException ignored){}
            timer += waitTime;
        }
    }

    public boolean moveToIntake(DecodeColor ballColor) { // true means it succeded false means it is full
        if (getPos()%2 == 1){
            increasePos(1);
            try {wait(250);}
            catch (InterruptedException ignored){}
        }
        if (getColorAtSensor(SorterColorSensors.INTAKE) == DecodeColor.EMPTY){
            return true;
        } else if (getColorAtSensor(SorterColorSensors.RIGHT) == DecodeColor.EMPTY) {
            increasePos(2);
            return true;
        }
        else if (getColorAtSensor(SorterColorSensors.LEFT) == DecodeColor.EMPTY) {
            increasePos(-2);
            return true;
        }
        else{
            return false;
        }


    }

    public boolean moveToOuttake(DecodeColor ballColor){ // true means it succeded false means it is empty
        if (getPos()%2 == 1){
            increasePos(-1);
            try {wait(250);}
            catch (InterruptedException ignored){}
        }
         if (getColorAtSensor(SorterColorSensors.RIGHT) == DecodeColor.EMPTY) {
            increasePos(-1);
             return true;
        }
        else if (getColorAtSensor(SorterColorSensors.LEFT) == DecodeColor.EMPTY) {
            increasePos(1);
             return true;
        }
        else if (getColorAtSensor(SorterColorSensors.INTAKE) == DecodeColor.EMPTY){
            increasePos(3);
             return true;
         }
        else{
            return false;
        }
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

    private void increasePos(int amount) {
        gotoPos(getPos()+amount);
    }

    private ColorSensor getSensor(SorterColorSensors sensorNumber) {
        switch (sensorNumber) {
            case INTAKE:
                return colorSensor0;
            case LEFT:
                return colorSensor1;
            case RIGHT:
                return colorSensor2;
            default:
                return null;
        }
    }

    private DecodeColor getColorAtSensor(SorterColorSensors sorterColorSensors) {
        ColorSensor sensor = getSensor(sorterColorSensors);
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
                return DecodeColor.INIT_COLOR;
            } else if (green > 150 && green > red && green > blue) {
                return DecodeColor.GREEN;
            } else {
                return DecodeColor.PURPLE;
            }
        }
    }
}
