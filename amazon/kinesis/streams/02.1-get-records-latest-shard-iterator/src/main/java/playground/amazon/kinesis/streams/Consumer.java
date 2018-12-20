package playground.amazon.kinesis.streams;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.amazonaws.services.kinesis.model.GetShardIteratorResult;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.Shard;
import com.amazonaws.services.kinesis.model.ShardIteratorType;
import com.amazonaws.services.kinesis.model.StreamDescription;

import java.util.ArrayList;
import java.util.List;

class Consumer {

    private  AmazonKinesis kinesisClient;
    private String streamName;

    Consumer(String streamName) {

        this.streamName = streamName;

        AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard();

        this.kinesisClient = clientBuilder.build();
    }

    void run() {

        Shard s = getSoleShard(streamName);

        String shardIterator = getInitialShardIterator(streamName, s.getShardId(), ShardIteratorType.LATEST);

        System.out.println("polling " + streamName + "/" + s.getShardId() + " for records ...");

        while(true) {

            GetRecordsRequest req = new GetRecordsRequest();
            req.setShardIterator(shardIterator);
            req.setLimit(25);

            GetRecordsResult res = kinesisClient.getRecords(req);

            for(Record r: res.getRecords()) {

                System.out.println(r);
            }

            shardIterator = res.getNextShardIterator();

            try {

                Thread.sleep(500L);
            }
            catch (InterruptedException e) {

                System.out.println("thread interrupted ...");
            }
        }
    }

    /**
     * We assume a single shard per stream - modify the example otherwise.
     */
    private Shard getSoleShard(String streamName) {

        List<Shard> shards = new ArrayList<>();

        String exclusiveStartShardId = null;

        while(true) {

            DescribeStreamRequest req = new DescribeStreamRequest();

            req.setStreamName(streamName);

            req.setExclusiveStartShardId(exclusiveStartShardId);

            DescribeStreamResult res = kinesisClient.describeStream(req);

            StreamDescription sd = res.getStreamDescription();

            List<Shard> batch = sd.getShards();

            if (batch.size() > 0) {

                exclusiveStartShardId = batch.get(batch.size() - 1).getShardId();
                shards.addAll(batch);
            }

            if (!sd.isHasMoreShards()) {

                break;
            }
        }

        if (shards.isEmpty()) {

            throw new IllegalStateException("no shards found");
        }

        if (shards.size() > 1) {

            throw new IllegalStateException(shards.size() + " shards found, this example does not know how to handle more than one shard");
        }

        return shards.get(0);
    }

    private String getInitialShardIterator(String streamName, String shardId, ShardIteratorType shardIteratorType) {

        GetShardIteratorRequest req = new GetShardIteratorRequest();
        req.setStreamName(streamName);
        req.setShardId(shardId);
        req.setShardIteratorType(shardIteratorType);

        GetShardIteratorResult res = kinesisClient.getShardIterator(req);

        return res.getShardIterator();
    }
}
