package com.ibercode.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Sensor {

    private String sensorId;
    private String temperature;
    private Long dataTTL;

    public Sensor() {
    }

    public Sensor(String sensorId, String temperature, Long dataTTL) {
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.dataTTL = dataTTL;
    }

    @DynamoDbPartitionKey
    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Long getDataTTL() {
        return dataTTL;
    }

    public void setDataTTL(Long dataTTL) {
        this.dataTTL = dataTTL;
    }
}
