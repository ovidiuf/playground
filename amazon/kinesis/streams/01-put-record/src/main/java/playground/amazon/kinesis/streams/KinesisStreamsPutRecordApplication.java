package playground.amazon.kinesis.streams;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;

@SpringBootApplication
public class KinesisStreamsPutRecordApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KinesisStreamsPutRecordApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard();

//        clientBuilder.setRegion(regionName);
//        clientBuilder.setCredentials(credentialsProvider);
//        clientBuilder.setClientConfiguration(config);

        String streamName = "ovidiu-test";

        AmazonKinesis kinesisClient = clientBuilder.build();

        byte[] content = getContent();

        putRecord(kinesisClient, streamName, content);
    }

    private void putRecord(AmazonKinesis kinesisClient, String streamName, byte[] content) throws Exception {

        PutRecordRequest putRecordRequest = new PutRecordRequest();

        putRecordRequest.setStreamName(streamName);

        putRecordRequest.setPartitionKey("something else");

        //        putRecordRequest.setSequenceNumberForOrdering( sequenceNumberOfPreviousRecord );

        putRecordRequest.setData(ByteBuffer.wrap(content));

        PutRecordResult putRecordResult = kinesisClient.putRecord(putRecordRequest);

        System.out.println(putRecordResult);

        //        String sequenceNumberOfPreviousRecord = putRecordResult.getSequenceNumber();
    }

    private static byte[] getContent() throws Exception {

        byte[] result;

        //result = getContentFromString();

        result = getContentFromFile();

        return result;
    }

    private static byte[] getContentFromString() {

        return "test-string".getBytes();
    }

    private static byte[] getContentFromFile() throws Exception {

        String fileName = "/Users/ovidiu/tmp/one-authorization.txt";

        File f = new File(fileName);

        if (!f.canRead()) {

            throw new IllegalArgumentException("file " + f + " does not exist or it cannot be read");
        }

        return Files.readAllBytes(f.toPath());
    }

}

