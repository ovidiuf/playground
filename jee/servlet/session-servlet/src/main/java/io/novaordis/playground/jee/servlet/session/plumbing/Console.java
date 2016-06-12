/*
 * Copyright (c) 2016 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.jee.servlet.session.plumbing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class Console {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Console.class);

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * @return a HTML table rendered on the single line of text (no \n).
     */
    public String sortedMapToHtmlTable(SortedMap<String, String> table) {

        String htmlTable = "<table>";

        for(String key: table.keySet()) {
            String value = table.get(key);
            htmlTable += "<tr><td align='right'>" + key + ":</td><td align='left'>" + value + "</td></tr>";
        }

        htmlTable += "</table>";
        return htmlTable;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    private String errors;
    private String warnings;
    private String infos;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public void info(String msg) {

        info(msg, true);
    }

    /**
     * @param doLog whether to log in the server's log or not.
     */
    public void info(String msg, boolean doLog) {

//        if (msg == null || msg.length() == 0) {
//            return;
//        }

        if (infos == null) {
            infos = msg + "\n";
        }
        else {
            infos += msg + "\n";
        }

        if (doLog) {
            log.info(msg);
        }
    }

    /**
     * This will be displayed as a HTML table at the console. The keys will be displayed in order.
     * @param table
     */
    public void info(SortedMap<String, String> table) {

        //
        // add a HTML table to the "info" content
        //
        String htmlTable = sortedMapToHtmlTable(table);
        info(htmlTable, false);
    }

    public void warn(String msg) {

        if (msg == null || msg.length() == 0) {
            return;
        }

        if (warnings == null) {
            warnings = msg;
        }
        else {
            warnings += msg + "\n";
        }

        log.warn(msg);
    }

    public void error(String msg) {

        if (msg == null || msg.length() == 0) {
            return;
        }

        if (errors == null) {
            errors = msg;
        }
        else {
            errors += msg + "\n";
        }

        log.error(msg);
    }

    /**
     * @return errors on separate lines or null if there are no errors
     */
    public String getErrors() {

        return errors;
    }

    /**
     * @return warnings on separate lines or null if there are no warnings
     */
    public String getWarnings() {

        return warnings;
    }

    /**
     * @return infos on separate lines or null if there are no infos
     */
    public String getInfos() {

        return infos;
    }

    /**
     * @return a list of strings representing, in order, multi-line error content, multi-line warning content,
     * multi-line info content. If no content of a specific kind is available, null is returned instead.
     */
    public List<String> getContent() {

        return Arrays.asList(errors, warnings, infos);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
