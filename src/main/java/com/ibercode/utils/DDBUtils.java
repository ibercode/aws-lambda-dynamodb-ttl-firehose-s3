package com.ibercode.utils;

import com.ibercode.model.Sensor;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DDBUtils {

    private final DynamoDbClient ddb = DynamoDbClient.builder()
            .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
            .build();
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(ddb)
            .build();


    public void putItem(Sensor sensor, String tableName){
        DynamoDbTable<Sensor> mappedTable = enhancedClient
                .table(tableName, TableSchema.fromBean(Sensor.class));

        mappedTable.putItem(sensor);
    }
}
