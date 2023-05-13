package com.smartmug.kafka.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfiguration {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    private String acks;
    private String retries;
    private String batchSize;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    private CompressionType compressionType;

    @Value("${spring.kafka.producer.topic}")
    private String topicName;

    private int latchTimeout = 5000;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(final String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(final String acks) {
        this.acks = acks;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(final String retries) {
        this.retries = retries;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(final String batchSize) {
        this.batchSize = batchSize;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(final String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(final String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public CompressionType getCompressionType() {
        if(null == compressionType){
            return CompressionType.NONE;
        }
        return compressionType;
    }

    public void setCompressionType(final CompressionType compressionType) {
        this.compressionType = compressionType;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getLatchTimeout() {
        return latchTimeout;
    }

    public void setLatchTimeout(int latchTimeout) {
        this.latchTimeout = latchTimeout;
    }

    public void copyConfiguration(final KafkaConfiguration kafkaConfiguration){
        this.acks = kafkaConfiguration.getAcks();
        this.batchSize = kafkaConfiguration.getBatchSize();
        this.bootstrapServers = kafkaConfiguration.getBootstrapServers();
        this.compressionType = kafkaConfiguration.getCompressionType();
        this.keySerializer = kafkaConfiguration.getKeySerializer();
        this.valueSerializer = kafkaConfiguration.getValueSerializer();
        this.retries = kafkaConfiguration.getRetries();
        this.latchTimeout = kafkaConfiguration.getLatchTimeout();
    }

}
