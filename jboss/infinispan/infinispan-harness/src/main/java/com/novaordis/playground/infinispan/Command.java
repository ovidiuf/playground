package com.novaordis.playground.infinispan;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public interface Command
{
    void execute() throws Exception;

    /**
     * Format:
     *
     * <- ................  80 ...................>
     * _command_name - .........
     * <- 8 -> ................
     *         ................
     */
    String getHelp();
}
