package org.firstinspires.ftc.teamcode.mechanisms;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Camera {
    private AprilTagProcessor aprilTagProcessor;
    private List<Integer> idsFound = new ArrayList<Integer>(6);
    private Pose3D pos = null;

    public void init(HardwareMap hardwareMap) {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        VisionPortal visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcessor);
    }

    private void updateCamera() {
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        idsFound.clear();
        for (AprilTagDetection detection : currentDetections) {
            idsFound.add(detection.id);
            pos = detection.robotPose;
        }
    }

    public Position getPos(){
        updateCamera();
        if(pos != null) {
            return pos.getPosition();
        }
        return null;
    }

    public List<Integer> getCurrentTags(){
        updateCamera();
        return idsFound;
    }

}