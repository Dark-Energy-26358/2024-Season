//package org.firstinspires.ftc.teamcode.opmodes;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import org.firstinspires.ftc.teamcode.Robot;
//
//@Autonomous(name="Red: Super Auto", group="Red")
//public class SuperAutoRed extends OpMode {
//
//    // Number of inches in a tile
//    final double TILE_SIZE = 24.0;
//
//    int sorterCooldown = 0;
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
//        sorterCooldown--;
//
//        if (robot.sorter.getNumberOfBalls() > 0) {
//            robot.intake.start();
//
//            boolean onSpot = robot.mecanumDrive.driveToPosition(TILE_SIZE*1, TILE_SIZE*1,
//                    45,
//                    robot.getPosition(), robot.getOrientation()
//            );
//
//            if (onSpot && sorterCooldown == 0) {
//                if (robot.sorter.getColor(0) != null && robot.sorter.getPos() % 2 == 0) {
//                    // Launch the ball
//                    robot.shooter.on(1);
//                } else {
//                    robot.sorter.increasePos(1);
//                    sorterCooldown = 100;
//                }
//            }
//        } else {
//            robot.intake.stop();
//
//            robot.sorter.gotoPos(0);
//
//            boolean onSpot = robot.mecanumDrive.driveToPosition(TILE_SIZE*0.5, TILE_SIZE*2,
//                    90,
//                    robot.getPosition(), robot.getOrientation()
//            );
//        }
//    }
//}
