package org.firstinspires.ftc.teamcode.opmodes.autos.scorepreloadandpark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Score Preload and Park Red", preselectTeleOp = "TeleOp", group = "Red")
public class ScorePreloadAndParkRed extends ScorePreloadAndParkBase {
    @Override
    public void init() {
        NET_ZONE_X = TILE * -1.5;
        NET_ZONE_Y = TILE * -1.5;
        NET_ZONE_YAW_RAD = Math.toRadians(180 + 45);
        PARK_X = TILE * -2;
        PARK_Y = TILE * -0.5;
        PARK_YAW_RAD = Math.toRadians(90);
        super.init();
    }
}
