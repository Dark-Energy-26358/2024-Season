package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp()
public class teleop extends OpMode {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    MecanumDrive drive = new MecanumDrive();

    @Override
    public void init() {
        drive.init(hardwareMap);

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcessor);
    }

    public void init_loop() {
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        StringBuilder idsFound = new StringBuilder();
        Pose3D pos = null;
        for (AprilTagDetection detection : currentDetections) { idsFound.append(detection.id);
            pos = detection.robotPose;
            idsFound.append(' ');

        }
        telemetry.addData("April Tags", idsFound);
        if(pos != null) {
            telemetry.addData("Robot Position from April Tags", pos.getPosition());
        }
    }

    public void start() {
        visionPortal.stopStreaming(); }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y/3;
        double right = gamepad1.left_stick_x/3;
        double rotate = gamepad1.right_stick_x/3;

        drive.drive(forward, right, rotate);
    }
}