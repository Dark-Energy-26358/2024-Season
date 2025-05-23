package org.firstinspires.ftc.teamcode.opmodes.autos.learningtemplateauto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "[CHANGE THIS] Blue", preselectTeleOp = "TeleOp", group = "Blue") // TODO: Change this name
public class AutoBlue extends AutoBase {
    @Override
    public void init() {
        TARGET_X = TILE * 1;
        TARGET_Y = TILE * 0.5;
        super.init();
    }
}
