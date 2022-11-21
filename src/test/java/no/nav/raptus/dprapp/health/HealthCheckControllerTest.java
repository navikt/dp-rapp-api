package no.nav.raptus.dprapp.health;

import no.nav.raptus.dprapp.db.repository.KallLoggDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Enhetstester for {@link HealthCheckController}.
 */
@WebMvcTest(HealthCheckController.class)
@Import({HealthCheckDbProbe.class})
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KallLoggDAO kallLoggDAO;

    @Test
    public void isReadyShouldSucceed() throws Exception {
        mockMvc.perform(get("/internal/isready") //
                        .accept(MediaType.ALL_VALUE)) //
                .andExpect(status().isOk());
    }
}
