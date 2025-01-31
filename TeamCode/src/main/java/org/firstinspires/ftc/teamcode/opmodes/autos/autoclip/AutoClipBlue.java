package org.firstinspires.ftc.teamcode.opmodes.autos.autoclip;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Clip Blue", preselectTeleOp = "TeleOp", group = "Blue")
public class AutoClipBlue extends AutoClipBase {
    @Override
    public void init() { // TODO: Change to real tested numbers
        CLIP_X = 0;
        CLIP_Y = TILE*1.5;
        CLIP_YAW = 180;
        PICKUP_X = TILE*-2;
        PICKUP_Y = TILE*2;
        super.init();
    }
}
