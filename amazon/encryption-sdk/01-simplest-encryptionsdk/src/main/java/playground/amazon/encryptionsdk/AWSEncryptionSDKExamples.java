package playground.amazon.encryptionsdk;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class AWSEncryptionSDKExamples implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AWSEncryptionSDKExamples.class, args);
    }

    @Override
    public void run(String... args) {

        //String keyArn = "arn:aws:kms:us-west-2:144446676909:key/72c58fc9-5b32-4cbe-8d1f-034e90929989";

        String keyArn2 = "arn:aws:kms:us-west-2:144446676909:key/18cab717-2c75-48e8-bec2-fd4881f6e654";

        String currentKey = keyArn2;

        final AwsCrypto crypto = new AwsCrypto();

        // Set up the KmsMasterKeyProvider backed by the default credentials
        final KmsMasterKeyProvider masterKeyProvider = new KmsMasterKeyProvider(currentKey);

        final Map<String, String> context = Collections.singletonMap("Example", "String");

//        String ciphertext = encrypt(crypto, masterKeyProvider, "something", context);
//
//        System.out.println(ciphertext);

        String ciphertext = "AYADeKxBwEkNon86hxZaTFh2mVYAcAACAAdFeGFtcGxlAAZTdHJpbmcAFWF3cy1jcnlwdG8tcHVibGljLWtleQBEQTJCQlp1bit5LzV5cGtKZWhvTHJxZTFEemZmbUxjdEdhVjFrRnI4YVRtNGgvRG15dXJhc20zZHRGTFN2WVVoKzV3PT0AAQAHYXdzLWttcwBLYXJuOmF3czprbXM6dXMtd2VzdC0yOjE0NDQ0NjY3NjkwOTprZXkvNzJjNThmYzktNWIzMi00Y2JlLThkMWYtMDM0ZTkwOTI5OTg5ALgBAgEAeNrdUVr9agEil/WZsJxKqpU+/YhQEz5SiSfflGsRXs0GAYiCT7mErIK14DBFXR+kUP4AAAB+MHwGCSqGSIb3DQEHBqBvMG0CAQAwaAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAy53/dqWBj7LK9WKtkCARCAOzX7yGKvqhUmBxqnXEeBWelQhsdQbrI6kMzyYoQNwCgliLOah7Q1shv8mzfA45b9lSahwF7YmyUptndPAgAAAAAMAAAQAAAAAAAAAAAAAAAAAPICKUATFsNPkHF2jNfX+kj/////AAAAAQAAAAAAAAAAAAAAAQAAAAmYJl2OYOCgyg4fyFeKCLMvI9vkEupm0jNHAGcwZQIwZxLfjsP12Plzd0X0NSM5sH8WETQNWy8pfa76/pqO9D1W3D6HqWWKAYZwRHekuoGsAjEAzuEiR2cTbaAfpGjBqoVcuMprdMRZ7F261b+fGlGFIVLarvNtjkIPk8aAJx+8vTC7";

        System.out.println(decrypt(crypto, masterKeyProvider, ciphertext, context));
    }

    private String encrypt(AwsCrypto crypto, KmsMasterKeyProvider masterKeyProvider, String cleartext, Map<String, String> context) {

        //
        // Encrypt the data
        //
        // Most encrypted data should have an associated encryption context to protect integrity. This sample uses
        // placeholder values.
        //
        // For more information see:
        // blogs.aws.amazon.com/security/post/Tx2LZ6WBJJANTNW/How-to-Protect-the-Integrity-of-Your-Encrypted-Data-by-Using-AWS-Key-Management
        //



        //noinspection UnnecessaryLocalVariable
        final String ciphertext = crypto.encryptString(masterKeyProvider, cleartext, context).getResult();

        //System.out.println("data: \"" + data + "\" (length " + data.length() + "), ciphertext: " + ciphertext + "(length " + ciphertext.length() + ")");

        return ciphertext;
    }

    private String decrypt(AwsCrypto crypto, KmsMasterKeyProvider masterKeyProvider, String ciphertext, Map<String, String> context) {

        final CryptoResult<String, KmsMasterKey> decryptResult = crypto.decryptString(masterKeyProvider, ciphertext);

        //
        // Before returning the plaintext, verify that the customer master key that was used in the encryption operation
        // was the one supplied to the master key provider.
        //

//        if (!decryptResult.getMasterKeyIds().get(0).equals(keyArn)) {
//
//            throw new IllegalStateException("Wrong key ID!");
//        }

        //
        // Also, verify that the encryption context in the result contains the encryption context supplied to the
        // encryptString method. Because the SDK can add values to the encryption context, don't require that the
        // entire context matches.
        //

        for (final Map.Entry<String, String> e : context.entrySet()) {

            if (!e.getValue().equals(decryptResult.getEncryptionContext().get(e.getKey()))) {

                throw new IllegalStateException("Wrong Encryption Context!");
            }
        }

        return decryptResult.getResult();
    }
}
