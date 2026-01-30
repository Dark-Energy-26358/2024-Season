package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;
import org.firstinspires.ftc.teamcode.enums.ObeliskPattern;
import org.firstinspires.ftc.teamcode.enums.SorterColorSensors;
import org.firstinspires.ftc.teamcode.mechanisms.LimelightCamera;
import org.firstinspires.ftc.teamcode.mechanisms.OpticalOdometry;
import org.firstinspires.ftc.teamcode.mechanisms.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.Sorter;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

public class Robot {
    public Globals globals = Globals.getInstance();
    public MecanumDrive mecanumDrive = new MecanumDrive();
    public LimelightCamera camera = new LimelightCamera();
    public OpticalOdometry opticalOdometry = new OpticalOdometry();
    private final Sorter sorter = new Sorter();
    private final Outtake outtake = new Outtake();

    public void init(HardwareMap hardwareMap){
        mecanumDrive.init(hardwareMap);

        sorter.init(hardwareMap);
        outtake.init(hardwareMap);
        //camera.init(hardwareMap);
        //opticalOdometry.init(hardwareMap);
    }


    public Position getPosition(){
        if (camera.isLive()) {
            return camera.getPosition();
        } else {
              return new Position(DistanceUnit.INCH, opticalOdometry.getPosition().x, opticalOdometry.getPosition().y, camera.getPosition().z,0);
        }
    }

    public YawPitchRollAngles getOrientation(){
        if (camera.isLive()) {
            return camera.getOrientation();
        } else {
            return new YawPitchRollAngles(AngleUnit.DEGREES, opticalOdometry.getPosition().h,camera.getOrientation().getPitch(),camera.getOrientation().getRoll(),0);
        }
    }

    // TODO: Ethan, add a docstring for this
    public ObeliskPattern getObeliskPattern() {
        return camera.getObeliskPattern();
    }

    // call every frame
    public void updatePosition(){
        if (camera.isLive()) {
            opticalOdometry.setPosition(new SparkFunOTOS.Pose2D(camera.getPosition().x,camera.getPosition().y,camera.getOrientation().getYaw()));
        }
    }

    /**
     * warning: it is recommended to only launch balls while the robot is stationary
     * warning: the robot will sleep on a separate thread, this function immediately returns
     * spins up the outtake flywheels to Outtake.launchSpeed.
     * move the sorter to have ballColor at the Outtake
     * lifts the ball into the shooter
     * shoots the ball
     * waits enough time to guarantee the ball has been shot
     * stops the outtake flywheels
     * moves an empty slot to the intake
     * @param ballColor
     * the color of the ball we intent to launch
     */
    public void launchBall(DecodeColor ballColor) {// it is recommended to only launch balls while the robot is stationary
        Thread launchThread = new Thread(() -> {
            outtake.spinUp();
            sorter.moveToOuttake(ballColor);
            outtake.liftBall();
            outtake.waitForLaunch();
            outtake.lowerBall();
            outtake.spinDown();
            sorter.moveToIntake(DecodeColor.EMPTY);
        });
        launchThread.start();
    }

    /**
     * warning: this function will sleep on a separate thread and will return immediately.
     * moves a empty slot to the intake.
     * starts the intake motor.
     * waits for the color sensor to detect a ball.
     * stops the intake.
     * moves a empty slot to the intake.
     * if the sorter is full then it will pulse the intake motor to alert the human driver.
     * if you desire to check the colors of the sorter it is recommended that you use getSorterColors
     */
    public void intakeBall(){

        Thread intakeThread = new Thread(() -> {
            sorter.moveToIntake(DecodeColor.EMPTY);
            sorter.startIntake();
            sorter.waitForIntake();
            sorter.stopIntake();
            sorter.moveToIntake(DecodeColor.EMPTY);
            });
        intakeThread.start();

    }

    /**
     * sets the angle of the shooter aiming servo and the speed that the ball will launch
     * @param angle
     * @param speed
     */
    public void aim(double angle, double speed){
        outtake.aim(angle,speed);
    }

    /**
     * changes the angle of the shooter aiming servo and the speed that the ball will launch
     * the speed starts at 0.5 and the angle starts at 0
     * @param angle
     * @param speed
     */
    public void modifyAim(double angle, double speed){
        outtake.aim(outtake.getAimAngle() + angle, outtake.getAimSpeed()+speed);
    }

    /**
     * warning: this is not implemented.
     * uses the limelight to align the robot's rotation, the launch speed, and the launch angle as to score the ball.
     * warning: it is recommended that you do not move the robot after you call this and before you shoot as if you move it will lose its aim.
     */
    public void smartAim(){//todo do dis

    }

    /**
    * returns the raw encoder value of flywheel 1
     * warning: this is intended for debugging
    */
    public int getRawEncoder(){
        return outtake.getRawEncoder();
    }

    /**
     * returns the color of the ball at the sensor
     * @param sensor
     * the sensor you want to check the color of
     * @return
     * returns the color of the ball at the sensor
     */
    public DecodeColor getSorterColors(SorterColorSensors sensor){
        return sorter.getRawColors(sensor);
    }
}

