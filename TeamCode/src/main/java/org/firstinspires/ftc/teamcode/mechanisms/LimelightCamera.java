package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;


public class LimelightCamera {
    private Pose3D pos = new Pose3D(new Position(), new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0));
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
        updateCamera();
        return live;
    }
    public void stop(){
        limelight.stopStreaming();
    }

}