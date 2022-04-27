package com.ibercode;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ibercode.model.Sensor;
import com.ibercode.utils.DDBUtils;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class DataProducer implements RequestHandler {

    private final DDBUtils ddbUtils = new DDBUtils();
    private final Random random = new Random();
    private final String SENSORS_TABLE = System.getenv("SENSORS_TABLE");

    @Override
    public String handleRequest(Object o, Context context) {

        for(int i = 0; i <10; i++){
            Sensor sensor = new Sensor(UUID.randomUUID().toString(),
                    String.valueOf(random.nextInt(100)),
                    Instant.now().getEpochSecond() + 60);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ddbUtils.putItem(sensor, SENSORS_TABLE);
        }

        return "Done";
    }
}
