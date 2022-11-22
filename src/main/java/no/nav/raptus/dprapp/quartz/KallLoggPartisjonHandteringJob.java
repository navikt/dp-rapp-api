package no.nav.raptus.dprapp.quartz;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.db.repository.KallLoggPartisjonHandterer;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Quartz-jobb for håndtering av daglige partisjoner av tabellen kall_logg.
 * <p>
 * Jobben er annotert til <u>ikke</u> å tillate samtidig kjøring, i tilfelle eksekveringstiden strekker seg inn i neste
 * kjøreperiode.
 */
@Slf4j
@DisallowConcurrentExecution
public class KallLoggPartisjonHandteringJob extends QuartzJobBean {

    private KallLoggPartisjonHandterer kallLoggPartisjonHandterer;

    public KallLoggPartisjonHandteringJob(KallLoggPartisjonHandterer kallLoggPartisjonHandterer) {
        this.kallLoggPartisjonHandterer = kallLoggPartisjonHandterer;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("{} startet {}", context.getJobDetail().getKey().getName(), toLocalDateTime(context.getFireTime()));

        kallLoggPartisjonHandterer.handterPartisjoner();

        if (context.getNextFireTime() != null) {
            log.info("Neste kjøring av {} er planlagt {}", context.getJobDetail().getKey().getName(),
                    toLocalDateTime(context.getNextFireTime()));
        } else {
            log.info("Ingen ny kjøring av {} er planlagt.", context.getJobDetail().getKey().getName()); // Kun test
        }
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
