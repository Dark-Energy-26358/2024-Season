package org.firstinspires.ftc.teamcode.opmodes.autos.learningtemplateauto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "[CHANGE THIS] Red", preselectTeleOp = "TeleOp", group = "Red") // TODO: Change this name
public class AutoRed extends AutoBase {
    @Override
    public void init() {
        TARGET_X = TILE * -1;
        TARGET_Y = TILE * -0.5;
        super.init();
    }
}
