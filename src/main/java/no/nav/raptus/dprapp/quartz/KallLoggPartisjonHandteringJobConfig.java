package no.nav.raptus.dprapp.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KallLoggPartisjonHandteringJobConfig {
    public static final String JOB_NAME = "KallLoggPartisjonHandteringJob";
    public static final String TRIGGER_NAME = "KallLoggPartisjonHandteringTrigger";

    @Bean
    public JobDetail kallLoggPartisjonHandteringJobDetail() {
        return JobBuilder.newJob()
                .ofType(KallLoggPartisjonHandteringJob.class)
                .storeDurably()
                .withIdentity(JobKey.jobKey(JOB_NAME))
                .build();
    }

    @Bean
    public Trigger kallLoggPartisjonHandteringTrigger(JobDetail job) {
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity(TriggerKey.triggerKey(TRIGGER_NAME))
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(4, 50))
                .build();
    }
}
