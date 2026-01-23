package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.enums.DecodeColor;
import org.firstinspires.ftc.teamcode.enums.State;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Robot robot = new Robot();
    ColorSensor sensor;
    boolean yPressed = false;
    State state = State.INIT;
    boolean intakeRunning = false;
    int stateFrames;

    @Override
    public void init() {
        robot.init(hardwareMap);
        sensor = hardwareMap.colorSensor.get("colorSensor0");
    }

    @Override
    public void loop() {
//        robot.updatePosition();

        double speed = 0.2 + gamepad1.left_trigger*0.3 + gamepad2.right_trigger*0.3;

        double forward = -gamepad1.left_stick_y * speed;
        double right = gamepad1.left_stick_x * speed;
        double rotate = (-gamepad1.right_stick_x) * 0.8;

        robot.mecanumDrive.drive(forward, right, rotate);

        if (stateFrames > 0) {
            stateFrames--;
            if (stateFrames == 0) {
                stateFrames = 25;
                switch (state) {
                    case INTAKE_PROCESS:
                        robot.sorter.increasePos(2);
                        state = State.SORTING_INTAKE;
                        break;
                    case SORTING_FOR_LAUNCH:
                        robot.shooter.shoot(1);
                        state = State.LAUNCHING;
                        break;
                    case LAUNCHING:
                        robot.sorter.increasePos(1);
                        state = State.FINISH_LAUNCHING;
                        break;
                    case FINISH_LAUNCHING:
                        robot.shooter.reset();
                        state = State.INTAKE_READY;
                        stateFrames = 0;
                        break;
                    case SORTING_INTAKE:
                        if (intakeRunning) {
                            state = State.INTAKE;
                        } else {
                            state = State.INTAKE_READY;
                        }
                        stateFrames = 0;
                        break;
                    default:
                        state = State.INTAKE_READY;
                        stateFrames = 0;
                }
            }
        }

        if (state.equals(State.INTAKE) && !robot.sorter.getColor(0).equals(DecodeColor.EMPTY)) {
            state = State.INTAKE_PROCESS;
            stateFrames = 10;
        }

        // Passively sort if we have an empty slot
        if (robot.sorter.getNumberOfBalls() < 3) {
            if (stateFrames == 0 && (state.equals(State.INTAKE_READY) || state.equals(State.INIT))) {
                // Make the intake slot empty
                if (!robot.sorter.getColor(0).equals(DecodeColor.EMPTY)) {
                    robot.sorter.increasePos(2);
                    if (!state.equals(State.SORTING_INTAKE)) {
                        state = State.SORTING_TO_READY;
                    }
                    stateFrames = 25;
                } else {
                    if (state.equals(State.INIT)) {
                        state = State.INTAKE_READY;
                    }
                }
            }
        } else {
            if (intakeRunning) {
                gamepad1.rumble(5000);
                robot.intake.stop();
                intakeRunning = false;
                if (state.equals(State.INTAKE)) {
                    state = State.INTAKE_READY;
                }
            }
        }

        // Green launch
        if (gamepad1.aWasPressed()) {
            if (state.equals(State.INTAKE_READY)) {
                if (robot.sorter.getColor(0).equals(DecodeColor.GREEN)) {
                    robot.sorter.increasePos(3);
                    state = State.SORTING_FOR_LAUNCH;
                    stateFrames = 25;
                } else if (robot.sorter.getColor(1).equals(DecodeColor.GREEN)) {
                    robot.sorter.increasePos(-1);
                    state = State.SORTING_FOR_LAUNCH;
                    stateFrames = 25;
                } else if (robot.sorter.getColor(2).equals(DecodeColor.GREEN)) {
                    robot.sorter.increasePos(1);
                    state = State.SORTING_FOR_LAUNCH;
                    stateFrames = 25;
                } else {
                    gamepad1.rumble(1000);
                }
            }
        }

        // Purple launch
        if (gamepad1.xWasPressed()) {
            if (state.equals(State.INTAKE_READY)) {
                if (robot.sorter.getColor(0).equals(DecodeColor.PURPLE)) {
                    robot.sorter.increasePos(3);
                    state = State.SORTING_FOR_LAUNCH;
                    stateFrames = 25;
                } else if (robot.sorter.getColor(1).equals(DecodeColor.PURPLE)) {
                    robot.sorter.increasePos(-1);
                    state = State.SORTING_FOR_LAUNCH;
                    stateFrames = 25;
                } else if (robot.sorter.getColor(2).equals(DecodeColor.PURPLE)) {
                    robot.sorter.increasePos(1);
                    state = State.SORTING_FOR_LAUNCH;
                    stateFrames = 25;
                }
            }
        }

        if (gamepad1.yWasPressed()) {
            if (!yPressed) {
                robot.shooter.shoot(1);
                yPressed = true;
            } else {
                robot.shooter.reset();
                yPressed = false;
            }
        }
        if (gamepad1.bWasPressed()) {
            if (!intakeRunning) {
                robot.intake.start();
                state = State.INTAKE;
                intakeRunning = true;
            } else {
                robot.intake.stop();
                state = State.INTAKE_READY;
                intakeRunning = false;
            }
        }

        if (gamepad1.dpadUpWasPressed()) {
            robot.shooter.aim(robot.shooter.getAim()+0.1);
        } else if (gamepad1.dpadDownWasPressed()) {
            robot.shooter.aim(robot.shooter.getAim()-0.1);
        }

        telemetry.addData("Sorter Position", robot.sorter.getPos());
        telemetry.addData("State", state.name());
        telemetry.addData("State frames", stateFrames);

        telemetry.addData("Position 0 color", robot.sorter.getColor(0));
        telemetry.addData("Position 1 color", robot.sorter.getColor(1));
        telemetry.addData("Position 2 color", robot.sorter.getColor(2));
    }
}
