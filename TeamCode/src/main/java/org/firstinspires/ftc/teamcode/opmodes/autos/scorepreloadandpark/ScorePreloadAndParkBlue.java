package org.firstinspires.ftc.teamcode.opmodes.autos.scorepreloadandpark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Score Preload and Park Blue", preselectTeleOp = "TeleOp", group = "Blue")
public class ScorePreloadAndParkBlue extends ScorePreloadAndParkBase {
    @Override
    public void init() {
        NET_ZONE_X = TILE * 2;
        NET_ZONE_Y = TILE * 2;
        NET_ZONE_YAW_RAD = Math.toRadians(45);
        PARK_X = TILE * 2;
        PARK_Y = TILE * 1;
        PARK_YAW_RAD = Math.toRadians(270);
        super.init();
    }
}
