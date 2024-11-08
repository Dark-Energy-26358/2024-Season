package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.Robot;

public class MecanumDrive {
        private DcMotor frontLeftMotor;
        private DcMotor frontRightMotor;
        private DcMotor backLeftMotor;
        private DcMotor backRightMotor;

        public final double WHEEL_CIRCUM = 11.87373602362;

        public double rotation = 0;
        public Position position = new Position();

        public Camera camera;

        public void init(HardwareMap hardwareMap) {
                position.unit = DistanceUnit.INCH;
                frontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
                frontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
                backLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
                backRightMotor = hardwareMap.dcMotor.get("back_right_motor");

                backRightMotor.setDirection(DcMotor.Direction.REVERSE);
                frontRightMotor.setDirection(DcMotor.Direction.REVERSE);

                frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        private void setPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
                double maxSpeed = 1;
                maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
                maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
                maxSpeed = Math.max(maxSpeed, Math.abs(backLeftPower));
                maxSpeed = Math.max(maxSpeed, Math.abs(backRightPower));

                frontLeftPower /= maxSpeed;
                frontRightPower /= maxSpeed;
                backLeftPower /= maxSpeed;
                backRightPower /= maxSpeed;

                frontLeftMotor.setPower(frontLeftPower);
                frontRightMotor.setPower(frontRightPower);
                backLeftMotor.setPower(backLeftPower);
                backRightMotor.setPower(backRightPower);
        }
        private void resetEncoders(){
                frontLeftMotor.getCurrentPosition();
        }
        public void drive(double forward, double right, double rotate) {
                double frontLeftPower = forward + right - rotate;
                double frontRightPower = forward - right + rotate;
                double backLeftPower = forward - right - rotate;
                double backRightPower = forward + right + rotate;

                if (camera.getOrientation() != null && camera.getPosition() != null){
                        rotation = camera.getOrientation().getYaw();
                        position = camera.getPosition();

                }

                setPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        }
}
