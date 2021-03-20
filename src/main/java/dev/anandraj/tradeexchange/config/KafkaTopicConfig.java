package dev.anandraj.tradeexchange.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${trade.exchange.order.topic}")
    private String orderTopic;

    @Bean
    public NewTopic txnEventTopic() {
        return TopicBuilder.name(orderTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
