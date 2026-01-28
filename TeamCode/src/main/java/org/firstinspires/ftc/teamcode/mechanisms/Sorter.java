package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.enums.DecodeColor;
import org.firstinspires.ftc.teamcode.enums.SorterColorSensors;

import java.sql.Array;

public class Sorter {
    public Servo tripaddle;

    private DcMotor intake;

    ColorSensor colorSensor0;
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;

    private static final double TOTAL_NUMBER_OF_POSITIONS = 30.2;
    private static final int NUMBER_OF_REACHABLE_POSITIONS = 12;

    private static final double intakeSpeed = 0.5;

    private static final int emptyColorThreshold = 750;

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

        gotoPos(6);
    }

    public void startIntake(){
        intake.setPower(intakeSpeed);
    }

    public void stopIntake(){
        intake.setPower(0);
    }


    public synchronized void waitForIntake(){
        int timer = 0;
        int x=1000;while (x>0){x--; Thread.yield();}
        while(getColorAtSensor(SorterColorSensors.INTAKE) == DecodeColor.EMPTY && timer < timeout){
            timer += 1;
        }
    }

    public boolean moveToIntake(DecodeColor ballColor) { // true means it succeded false means it is full
        if (getPos()%2 == 1){
            increasePos(1);
        }
        if (getColorAtSensor(SorterColorSensors.INTAKE) == DecodeColor.EMPTY){
            return true;
        } else if (getColorAtSensor(SorterColorSensors.RIGHT) == DecodeColor.EMPTY) {
            increasePos(-2);
            return true;
        }
        else if (getColorAtSensor(SorterColorSensors.LEFT) == DecodeColor.EMPTY) {
            increasePos(2);
            return true;
        }
        else{
            return false;
        }


    }

    public boolean moveToOuttake(DecodeColor ballColor){ // true means it succeded false means it is empty
        if (getPos()%2 == 1){
            increasePos(-1);
        }
         if (getColorAtSensor(SorterColorSensors.RIGHT) == ballColor) {
            increasePos(1);
             return true;
        }
        else if (getColorAtSensor(SorterColorSensors.LEFT) == ballColor) {
            increasePos(-1);
             return true;
        }
        else if (getColorAtSensor(SorterColorSensors.INTAKE) == ballColor){
            increasePos(3);
             return true;
         }
        else{
            return false;
        }
    }

    public DecodeColor getRawColors(SorterColorSensors sensor){
        return getColorAtSensor(sensor);
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
        try {Thread.sleep(250L * Math.abs(amount));}
        catch (InterruptedException ignored){}
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
        if (red + blue + green < emptyColorThreshold){
            return DecodeColor.EMPTY;
        } else {
            if (blue > red + green) {
                return DecodeColor.INIT_COLOR;
            } else if (green > Math.max(red,blue) ) {
                return DecodeColor.GREEN;
            } else {
                return DecodeColor.PURPLE;
            }
        }
    }
}
