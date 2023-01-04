package no.nav.raptus.dprapp.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.raptus.dprapp.db.repository.MellomlagretMeldeperiodeDAO;
import no.nav.raptus.dprapp.model.ActivityType;
import no.nav.raptus.dprapp.model.Data;
import no.nav.raptus.dprapp.model.Day;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Mellomlagring.class)
public class MellomlagringTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MellomlagretMeldeperiodeDAO mellomlagretMeldeperiodeDAO;

    static {
        System.setProperty("DB_USERNAME", "test");
        System.setProperty("KAFKA_BROKERS", "kafka.nav.no:26484");
    }


    @Test
    public void skalLagreOgHente() throws Exception {
        List<Day> days = new ArrayList<>();
        days.add(Day.builder().date(LocalDate.of(2022, 12, 5)).type(ActivityType.WORK).hours(1d).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 6)).type(ActivityType.WORK).hours(2d).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 7)).type(ActivityType.MEASURES).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 12)).type(ActivityType.WORK).hours(3d).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 13)).type(ActivityType.WORK).hours(4d).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 15)).type(ActivityType.MEASURES).build());
        days.add(Day.builder().date(LocalDate.of(2022, 12, 16)).type(ActivityType.ILLNESS).build());

        long id = 1L;
        Data data = Data.builder()
                .id(id)
                .questionWork(true)
                .questionMeasures(true)
                .questionIllness(true)
                .questionVacation(false)
                .days(days)
                .build();
        byte[] dataBytes = new ObjectMapper().writeValueAsBytes(data);

        mockMvc.perform(post("/api/v1/lagre").contentType(MediaType.APPLICATION_JSON).content(dataBytes))
                .andExpect(status().isOk());
        verify(mellomlagretMeldeperiodeDAO).lagre(id, data.toString());

        when(mellomlagretMeldeperiodeDAO.hente(id)).thenReturn(data.toString());
        MvcResult result = mockMvc.perform(get("/api/v1/hente/" + id))
                .andExpect(status().isOk())
                .andReturn();


        String content = result.getResponse().getContentAsString();
        assertEquals(data.toString(), content);
    }
}
