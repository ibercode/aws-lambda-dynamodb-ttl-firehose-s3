package com.ibercode;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.firehose.FirehoseClient;
import software.amazon.awssdk.services.firehose.model.PutRecordRequest;
import software.amazon.awssdk.services.firehose.model.PutRecordResponse;
import software.amazon.awssdk.services.firehose.model.Record;

public class DataProcessor implements RequestHandler<DynamodbEvent, String> {

    private final FirehoseClient firehoseClient = FirehoseClient.builder()
            .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
            .build();
    private final String FIREHOSE_NAME = System.getenv("FIREHOSE_NAME");

    @Override
    public String handleRequest(DynamodbEvent ddbEvent, Context context) {

        ddbEvent.getRecords().forEach(record -> {

            if(record.getEventName().equals("REMOVE")){
                System.out.println("[record] " + record);
                sendDataToFirehose(record);
            }
        });
        return "Done";
    }

    private String sendDataToFirehose(DynamodbEvent.DynamodbStreamRecord ddbRecord) {

        SdkBytes sdkBytes = SdkBytes.fromByteArray(ddbRecord.toString().getBytes());

        Record record = Record.builder()
                .data(sdkBytes)
                .build();

        PutRecordRequest recordRequest = PutRecordRequest.builder()
                .deliveryStreamName(FIREHOSE_NAME)
                .record(record)
                .build();

        PutRecordResponse recordResponse = firehoseClient.putRecord(recordRequest) ;
        return recordResponse.recordId();
    }
}
