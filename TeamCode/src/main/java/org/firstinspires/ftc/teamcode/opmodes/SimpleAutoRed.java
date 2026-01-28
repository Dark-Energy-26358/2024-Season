//package org.firstinspires.ftc.teamcode.opmodes;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.firstinspires.ftc.teamcode.Robot;
//
//@Autonomous(name="Red: Simple Auto", group="Red")
//public class SimpleAutoRed extends OpMode {
//
//    // Number of inches in a tile
//    final double TILE_SIZE = 24.0;
//
//    Robot robot = new Robot();
//
//    @Override
//    public void init() {
//        robot.init(hardwareMap);
//    }
//
//    @Override
//    public void loop() {
//        boolean onSpot = robot.mecanumDrive.driveToPosition(TILE_SIZE*1, TILE_SIZE*1,
//                45,
//                robot.getPosition(), robot.getOrientation()
//        );
//
//        if (onSpot) {
//            // Launch the ball
//            robot.shooter.on(1);
//        }
//    }
//}
