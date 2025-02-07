package org.firstinspires.ftc.teamcode.opmodes.autos.autopark;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Clip Blue", preselectTeleOp = "TeleOp", group = "Blue")
public class AutoParkBlue extends AutoParkBase {
    @Override
    public void init() {
        PARK_X = TILE*-2;
        PARK_Y = TILE*2;
        super.init();
    }
}
