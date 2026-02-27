package com.amigos.amqp.config;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQMessageProducer {
    private final AmqpTemplate amqpTemplate;

    public RabbitMQMessageProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publish(Object payload, String exchange, String routingKey) {
        //log.info("Publishing to exchange - {} using routingKey - {}. Payload {}", exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        //log.info("Published to exchange - {} using routingKey - {}. Payload {}", exchange, routingKey, payload);
    }
}
