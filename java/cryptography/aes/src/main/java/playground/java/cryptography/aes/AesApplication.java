package playground.java.cryptography.aes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@SpringBootApplication
public class AesApplication implements CommandLineRunner {

    private SecureRandom secureRandom = new SecureRandom();

    public static void main(String[] args) {
        SpringApplication.run(AesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String key = "this is the secret encryption key";

        File inputFile = processCommandLine(args);

        File encryptedFile = encrypt(key, inputFile);
        decrypt(key, encryptedFile);
    }

    private File processCommandLine(String ... args) throws Exception {

        if (args == null || args.length == 0) {

            throw new Exception("the clear text file must be provided");
        }

        String fileName = args[0];
        return new File(fileName);
    }

    private byte[] pad(String keyString, int length) {

        if (keyString.length() < length) {

            int missingBytes = length - keyString.length();

            StringBuilder keyStringBuilder = new StringBuilder(keyString);

            for(int i = 0; i < missingBytes; i ++) {

                keyStringBuilder.append(" ");
            }

            keyString = keyStringBuilder.toString();
        }

        return keyString.substring(0, length).getBytes(StandardCharsets.UTF_8);
    }

    private File encrypt(String key, File inputFile) throws Exception {

        // we're currently not using it, but we're writing it

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 256); // AES-256
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        byte[] key = keyFactory.generateSecret(spec).getEncoded();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        byte[] keyAsBytes = pad(key, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyAsBytes, "AES");

        byte[] ivBytes = new byte[16];
        secureRandom.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

        FileInputStream in = new FileInputStream(inputFile);
        byte[] input = new byte[(int) inputFile.length()];
        in.read(input);

        byte[] ciphertext = cipher.doFinal(input);

        byte[] finalCiphertext = new byte[2 * 16 + ciphertext.length];
        System.arraycopy(ivBytes, 0, finalCiphertext, 0, 16);
        System.arraycopy(salt, 0, finalCiphertext, 16, 16);
        System.arraycopy(ciphertext, 0, finalCiphertext, 32, ciphertext.length);

        File outputFile = new File(inputFile.getParentFile(), inputFile.getName() + ".encrypted");
        FileOutputStream out = new FileOutputStream(outputFile);

        out.write(finalCiphertext);

        out.flush();
        out.close();
        in.close();

        System.out.println("wrote " + outputFile);

        return outputFile;
    }

    private void decrypt(String key, File encryptedFile) throws Exception {

        FileInputStream in = new FileInputStream(encryptedFile);
        byte[] input = new byte[(int) encryptedFile.length()];
        in.read(input);

        byte[] ivBytes = new byte[16];
        System.arraycopy(input, 0, ivBytes, 0, 16);
        byte[] salt = new byte[16];
        System.arraycopy(input, 16, salt, 0, 16);
        byte[] ciphertext = new byte[input.length - 32];
        System.arraycopy(input, 32, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        byte[] keyAsBytes = pad(key, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyAsBytes, "AES");

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

        byte[] cleartext = cipher.doFinal(ciphertext);

        File outputFile = new File(encryptedFile.getParentFile(), encryptedFile.getName() + ".thenDecrypted");
        FileOutputStream out = new FileOutputStream(outputFile);
        out.write(cleartext);

        out.flush();
        out.close();
        in.close();

        System.out.println("wrote " + outputFile);
    }
}
