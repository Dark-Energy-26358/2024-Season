package org.firstinspires.ftc.teamcode.opmodes.autos.supergoaty;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Super GOATy", preselectTeleOp = "TeleOp", group = "Rizzy Grizzy") // TODO: Change this name
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
                if (robot.mecanumDrive.driveToPosition(TILE*-2.61,TILE*-1, position, angles))
                    stage++;
                break;
            case 2:
                if (robot.mecanumDrive.driveToPosition(TILE*-1.5,TILE*-1.5, position, angles))
                    stage++;
                break; // Don't forget this break!
            case 3:
                if (robot.mecanumDrive.driveToPosition(TILE*-0,TILE*-2, position, angles))
                    stage++;
                break;
            case 4:
                if (robot.mecanumDrive.driveToPosition(TILE*2,TILE*-1, position, angles))
                    stage++;
                break;
            case 5:
                if (robot.mecanumDrive.driveToPosition(TILE*2.5,TILE*2.5, position, angles))
                    stage++;
                break;
        }

        super.loop(); // Don't delete this!
    }
}
