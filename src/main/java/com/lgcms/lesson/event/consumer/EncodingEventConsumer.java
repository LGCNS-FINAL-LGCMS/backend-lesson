package com.lgcms.lesson.event.consumer;

import com.lgcms.lesson.common.kafka.dto.KafkaEvent;
import com.lgcms.lesson.common.kafka.dto.EncodingSuccess;
import com.lgcms.lesson.common.kafka.utils.KafkaEventFactory;
import com.lgcms.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EncodingEventConsumer {

    private final LessonService lessonService;
    private final KafkaEventFactory kafkaEventFactory;

    @KafkaListener(topics = "ENCODING_SUCCESS_LESSON", containerFactory = "defaultFactory")
    public void LectureUploadConsume(KafkaEvent event){
        EncodingSuccess videoEncodingSuccess = kafkaEventFactory.convert(event, EncodingSuccess.class);
        lessonService.updateVideoStatusAndThumbnail(videoEncodingSuccess);
    }
}
