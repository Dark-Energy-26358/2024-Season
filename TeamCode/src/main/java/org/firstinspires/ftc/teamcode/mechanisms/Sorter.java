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

    private static final double TOTAL_NUMBER_OF_POSITIONS = 30.2;
    private static final int NUMBER_OF_REACHABLE_POSITIONS = 12;
    private static final int initPos = 6;

    private static final long moveTime = 750;//the approximate amount of time it takes the tri-paddle to move one position
    private static final int waitForIntakeTime = 500;

    private static final double intakeSpeed = 0.5;

    private static final int emptyColorThreshold = 750;

    public void init(HardwareMap hardwareMap) {
        tripaddle = hardwareMap.servo.get("tripaddle");

        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        colorSensor0 = hardwareMap.colorSensor.get("colorSensor0");
        colorSensor1 = hardwareMap.colorSensor.get("colorSensor1");
        colorSensor2 = hardwareMap.colorSensor.get("colorSensor2");

        // TODO: keep track of what ball spots are filled and their color

        gotoPos(initPos);
    }

    public void startIntake(){
        intake.setPower(intakeSpeed);
    }

    public void stopIntake(){
        intake.setPower(0);
    }

    public void waitForIntake(){
        while(getColorAtSensor(SorterColorSensors.INTAKE) == DecodeColor.EMPTY );
        try {Thread.sleep(waitForIntakeTime);}
        catch (InterruptedException ignored){}
    }

    public boolean moveToIntake(DecodeColor ballColor) { // true means it succeeded false means it is full
        if (getPos() % 2 == 1) {
            increasePos(1);
        }
        if (getColorAtSensor(SorterColorSensors.INTAKE) == DecodeColor.EMPTY){//if the spot at the intake is already the correct color then return true
            return true;
        } else if (getColorAtSensor(SorterColorSensors.RIGHT) == DecodeColor.EMPTY) {// if the spot to the right is empty move to the intake and return true
            increasePos(-2);
            return true;
        }
        else if (getColorAtSensor(SorterColorSensors.LEFT) == DecodeColor.EMPTY) {// if the spot to the left is empty move to the intake and return true
            increasePos(2);
            return true;
        }
        else{
            return false;//else return false because the entire sorter is the wrong color
        }
    }

    public boolean moveToOuttake(DecodeColor ballColor){ // true means it succeded false means it is empty
        if (getPos()%2 == 1){
            increasePos(-1);
        }
         if (getColorAtSensor(SorterColorSensors.RIGHT) == ballColor) {//if the spot to the right is the correct color then move to the outtake and return true
            increasePos(1);
             return true;
        }
        else if (getColorAtSensor(SorterColorSensors.LEFT) == ballColor) {//if the spot to the left is the correct color then move to the outtake and return true
            increasePos(-1);
             return true;
        }
        else if (getColorAtSensor(SorterColorSensors.INTAKE) == ballColor){//if the spot by the intake is the correct color then move to the outtake and return true
            increasePos(3);
             return true;
        } else {
            return false;//else return false because the entire sorter is the wrong color
        }
    }


    // telemetry
    public DecodeColor getRawColors(SorterColorSensors sensor) {
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
        int realAmount;
        if (amount>=0){
            if (getPos()<=6){
                realAmount = amount;
            }
            else {
                realAmount = amount-6;
            }
        }else {
            if (getPos()>=6){
                realAmount = amount;
            }else {
                realAmount = amount+6;
            }
        }
        gotoPos(getPos() + realAmount);
        try {Thread.sleep(moveTime * Math.abs(realAmount));}
        catch (InterruptedException ignored) {}
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

        if (red + blue + green < emptyColorThreshold) { // if the total color is below the empty threshold then it is empty
            return DecodeColor.EMPTY;
        } else {
            if (blue > red + green) { // if blue is greater the red plus green the it is blue/init color
                return DecodeColor.INIT_COLOR;
            } else if (green > Math.max(red, blue)) { // if green is greater than the max of red and blue
                return DecodeColor.GREEN;
            } else { // otherwise it is purple
                return DecodeColor.PURPLE;
            }
        }
    }
}
