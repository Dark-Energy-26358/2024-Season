package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot; import com.qualcomm.robotcore.eventloop.opmode.OpMode; import com.qualcomm.robotcore.eventloop.opmode.TeleOp; import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit; import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;
@TeleOp()
public class fieldRelativeTeleop extends OpMode {
    MecanumDrive drive = new MecanumDrive(); IMU imu;
    @Override
    public void init() {
        drive.init(hardwareMap);
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }
    private void driveFieldRelative(double forward, double right, double rotate) { double robotAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        // convert to polar
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(forward, right);
        // rotate angle
        theta = AngleUnit.normalizeRadians(theta - robotAngle);
        // convert back to cartesian
        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);
        drive.drive(newForward, newRight, theta);
    }
    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y/10;
        double right = gamepad1.left_stick_x/10;
        double rotate = gamepad1.right_stick_x/10;

        driveFieldRelative(forward, right, rotate);
    }
}