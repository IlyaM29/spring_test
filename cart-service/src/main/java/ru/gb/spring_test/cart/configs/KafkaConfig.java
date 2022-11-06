package ru.gb.spring_test.cart.configs;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.gb.spring_test.cart.dto.OrderDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String server;

    @Bean
    public Map<String, Object> producerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, "OrderDto:ru.gb.spring_test.cart.dto.OrderDto");
        return props;
    }

    @Bean
    public ProducerFactory<Long, OrderDto> producerFactory(){
      return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean(value = "Kafka")
    public KafkaTemplate<Long, OrderDto> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
