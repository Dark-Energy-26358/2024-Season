package org.firstinspires.ftc.teamcode.opmodes.autos.learningtemplateauto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Ethan's team", preselectTeleOp = "TeleOp", group = "Ethan")
public class AutoBase extends org.firstinspires.ftc.teamcode.opmodes.autos.AutoBase {
    final double TILE = 24;

    int stage = 1;
    double TARGET_X = 0.0; // CHANGE_ME!!
    double TARGET_Y = 0.0
            ; // CHANGE_ME!!

    @Override
    public void init() {
        super.init();
    }

    public void loop() {
        switch (stage) {
            case 1:
                TARGET_X = -60;
                TARGET_Y = -40;
                if (robot.mecanumDrive.driveToPosition(TARGET_X, TARGET_Y, position, angles))
                    stage++;
                break;
            case 2:
                TARGET_X = 48;
                TARGET_Y = -36;
                if (robot.mecanumDrive.driveToPosition(TARGET_X, TARGET_Y, position, angles))
                    stage++;
                break;
            case 3:
                TARGET_X = 48;
                TARGET_Y = 60;
                if (robot.mecanumDrive.driveToPosition(TARGET_X, TARGET_Y, position, angles))
                    stage++;
                break;
        }

        super.loop(); // Don't delete this!
    }
}
