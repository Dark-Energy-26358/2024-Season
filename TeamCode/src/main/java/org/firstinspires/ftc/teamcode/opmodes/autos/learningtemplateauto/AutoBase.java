package org.firstinspires.ftc.teamcode.opmodes.autos.learningtemplateauto;

public class AutoBase extends org.firstinspires.ftc.teamcode.opmodes.autos.AutoBase {
    final double TILE = 24;

    int stage = 1;
    double TARGET_X = 0.0; // CHANGE_ME!!
    double TARGET_Y = 0.0; // CHANGE_ME!!

    @Override
    public void init() {
        super.init();
    }

    public void loop() {
        switch (stage) {
            case 1:
                if (robot.mecanumDrive.driveToPosition(TARGET_X, TARGET_Y, position, angles))
                    stage++;
                break;
            case 2:
                // TODO: Go somewhere else here
                break; // Don't forget this break!
        }

        super.loop(); // Don't delete this!
    }
}
