package no.nav.raptus.dprapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Day {

    @Getter
    @Setter
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    @Getter
    @Setter
    private ActivityType type;

    @Getter
    @Setter
    private Double hours;

    @Override
    public String toString() {
        return String.format("{ date: %s, type: %s, hours: %.1f }", date, type, hours);
    }
}
