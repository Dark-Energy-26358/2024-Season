package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;

public class Sorter {
    public Servo tripaddle;

    ColorSensor colorSensor0;
    ColorSensor colorSensor1;
    ColorSensor colorSensor2;

    final double TOTAL_NUMBER_OF_POSITIONS = 30.2;
    final int NUMBER_OF_REACHABLE_POSITIONS = 12;

    public void init(HardwareMap hardwareMap) {
        tripaddle = hardwareMap.servo.get("tripaddle");

        colorSensor0 = hardwareMap.colorSensor.get("colorSensor0");
        colorSensor1 = hardwareMap.colorSensor.get("colorSensor1");
        colorSensor2 = hardwareMap.colorSensor.get("colorSensor2");

        // TODO: keep track of what ball spots are filled and their color

        tripaddle.setPosition(0);
    }

    public void calibrate(Telemetry telemetry) {
        while (getColor(0) != DecodeColor.BLUE) {
            telemetry.addData("Position 0 color", getColor(0));
            telemetry.addData("Position 0 red", getRed(0));
            telemetry.addData("Position 0 green", getGreen(0));
            telemetry.addData("Position 0 blue", getBlue(0));
            telemetry.update();

            tripaddle.setPosition(tripaddle.getPosition() + 0.01);
            try {
                //noinspection BusyWait
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void gotoPos(int pos) {
        // Sets the position of the sorter to one of 6 positions, 
        // 0 and even numbers have the intake open the odd leave it blocked
        double targetPos = ((double) (pos % NUMBER_OF_REACHABLE_POSITIONS))/TOTAL_NUMBER_OF_POSITIONS;
        tripaddle.setPosition(targetPos);
    }

    public int getPos() {
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

    public int getRed(int sensorNumber) {
        assert getSensor(sensorNumber) != null;
        return getSensor(sensorNumber).red();
    }

    public int getGreen(int sensorNumber) {
        assert getSensor(sensorNumber) != null;
        return getSensor(sensorNumber).green();
    }

    public int getBlue(int sensorNumber) {
        return getSensor(sensorNumber).blue();
    }

    public DecodeColor getColor(int sensorNumber) {
        ColorSensor sensor = getSensor(sensorNumber);
        return getSensorColor(sensor);
    }

    private DecodeColor getSensorColor(ColorSensor sensor) {
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

    public int getNumberOfBalls() {
        return (
                (getColor(0).equals(DecodeColor.EMPTY) ? 0 : 1) +
                (getColor(1).equals(DecodeColor.EMPTY) ? 0 : 1) +
                (getColor(2).equals(DecodeColor.EMPTY) ? 0 : 1)
        );
    }
}
