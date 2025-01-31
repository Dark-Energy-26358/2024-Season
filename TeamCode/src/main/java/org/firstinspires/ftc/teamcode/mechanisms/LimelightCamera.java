package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;


public class LimelightCamera {
    private Pose3D pos = new Pose3D(new Position(DistanceUnit.INCH, 0, 72, 0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0));
    private Limelight3A limelight;

    private boolean live = false;

    public void init(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */
        limelight.start();
    }

    private void updateCamera() {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                pos = result.getBotpose();
                live = true;
            } else {
                live = false;
            }
        }
    }

    public Position getPosition() {
        updateCamera();
        return pos.getPosition().toUnit(DistanceUnit.INCH);
    }

    public YawPitchRollAngles getOrientation() {
        updateCamera();
        return pos.getOrientation();
    }

    public boolean isLive() {
        updateCamera();
        return live;
    }

    public void stop() {
        limelight.stop();
    }
}
