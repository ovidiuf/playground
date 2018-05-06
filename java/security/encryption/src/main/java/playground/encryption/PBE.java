package playground.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

/**
 * Static PBE encryption/decryption utilities.
 *
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 */
public class PBE
{
    // Constants -----------------------------------------------------------------------------------

    private static SecretKeyFactory secretKeyFactory;
    private static Cipher cipher;

    // Static --------------------------------------------------------------------------------------

    public static void encrypt(InputStream clear, OutputStream encrypted,
                               char[] password, byte[] salt, int iterationCount)
        throws GeneralSecurityException, IOException
    {
        process(Cipher.ENCRYPT_MODE, clear, encrypted, password, salt, iterationCount);
    }

    public static void decrypt(InputStream encrypted, OutputStream clear,
                               char[] password, byte[] salt, int iterationCount)
        throws GeneralSecurityException, IOException
    {
        process(Cipher.DECRYPT_MODE, encrypted, clear, password, salt, iterationCount);
    }

    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    private synchronized static void initializeKeyFactoryAndCipher() throws GeneralSecurityException
    {
        if (secretKeyFactory == null)
        {
            secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            cipher = Cipher.getInstance("PBEWithMD5AndDES");
        }
    }

    /**
     * @param mode can be Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE.
     */
    private static void process(int mode, InputStream in, OutputStream out,
                                char[] password, byte[] salt, int iterationCount)
        throws GeneralSecurityException, IOException
    {
        initializeKeyFactoryAndCipher();

        PBEParameterSpec ps = new PBEParameterSpec(salt, iterationCount);
        PBEKeySpec ks = new PBEKeySpec(password);
        SecretKey sk = secretKeyFactory.generateSecret(ks);
        cipher.init(mode, sk, ps);

        int read;
        byte[] input = new byte[64];
        byte[] output = null;
        while ((read = in.read(input)) != -1)
        {
            output = cipher.update(input, 0, read);
            if (output != null)
            {
                out.write(output);
            }
        }

        output = cipher.doFinal();
        if (output != null)
        {
            out.write(output);
        }

        out.flush();
    }

    // Inner classes -------------------------------------------------------------------------------

}
