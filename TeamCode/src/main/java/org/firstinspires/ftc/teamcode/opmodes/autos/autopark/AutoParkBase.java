package org.firstinspires.ftc.teamcode.opmodes.autos.autopark;

import org.firstinspires.ftc.teamcode.opmodes.autos.AutoBase;

public class AutoParkBase extends AutoBase { // TODO: TEST THIS
    final double TILE = 24;

    double PARK_X = 0.0; // CHANGE_ME!!
    double PARK_Y = 0.0; // CHANGE_ME!!

    boolean ACHIEVED_POSITION = false;

    @Override
    public void init() {
        super.init();
    }

    public void loop() {
        if (!ACHIEVED_POSITION) {
            ACHIEVED_POSITION = robot.mecanumDrive.driveToPosition(PARK_X, PARK_Y, position, angles);
        }
        super.loop();
    }
}
