package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.enums.ObeliskPattern;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;


public class LimelightCamera {
    private Pose3D pos = new Pose3D(new Position(DistanceUnit.INCH, 0, 72, 0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0));
    private Limelight3A limelight;

    private ObeliskPattern obeliskPattern = null;

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
            for (LLResultTypes.ClassifierResult classifierResult: result.getClassifierResults()) {
                switch (classifierResult.getClassId()) {
                    case 21:
                        obeliskPattern = ObeliskPattern.GREEN_PURPLE_PURPLE;
                        break;
                    case 22:
                        obeliskPattern = ObeliskPattern.PURPLE_GREEN_PURPLE;
                        break;
                    case 23:
                        obeliskPattern = ObeliskPattern.PURPLE_PURPLE_GREEN;
                        break;
                }
            }
            if (result.isValid()) {
                pos = result.getBotpose();
                // TODO: Automatically update Optical Odometry?
                live = true;
            }else {
                live = false;
            }
        }
    }

    public Position getPosition(){
        updateCamera();
        if(pos != null) {
            return pos.getPosition().toUnit(DistanceUnit.INCH);
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

    public ObeliskPattern getObeliskPattern() {
        updateCamera();
        return obeliskPattern;
    }

    public boolean isLive() {
        updateCamera();
        return live;
    }
    public void stop(){
        limelight.stop();
    }

}
