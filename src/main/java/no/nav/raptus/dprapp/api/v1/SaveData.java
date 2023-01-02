package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.model.ActivityType;
import no.nav.raptus.dprapp.model.Data;
import no.nav.raptus.dprapp.model.Day;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SaveData {

    @PostMapping(path = "/save")
    public ResponseEntity<String> save(@RequestBody Data data) {
        log.warn(data.toString());
        // Sjekke at bruker i token "eier" innsendte meldekortet
        // Lagre data

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/send")
    public ResponseEntity<String> send(@RequestBody Data data) {
        // Lagre
        save(data);
        // Kontroller
        // Svar
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Data> get(@PathVariable String id) {
        log.warn(id);

        List<Day> days = new ArrayList<>();
        days.add(Day.builder().date(LocalDate.of(2022, 12, 5)).type(ActivityType.WORK).hours(4d).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 6)).type(ActivityType.WORK).hours(3d).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 7)).type(ActivityType.MEASURES).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 8)).type(ActivityType.MEASURES).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 9)).type(ActivityType.ILLNESS).build());

        days.add(Day.builder().date(LocalDate.of(2022, 12, 12)).type(ActivityType.ILLNESS).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 13)).type(ActivityType.MEASURES).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 14)).type(ActivityType.MEASURES).build());

        Data data = new Data();
        data.setId(id);
        data.setQuestionWork(true);
        data.setQuestionMeasures(true);
        data.setQuestionIllness(true);
        data.setQuestionVacation(false);
        data.setDays(days);
        data.setQuestionProceed(false);

        return ResponseEntity.ok(data);
    }
}
