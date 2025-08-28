package com.lgcms.lesson.event.producer;

import com.lgcms.lesson.common.kafka.dto.EncodingSuccess;
import com.lgcms.lesson.common.kafka.dto.KafkaEvent;
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
public class EncodingEventProducer {

    @Value("${spring.application.name}")
    private String applicationName;

    private final KafkaTemplate kafkaTemplate;

    public void EncodingSuccessEvent(EncodingSuccess encodingSuccess){
        String eventId = applicationName + "_" + UUID.randomUUID().toString();
        KafkaEvent kafkaEvent = KafkaEvent.builder()
                .eventId(eventId)
                .eventTime(LocalDateTime.now().toString())
                .eventType("ENCODING_SUCCESS")
                .data(encodingSuccess)
                .build();
        kafkaTemplate.send("ENCODING_SUCCESS_LECTURE",kafkaEvent);
    }
}
