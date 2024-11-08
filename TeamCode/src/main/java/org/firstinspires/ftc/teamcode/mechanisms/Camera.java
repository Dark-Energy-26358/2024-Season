package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


import java.util.ArrayList;
import java.util.List;


public class Camera {
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;
    private List<Integer> idsFound = new ArrayList<Integer>(6);
    private Pose3D pos = null;

    public void init(HardwareMap hardwareMap) {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcessor);
    }

    private void updateCamera() {
        pos = null;
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        idsFound.clear();
        for (AprilTagDetection detection : currentDetections) {
            idsFound.add(detection.id);
            pos = detection.robotPose;
        }
    }

    public Position getPosition(){
        updateCamera();
        if(pos != null) {
            return pos.getPosition();
        }
        return null;
    }
    public YawPitchRollAngles getOrientation(){
        updateCamera();
        if(pos != null) {
            return pos.getOrientation();
        }
        return null;
    }

    public List<Integer> getCurrentTags(){
        updateCamera();
        return idsFound;
    }

    public void stop(){
        visionPortal.stopStreaming();
    }

}