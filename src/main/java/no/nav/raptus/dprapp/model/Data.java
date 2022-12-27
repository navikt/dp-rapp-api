package no.nav.raptus.dprapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Data {

    @Getter
    @Setter
    private Boolean questionWork;

    @Getter
    @Setter
    private Boolean questionMeasures;

    @Getter
    @Setter
    private Boolean questionIllness;

    @Getter
    @Setter
    private Boolean questionVacation;

    @Getter
    @Setter
    private List<Day> days;

    @Getter
    @Setter
    private Boolean questionProceed;

    @Override
    public String toString() {
        var daysStr = new StringBuilder();
        for (Day day : days) {
            daysStr.append(day);
        }

        return String.format(
                """
                {
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
