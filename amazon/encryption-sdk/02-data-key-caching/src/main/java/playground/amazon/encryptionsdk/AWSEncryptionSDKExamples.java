package playground.amazon.encryptionsdk;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoMaterialsManager;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.caching.CachingCryptoMaterialsManager;
import com.amazonaws.encryptionsdk.caching.CryptoMaterialsCache;
import com.amazonaws.encryptionsdk.caching.LocalCryptoMaterialsCache;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class AWSEncryptionSDKExamples implements CommandLineRunner {

    public static final int MAX_CACHE_SIZE = 10;

    public static void main(String[] args) {
        SpringApplication.run(AWSEncryptionSDKExamples.class, args);
    }

    @Override
    public void run(String... args) {

        String keyArn = "arn:aws:kms:us-west-2:144446676909:key/72c58fc9-5b32-4cbe-8d1f-034e90929989";

        //
        // Create the data key cache, using the SDK default implementation
        //

        CryptoMaterialsCache cache = new LocalCryptoMaterialsCache(MAX_CACHE_SIZE);

        //
        // Set up the KmsMasterKeyProvider backed by the default credentials
        //

        final KmsMasterKeyProvider masterKeyProvider = new KmsMasterKeyProvider(keyArn);

        //
        // Create the caching cryptographic materials manager
        //


        int maxAgeSeconds = 3600; // required
        int messageUseLimit = 10000000;

        CryptoMaterialsManager cryptoMaterialsManager = CachingCryptoMaterialsManager.newBuilder().
                withMasterKeyProvider(masterKeyProvider).
                withCache(cache).
                withMaxAge(maxAgeSeconds, TimeUnit.SECONDS).
                withMessageUseLimit(messageUseLimit).
                build();


        //
        // Instantiate the SDK
        //

        final AwsCrypto crypto = new AwsCrypto();

        String data = "something";

        final Map<String, String> context = Collections.singletonMap("Example", "String");

        //encryptDecryptData(crypto, cryptoMaterialsManager, data, context, keyArn);

        encryptDecryptDataInALoop(crypto, masterKeyProvider, null, data, context, 100);
        //encryptDecryptDataInALoop(crypto, null, cryptoMaterialsManager, data, context, 100);

    }

    private void encryptDecryptData(AwsCrypto crypto, CryptoMaterialsManager cryptoMaterialsManager, String data, Map<String, String> context, String keyArn) {

        //
        // Encrypt
        //

        final String ciphertext = crypto.encryptString(cryptoMaterialsManager, data, context).getResult();

        System.out.println("data: \"" + data + "\" (length " + data.length() + "), ciphertext: " + ciphertext + "(length " + ciphertext.length() + ")");

        //
        // Decrypt the data
        //

        final CryptoResult<String, ?> decryptResult = crypto.decryptString(cryptoMaterialsManager, ciphertext);

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

    private void encryptDecryptDataInALoop(
            AwsCrypto crypto, KmsMasterKeyProvider masterKeyProvider, CryptoMaterialsManager cryptoMaterialsManager,
            String data, Map<String, String> context, int count) {

        long totalMs = 0;

        for(int i = 0; i < count; i ++) {

            long t0 = System.currentTimeMillis();

            final CryptoResult<String, ?> decryptResult;

            if (masterKeyProvider != null) {

                final String ciphertext = crypto.encryptString(masterKeyProvider, data, context).getResult();
                decryptResult = crypto.decryptString(masterKeyProvider, ciphertext);
            }
            else {

                final String ciphertext = crypto.encryptString(cryptoMaterialsManager, data, context).getResult();
                decryptResult = crypto.decryptString(cryptoMaterialsManager, ciphertext);
            }

            totalMs += (System.currentTimeMillis() - t0);

            if (!data.equals(decryptResult.getResult())) {

                throw new IllegalStateException("decrypted data different than what went in");
            }
        }

        double averageMsPerCycle = ((double)totalMs) / count;

        System.out.println("average ms per cycle: " + averageMsPerCycle);
    }
}
