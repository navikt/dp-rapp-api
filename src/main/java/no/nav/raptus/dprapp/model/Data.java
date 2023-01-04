package no.nav.raptus.dprapp.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Data {

    long id;
    private Boolean questionWork;
    private Boolean questionMeasures;
    private Boolean questionIllness;
    private Boolean questionVacation;
    private List<Day> days;
    private Boolean questionProceed;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
