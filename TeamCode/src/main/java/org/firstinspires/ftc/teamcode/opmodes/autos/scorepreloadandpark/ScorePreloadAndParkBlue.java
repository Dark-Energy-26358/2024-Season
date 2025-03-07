package org.firstinspires.ftc.teamcode.opmodes.autos.scorepreloadandpark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Score Preload and Park Blue", preselectTeleOp = "TeleOp", group = "Blue")
public class ScorePreloadAndParkBlue extends ScorePreloadAndParkBase {
    @Override
    public void init() {
        NET_ZONE_X = TILE * 2.25;
        NET_ZONE_Y = TILE * 2;
        NET_ZONE_YAW_RAD = Math.toRadians(60);
        PARK_X = TILE * 1;
        PARK_Y = TILE * 0.5;
        PARK_YAW_RAD = Math.toRadians(90);
        super.init();
    }
}
