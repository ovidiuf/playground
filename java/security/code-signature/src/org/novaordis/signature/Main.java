package org.novaordis.signature;

import java.io.File;

/**
 * @author <a href="mailto:ovidiu@feodorov.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision$</tt>
 * $Id$
 */
public class Main
{
    // Constants -----------------------------------------------------------------------------------

    // Static --------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        System.out.println("hello, I am " + Main.class + " and I am trying to perform a secure operation ....");
        listCurrentDirectory();
    }

    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    private static void listCurrentDirectory() throws Exception
    {
        System.out.println("attempting to list current directory ...");
        
        File dir = new File(".");

        File[] files = dir.listFiles();
        for(File f: files)
        {
            System.out.println(f.getName());
        }

    }

    // Inner classes -------------------------------------------------------------------------------
}
