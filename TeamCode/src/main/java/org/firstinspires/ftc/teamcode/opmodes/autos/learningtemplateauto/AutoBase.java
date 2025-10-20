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
            case 2:
                TARGET_Y = -60;
                robot.mecanumDrive.drive(0, 0.2, 0);
                if (robot.getPosition().y <= TARGET_Y) {
                    stage++;
                    telemetry.addLine("stage increased to 2");
                }
                break;
            case 3:
                TARGET_X = 20;
                robot.mecanumDrive.drive(0.2, 0, 0);
                if (robot.getPosition().x >= TARGET_X) {
                    stage++;
                    telemetry.addLine("stage increased to 3");
                }
                break;
            case 4:
                TARGET_Y = -61;
                robot.mecanumDrive.drive(0, -0.2, 0);
                if (robot.getPosition().y >= TARGET_Y) {
                    stage++;
                    telemetry.addLine("stage increased to 2");
                }
                break;
            case 5:
            case 6:
                TARGET_X = 36;
                robot.mecanumDrive.drive(0.2, 0, 0);
                if (robot.getPosition().x >= TARGET_X) {
                    stage++;
                    telemetry.addLine("stage increased to 3");
                }
                break;
            case 7:
                TARGET_Y = 60;

                if (!(robot.getPosition().y >= TARGET_Y)) {
                    robot.mecanumDrive.drive(0, -0.2, 0);
                }
                break;
        }

        super.loop(); // Don't delete this!
    }
}
