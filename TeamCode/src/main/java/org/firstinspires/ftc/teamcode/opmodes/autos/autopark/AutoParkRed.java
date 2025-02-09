package org.firstinspires.ftc.teamcode.opmodes.autos.autopark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Park Red", preselectTeleOp = "TeleOp", group = "Red")
public class AutoParkRed extends AutoParkBase {
    @Override
    public void init() {
        PARK_X = TILE*2.5;
        PARK_Y = TILE*-2.5;
        super.init();
    }
}
