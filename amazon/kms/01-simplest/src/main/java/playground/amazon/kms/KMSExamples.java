package playground.amazon.kms;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.CreateKeyRequest;
import com.amazonaws.services.kms.model.CreateKeyResult;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DescribeKeyRequest;
import com.amazonaws.services.kms.model.DescribeKeyResult;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import com.amazonaws.services.kms.model.KeyListEntry;
import com.amazonaws.services.kms.model.ListKeysRequest;
import com.amazonaws.services.kms.model.ListKeysResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.ByteBuffer;

@SpringBootApplication
public class KMSExamples implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KMSExamples.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //listAllCustomerMasterKeys();


        createCustomerMasterKey();

        // view the CMK previously created

//        String previouslyCreated = "arn:aws:kms:us-west-2:673499572719:key/0138371a-8054-4c96-9d1f-20a4db2c4ffd";
//        String bamboo = "arn:aws:kms:us-west-2:144446676909:key/96a3a735-b8f5-4456-9488-ecbeb4087540";
//        listMasterKey(bamboo);

        //listSpecificCustomerMasterKeyWithExplicitCredentials("arn:aws:kms:us-west-2:673499572719:key/0138371a-8054-4c96-9d1f-20a4db2c4ffd");

        //assertEquals(clear, clear2);
    }

    private static void createCustomerMasterKey() {

        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

        //
        // create a CMK
        //

        String desc = "experimental key created programmatically";

        CreateKeyRequest req = new CreateKeyRequest().withDescription(desc);

        CreateKeyResult res = kmsClient.createKey(req);

        System.out.println(res);

        // created key keyId 0138371a-8054-4c96-9d1f-20a4db2c4ffd
        // arn:aws:kms:us-west-2:673499572719:key/0138371a-8054-4c96-9d1f-20a4db2c4ffd
    }

    private static void listAllCustomerMasterKeys() {

        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

        //
        // list all keys
        //

        ListKeysRequest req = new ListKeysRequest().withLimit(100);

        ListKeysResult res = kmsClient.listKeys(req);

        for(KeyListEntry e: res.getKeys()) {

            System.out.println(e);
            System.out.println();
        }
    }

    private static void listMasterKey(String masterKeyArn) {

        System.out.println("listing key: " + masterKeyArn);

        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

        DescribeKeyRequest req = new DescribeKeyRequest().withKeyId(masterKeyArn);

        DescribeKeyResult res = kmsClient.describeKey(req);

        System.out.println(res);
    }

    private static void listSpecificCustomerMasterKeyWithExplicitCredentials(String masterKeyArn) {

        System.out.println("listing master key " + masterKeyArn + " using explicit AWS credentials");

        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

        DescribeKeyRequest req =
                new DescribeKeyRequest().
                        withKeyId(masterKeyArn).
                        withRequestCredentialsProvider(new AWSCredentialsProvider() {
                            @Override
                            public AWSCredentials getCredentials() {

                                return new AWSCredentials() {

                                    @Override
                                    public String getAWSAccessKeyId() {
                                        return "...";
                                    }

                                    @Override
                                    public String getAWSSecretKey() {
                                        return "...";
                                    }
                                };
                            }

                            @Override
                            public void refresh() {
                            }
                        });

        DescribeKeyResult res = kmsClient.describeKey(req);

        System.out.println(res);
    }

    /**
     * Returns the key in clear.
     */
    private static byte[] generateDataKey(String cmkArn) {

        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

        //
        // generate a data key
        //

        GenerateDataKeyRequest dataKeyRequest = new GenerateDataKeyRequest();
        dataKeyRequest.setKeyId(cmkArn);
        dataKeyRequest.setKeySpec("AES_256");

        GenerateDataKeyResult dataKeyResult = kmsClient.generateDataKey(dataKeyRequest);

        System.out.println(dataKeyResult);

        ByteBuffer encryptedKey = dataKeyResult.getCiphertextBlob();
        byte[] encrypted = encryptedKey.array();

        ByteBuffer plaintextKey = dataKeyResult.getPlaintext();
        return plaintextKey.array();
    }

    private static byte[] decryptDataKey(byte[] encryptedKey) {

        ByteBuffer buffer = ByteBuffer.wrap(encryptedKey);

        AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

        DecryptRequest req3 = new DecryptRequest().withCiphertextBlob(buffer);

        ByteBuffer plaintextKey2 = kmsClient.decrypt(req3).getPlaintext();

        return plaintextKey2.array();
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
