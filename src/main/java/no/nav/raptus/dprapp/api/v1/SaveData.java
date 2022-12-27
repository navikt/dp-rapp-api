package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SaveData {

    @PostMapping(path = "/save")
    public ResponseEntity<String> save(Data data) {
        log.warn(data.toString());
        return ResponseEntity.ok().build();
    }
}

class Data {
    Boolean questionWork;
    Boolean questionMeasures;
    Boolean questionIllness;
    Boolean questionVacation;
    Day[] days;
    Boolean questionProceed;

    @Override
    public String toString() {
        var daysStr = new StringBuilder();
        for (Day day : days) {
            daysStr.append(day);
        }

        return String.format(
                """
                {,
                    questionWork: %s,
                    questionMeasures: %s,
                    questionIllness: %s,
                    questionVacation: %s,
                    [
                        %s
                    ],
                    questionProceed: %s,
                }
                """,
                questionWork,
                questionMeasures,
                questionIllness,
                questionVacation,
                daysStr,
                questionProceed
        );
    }
}

class Day {
    String date;
    String type;
    Double hours;

    @Override
    public String toString() {
        return String.format("{ date: %s, type: %s, hours: %.1f }", date, type, hours);
    }
}
