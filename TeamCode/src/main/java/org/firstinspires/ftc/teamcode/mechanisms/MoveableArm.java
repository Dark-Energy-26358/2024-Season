package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface MoveableArm {
    void init(HardwareMap hardwareMap); // Add your init code. Your motor should be initialized here.

    void run(float power); // Preferably -1 to 1, positive is forward, negative is backward. The motor should run through this function.
}
