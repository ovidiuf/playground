package playground.amazon.kinesis.streams;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class KinesisStreamsPutRecordApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KinesisStreamsPutRecordApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println(".");

        AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard();

//        clientBuilder.setRegion(regionName);
//        clientBuilder.setCredentials(credentialsProvider);
//        clientBuilder.setClientConfiguration(config);

        AmazonKinesis kinesisClient = clientBuilder.build();

        putRecord(kinesisClient, "ovidiu-test");
    }

    private void putRecord(AmazonKinesis kinesisClient, String streamName) throws Exception {

        PutRecordRequest putRecordRequest = new PutRecordRequest();

        putRecordRequest.setStreamName(streamName);

        putRecordRequest.setData(ByteBuffer.wrap("testData".getBytes()));

        putRecordRequest.setPartitionKey("partitionKey-1");

        //        putRecordRequest.setSequenceNumberForOrdering( sequenceNumberOfPreviousRecord );

        PutRecordResult putRecordResult = kinesisClient.putRecord(putRecordRequest);

        System.out.println(putRecordResult);

        //        String sequenceNumberOfPreviousRecord = putRecordResult.getSequenceNumber();
    }
}

