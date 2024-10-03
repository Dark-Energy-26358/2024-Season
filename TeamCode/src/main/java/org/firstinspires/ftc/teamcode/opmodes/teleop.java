package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp()
public class teleop extends OpMode {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    MecanumDrive drive = new MecanumDrive();

    @Override
    public void init() {
        drive.init(hardwareMap);

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcessor);
    }


    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y/10;
        double right = gamepad1.left_stick_x/10;
        double rotate = gamepad1.right_stick_x/10;

        drive.drive(forward, right, rotate);
        telemetry.addData("robot rotation", drive.rotation);
    }
}