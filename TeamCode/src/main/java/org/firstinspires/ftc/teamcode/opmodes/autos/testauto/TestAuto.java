package org.firstinspires.ftc.teamcode.opmodes.autos.testauto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmodes.autos.AutoBase;
import org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow.Pathfinding;
import org.firstinspires.ftc.teamcode.processing.pathfinding.pointflow.Vector;

@Autonomous(name = "Test Auto", preselectTeleOp = "TeleOp")
public class TestAuto extends AutoBase { // TODO: TEST THIS
    final double TILE = 24;

    double TARGET_X = -TILE*2.5; // CHANGE_ME!!
    double TARGET_Y = -TILE*2.5; // CHANGE_ME!!

    boolean ACHIEVED_POSITION = false;

    @Override
    public void init() {
        super.init();
    }

    public void loop() {
        if (!ACHIEVED_POSITION) {
            Vector movement = Pathfinding.pathfind(position, TARGET_X, TARGET_Y);
            robot.mecanumDrive.moveFieldRelative(movement.x, movement.y, 0, angles.getYaw(AngleUnit.RADIANS));
        }

        super.loop();
    }
}
