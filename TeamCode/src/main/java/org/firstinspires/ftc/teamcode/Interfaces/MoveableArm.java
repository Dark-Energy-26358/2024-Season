package org.firstinspires.ftc.teamcode.Interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface MoveableArm {

    void init(HardwareMap hardwareMap); // Add your init code. Your motors should be initialized here.

    void stop();//stops arm

    void setTargetArmExtension(int targetExtension); //set your target extension, the arm will go to that position. this should be in inches.
    void setTargetArmRotation(int targetRotation); //set your target rotation, the arm will go to that rotation. this should be in degrees. 90 degrees is verticle, 0 in flat on the robot and 180 it flat away from the robot.

    int getTargetArmExtension(); //gets target arm extension in inches
    double getTargetArmRotation(); //gets target arm rotation in degrees

    int getCurrentArmExtension(); //gets current arm extension in inches
    double getCurrentArmRotation(); //gets current arm rotation in degrees

    void setTargetManipulatorHandPosition(int targetHandPosition); // set manipulator hand(the piece in contact with the piece we are contacting) position. this may be degrees or inches depinding on hand.

}
