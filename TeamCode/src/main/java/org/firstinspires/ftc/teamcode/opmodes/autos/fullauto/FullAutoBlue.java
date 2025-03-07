package org.firstinspires.ftc.teamcode.opmodes.autos.fullauto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Full Auto Blue", preselectTeleOp = "TeleOp", group = "Blue")
public class FullAutoBlue extends FullAutoBase {
    @Override
    public void init() {
        NET_ZONE_X = TILE * 1.5;
        NET_ZONE_Y = TILE * 1.5;
        NET_ZONE_YAW_RAD = Math.toRadians(45);
        PARK_X = TILE * 2;
        PARK_Y = TILE * 0.5;
        PARK_YAW_RAD = Math.toRadians(270);
        super.init();
    }
}
