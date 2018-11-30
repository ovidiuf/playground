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

        System.out.println("encrypting ....");

        String keyArn = "arn:aws:kms:us-west-2:673499572719:key/0138371a-8054-4c96-9d1f-20a4db2c4ffd";

        //
        // Instantiate the SDK
        //

        final AwsCrypto crypto = new AwsCrypto();

        //
        // Set up the KmsMasterKeyProvider backed by the default credentials
        //

        final KmsMasterKeyProvider prov = new KmsMasterKeyProvider(keyArn);

        //
        // Encrypt the data
        //
        // Most encrypted data should have an associated encryption context to protect integrity. This sample uses
        // placeholder values.
        //
        // For more information see:
        // blogs.aws.amazon.com/security/post/Tx2LZ6WBJJANTNW/How-to-Protect-the-Integrity-of-Your-Encrypted-Data-by-Using-AWS-Key-Management
        //

        final Map<String, String> context = Collections.singletonMap("Example", "String");

        String data = "something";

        final String ciphertext = crypto.encryptString(prov, data, context).getResult();

        System.out.println("data: \"" + data + "\" (length " + data.length() + "), ciphertext: " + ciphertext + "(length " + ciphertext.length() + ")");

        //
        // Decrypt the data
        //

        final CryptoResult<String, KmsMasterKey> decryptResult = crypto.decryptString(prov, ciphertext);

        //
        // Before returning the plaintext, verify that the customer master key that was used in the encryption operation
        // was the one supplied to the master key provider.
        //

        if (!decryptResult.getMasterKeyIds().get(0).equals(keyArn)) {

            throw new IllegalStateException("Wrong key ID!");
        }

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

        //
        // Now we can return the plaintext data
        //

        System.out.println("Decrypted: " + decryptResult.getResult());
    }
}
