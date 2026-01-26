package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;
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
    private Sorter sorter = new Sorter();
    private Outtake outtake = new Outtake();

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

    // call every frame
    public void updatePosition(){
        if (camera.isLive()) {
            opticalOdometry.setPosition(new SparkFunOTOS.Pose2D(camera.getPosition().x,camera.getPosition().y,camera.getOrientation().getYaw()));
        }
    }

    public void launchBall(DecodeColor ballColor){// it is recommended to only launch balls while the robot is stationary
        mecanumDrive.drive(0,0,0);//stops all robot movement
        if (sorter.moveToOuttake(ballColor)) {
            outtake.spinUp();
            try {wait(1000);}
            catch (InterruptedException ignored){}
            outtake.liftBall();
            outtake.waitForLaunch();
            outtake.lowerBall();
            outtake.spinDown();
        }
    }

    public void intakeBall(){
        if (sorter.moveToIntake(DecodeColor.EMPTY)) {
            sorter.startIntake();
            Thread intakeThread = new Thread(() -> {
                try {wait(250);}
                catch (InterruptedException ignored){}
                sorter.waitForIntake();
                sorter.stopIntake();
            });
            intakeThread.start();
        }
    }

    public void aim(double angle, double speed){
        outtake.aim(angle,speed);
    }

    public void modifyAim(double angle, double speed){
        outtake.aim(outtake.getAimAngle() + angle, outtake.getAimSpeed()+speed);
    }

    public void smartAim(){

    }
}

