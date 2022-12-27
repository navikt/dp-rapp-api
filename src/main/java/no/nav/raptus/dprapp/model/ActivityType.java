package no.nav.raptus.dprapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActivityType {
    WORK,
    ILLNESS,
    MEASURES,
    VACATION;

    @JsonCreator
    public static ActivityType getByCode(String code) {
        if (code == null) throw new IllegalArgumentException("Cannot find ActivityType");

        return ActivityType.valueOf(code.toUpperCase());
    }
}
