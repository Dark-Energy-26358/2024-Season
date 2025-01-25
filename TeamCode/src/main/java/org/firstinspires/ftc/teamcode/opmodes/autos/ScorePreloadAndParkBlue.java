package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Score Preload and Park Blue", preselectTeleOp = "TeleOp", group = "Blue")
public class ScorePreloadAndParkBlue extends ScorePreloadAndParkBase {

    final double NET_ZONE_X = TILE*2.5;
    final double NET_ZONE_Y = TILE*2.5;
    final double PARK_X = TILE*2;
    final double PARK_Y = TILE*1;

}
