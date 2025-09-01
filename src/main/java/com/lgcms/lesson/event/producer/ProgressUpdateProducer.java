package com.lgcms.lesson.event.producer;

import com.lgcms.lesson.common.kafka.dto.KafkaEvent;
import com.lgcms.lesson.common.kafka.dto.ProgressUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgressUpdateProducer {

    private final KafkaTemplate kafkaTemplate;
    @Value("${spring.application.name}")
    private String applicationName;

    public void updateProgressEvent(ProgressUpdate progressUpdate){

        KafkaEvent kafkaEvent = KafkaEvent.builder()
                .eventId(applicationName + UUID.randomUUID().toString())
                .eventType("PROGRESS_UPDATED")
                .eventTime(LocalDateTime.now().toString())
                .data(progressUpdate)
                .build();

        kafkaTemplate.send("PROGRESS_UPDATED", kafkaEvent);
    }
}
