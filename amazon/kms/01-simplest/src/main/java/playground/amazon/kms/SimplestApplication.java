package playground.amazon.kms;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DescribeKeyRequest;
import com.amazonaws.services.kms.model.DescribeKeyResult;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import com.amazonaws.services.kms.model.ListKeysRequest;
import com.amazonaws.services.kms.model.ListKeysResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.ByteBuffer;

@SpringBootApplication
public class SimplestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimplestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

        //
        // create a CMK
        //

//        String desc = "experimental key created programmatically";
//
//        CreateKeyRequest req = new CreateKeyRequest().withDescription(desc);
//
//        CreateKeyResult res = client.createKey(req);
//
//        System.out.println(res);

        // created key keyId 0138371a-8054-4c96-9d1f-20a4db2c4ffd
        // arn:aws:kms:us-west-2:673499572719:key/0138371a-8054-4c96-9d1f-20a4db2c4ffd


        //
        // list all keys
        //

        ListKeysRequest req = new ListKeysRequest().withLimit(100);

        ListKeysResult res = kmsClient.listKeys(req);

        System.out.println(res);

        // view the CMK previously created

        String keyId = "arn:aws:kms:us-west-2:673499572719:key/0138371a-8054-4c96-9d1f-20a4db2c4ffd";

        DescribeKeyRequest req2 = new DescribeKeyRequest().withKeyId(keyId);

        DescribeKeyResult res2 = kmsClient.describeKey(req2);

        System.out.println(res2);

        //
        // generate a data key
        //

        GenerateDataKeyRequest dataKeyRequest = new GenerateDataKeyRequest();
        dataKeyRequest.setKeyId(keyId);
        dataKeyRequest.setKeySpec("AES_256");

        GenerateDataKeyResult dataKeyResult = kmsClient.generateDataKey(dataKeyRequest);

        ByteBuffer plaintextKey = dataKeyResult.getPlaintext();
        byte[] clear = plaintextKey.array();

        ByteBuffer encryptedKey = dataKeyResult.getCiphertextBlob();
        byte[] encrypted = encryptedKey.array();

        System.out.println(dataKeyResult);

        //
        // decrypt the data key
        //

        DecryptRequest req3 = new DecryptRequest().withCiphertextBlob(encryptedKey);

        ByteBuffer plaintextKey2 = kmsClient.decrypt(req3).getPlaintext();
        byte[] clear2 = plaintextKey2.array();

        assertEquals(clear, clear2);
    }

    private void assertEquals(byte[] b, byte[] b2) {

        if (b.length != b2.length) {

            throw new IllegalStateException("not the same length");
        }

        for(int i = 0; i < b.length; i ++) {

            if (b[i] != b2[i]) {

                throw new IllegalStateException("elements " + i + " differ");
            }
        }
    }
}
