package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.enums.DecodeColor;

public class Sorter {
    public Servo tripaddle;

    ColorSensor colorSensor0;
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;

    final double TOTAL_NUMBER_OF_POSITIONS = 30.2;
    final int NUMBER_OF_REACHABLE_POSITIONS = 24;

    public void init(HardwareMap hardwareMap){
        tripaddle = hardwareMap.servo.get("tripaddle");

        colorSensor0 = hardwareMap.colorSensor.get("colorSensor0");
        colorSensor1 = hardwareMap.colorSensor.get("colorSensor1");
        colorSensor2 = hardwareMap.colorSensor.get("colorSensor2");

        //todo keep track of what ball spots are filled and their color


        tripaddle.setPosition(0);
    }

    public int sort(DecodeColor color){
        //-1 means the sorter was not aligned
        //0 means the sorter is empty
        //1 means it was sorted correctly
        //2 means the sorter sorted to a ball of a different color than requested because there were no balls of the correct color

        if (getPos() % 2 == 0){//check to see if the sorter is aligned to be able to see

            //check to see if the correct color ball is anywhere, if it is then return 1
            if (getColor(1) == color){
                increasePos(-1);
                return 1;
            } else if (getColor(2) == color) {
                increasePos(1);
                return 1;
            } else if (getColor(0) == color) {
                increasePos(3);
                return 1;
            }

            //if it isn't then check to see if any ball is anywhere, if it is then return 2
            else if (getColor(1) != DecodeColor.black) {
                increasePos(-1);
                return 2;
            }else if (getColor(2) != DecodeColor.black) {
                increasePos(1);
                return 2;
            }else if (getColor(0) != DecodeColor.black) {
                increasePos(3);
                return 2;
            }else { //if it is empty return 0
                return 0;
            }
        } else{ //if it is not aligned return -1
            increasePos(1);
            return -1;
        }
    }

    private void gotoPos(int pos){
        //sets the position of the sorter to one of 6 positions, 0 and even numbers have the intake open the odd leave it blocked
        double targetPos = ((double) (pos % NUMBER_OF_REACHABLE_POSITIONS))/TOTAL_NUMBER_OF_POSITIONS;
        tripaddle.setPosition(targetPos);
    }

    public int getPos(){
        return (int) Math.round(tripaddle.getPosition()*TOTAL_NUMBER_OF_POSITIONS);
    }

    public boolean moveToEmpty(){//todo do dis
        return false;
    }

    public void increasePos(int amount){
        gotoPos(getPos()+amount);
    }
    private DecodeColor getColor(int sensor){
        switch (sensor){
            case 0:
                return getSensorColor(colorSensor0);
            case 1:
                return getSensorColor(colorSensor1);
            case 2:
                return getSensorColor(colorSensor2);


        }
        return null;
    }

    private DecodeColor getSensorColor(ColorSensor sensor){
    int red = sensor.red();
    int green = sensor.green();
    int blue = sensor.blue();
        if (red+blue+green < 300){
            return DecodeColor.black;
        }else {
            if (blue > green) {
                return DecodeColor.purple;
            }else {
                return DecodeColor.green;
            }
        }
    }



}
