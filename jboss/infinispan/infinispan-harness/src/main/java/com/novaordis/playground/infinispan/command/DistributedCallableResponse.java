package com.novaordis.playground.infinispan.command;

import java.io.Serializable;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class DistributedCallableResponse implements Serializable
{
    // Constants -----------------------------------------------------------------------------------

    private static final long serialVersionUID = 349834L;

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private String nodeName;
    private String result;

    // Constructors --------------------------------------------------------------------------------

    public DistributedCallableResponse(String nodeName, String result)
    {
        this.nodeName = nodeName;
        this.result = result;
    }

    // Public --------------------------------------------------------------------------------------

    public String getNodeName()
    {
        return nodeName;
    }

    public String getResult()
    {
        return result;
    }

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------
}
