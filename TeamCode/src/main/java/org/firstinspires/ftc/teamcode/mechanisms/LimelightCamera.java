package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.ArrayList;
import java.util.List;


public class LimelightCamera {
    private Pose3D pos = null;
    private Limelight3A limelight;

    private boolean live = false;

    public void init(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */
        limelight.start();
    }// }

    private void updateCamera() {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                pos = result.getBotpose();
                // TODO: Automatically update Optical Odometry?
            }
            live = true;
        } else {
            live = false;
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

    public boolean isLive() {
        return live;
    }
    public void stop(){
        limelight.stop();
    }

}
