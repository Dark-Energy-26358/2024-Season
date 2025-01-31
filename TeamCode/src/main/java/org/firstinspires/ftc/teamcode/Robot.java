package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.mechanisms.LimelightCamera;
import org.firstinspires.ftc.teamcode.mechanisms.OpticalOdometry;
import org.firstinspires.ftc.teamcode.mechanisms.StageOneAscentArms;
import org.firstinspires.ftc.teamcode.mechanisms.ManipulatorArm;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

public class Robot {
    public Globals globals = Globals.getInstance();
    public MecanumDrive mecanumDrive = new MecanumDrive();
    public ManipulatorArm manipulatorArm = new ManipulatorArm();
//    public StageTwoAscentArms stageTwoAscentArms = new StageTwoAscentArms();
    public StageOneAscentArms stageOneAscentArms = new StageOneAscentArms();
    public LimelightCamera camera = new LimelightCamera();
    public OpticalOdometry opticalOdometry = new OpticalOdometry();

    public void init(HardwareMap hardwareMap){
        mecanumDrive.init(hardwareMap);
        manipulatorArm.init(hardwareMap);
        //stageTwoAscentArms.init(hardwareMap);
        stageOneAscentArms.init(hardwareMap);
        camera.init(hardwareMap);
        opticalOdometry.init(hardwareMap);
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
            return new YawPitchRollAngles(AngleUnit.DEGREES, opticalOdometry.getPosition().h, camera.getOrientation().getPitch(), camera.getOrientation().getRoll(),0);
        }
    }

    // call every frame
    public void updatePosition(){
        if (camera.isLive()) {
            opticalOdometry.setPosition(new SparkFunOTOS.Pose2D(camera.getPosition().x, camera.getPosition().y, camera.getOrientation().getYaw()));
        }
    }
}

