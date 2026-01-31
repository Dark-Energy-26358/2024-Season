package org.firstinspires.ftc.teamcode.enums;

public enum ObeliskPattern {
    GREEN_PURPLE_PURPLE(21, 0b011),
    PURPLE_GREEN_PURPLE(22, 0b101),
    PURPLE_PURPLE_GREEN(23, 0b110);
    private final int id;
    private final int patternBits;

    ObeliskPattern(int id, int patternBits) {
        this.id = id;
        this.patternBits = patternBits;
    }
    public int getId() {
        return this.id;
    }

    public int getPatternBits() {
        return this.patternBits;
    }
}
