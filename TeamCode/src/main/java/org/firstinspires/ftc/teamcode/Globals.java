package org.firstinspires.ftc.teamcode;

public final class Globals {
    private static Globals INSTANCE;
    private String info = "Global variables to be shared between autonoumous and teleop";

    private Boolean manipArmAccurate = false;

    private Globals() {
        // basing this code on https://www.baeldung.com/java-singleton#singleton
    }

    public static Globals getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Globals();
        }
        return INSTANCE;
    }

    public Boolean getManipArmAccurate() {
        return manipArmAccurate;
    }

    public void setManipArmAccurate(Boolean manipArmAccurate) {
        this.manipArmAccurate = manipArmAccurate;
    }
}
