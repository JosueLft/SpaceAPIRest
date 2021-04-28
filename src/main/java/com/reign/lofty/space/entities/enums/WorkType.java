package com.reign.lofty.space.entities.enums;

public enum WorkType {

    MANGA(1),
    NOVEL(2);

    private int code;

    private WorkType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static WorkType valueOf(int code) {
        for(WorkType value : WorkType.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Type code");
    }
}