package com.Ecommerce.ProductService.component;

import com.Ecommerce.ProductService.enums.EventStatus;
import com.Ecommerce.ProductService.model.OutboxEvent;
import com.Ecommerce.ProductService.repository.OutboxEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxEventPublisher {

    private final KafkaTemplate<String, String>  kafkaTemplate;
    private final OutboxEventRepository  outboxEventRepository;

    public OutboxEventPublisher(KafkaTemplate<String, String> kafkaTemplate, OutboxEventRepository outboxEventRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.outboxEventRepository = outboxEventRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void publishEvents() {
        List<OutboxEvent> events = outboxEventRepository.findAllByStatus(EventStatus.NEW);

        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send("product-events", event.getPayload());
                event.setStatus(EventStatus.PROCESSED);
            } catch (Exception e) {
                event.setStatus(EventStatus.FAILED); // Handle failures
            }
            outboxEventRepository.save(event);
        }
    }
}
