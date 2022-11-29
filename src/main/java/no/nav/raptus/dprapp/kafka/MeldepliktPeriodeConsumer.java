package no.nav.raptus.dprapp.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MeldepliktPeriodeConsumer {

    @KafkaListener(topics = "${app.kafka.topic.meldepliktperiode}")
    public void consume(ConsumerRecord<String, String> record) {
        log.info(String.format("Record receivied: Key: %s \t Value: %s", record.key(), record.value()));
    }
}
