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

package io.novaordis.playground.java.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 11/9/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            throw new Exception("XML file name should be specified as the first command line argument");
        }

        File xmlFile = new File(args[0]);

        //
        // instantiate the factory
        //

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //
        // configure the factory
        //

        factory.setNamespaceAware(false);


        factory.setValidating(false);


        //
        // get the parser
        //

        DocumentBuilder builder = factory.newDocumentBuilder();

        //
        // install the error handler
        //

        builder.setErrorHandler(new ErrorHandlerImpl());


        //
        // parse
        //

        Document d = builder.parse(xmlFile);

        //
        // walk the document
        //

        walk(d, 0);

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    private static void walk(Node node, int indentationLevel) {


        if (node instanceof Text && node.getNodeValue().trim().length() == 0) {

            //
            // if we are ignorable white space, ... ignore
            //
            return;
        }

        System.out.print(indentation(indentationLevel));

        String nodeName = node.getNodeName();

        if (node instanceof Element) {
            System.out.print("<" + nodeName + ">");
        }
        else if (node instanceof Text) {

            //
            // we are not ignorable white space, we caught that case above
            //

            String s = node.getNodeValue();

            //
            // remove the new lines
            //

            s = removeNewLines(s);

            System.out.print(s);
        }

        else {
            System.out.print(".");
        }

        System.out.println();

        NodeList list = node.getChildNodes();

        for(int i = 0; i < list.getLength(); i ++) {

            Node child = list.item(i);
            walk(child, indentationLevel + 1);
        }

        if (node instanceof Element) {
            System.out.print(indentation(indentationLevel));
            System.out.print("</" + nodeName + ">");
            System.out.println();
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static String indentation(int level) {

        String s = "";

        for(int i = 0; i < level * 4; i ++) {
            s += ' ';
        }

        return s;
    }

    private static String removeNewLines(String s) {

        int i = s.length() - 1;

        for(; i >= 0; i --) {

            if (s.charAt(i) != '\n') {
                break;
            }
        }

        return s.substring(0, i + 1);
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
