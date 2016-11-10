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

package io.novaordis.playground.java.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 11/9/16
 */
public class ContentHandlerImpl implements ContentHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // ContentHandler implementation -----------------------------------------------------------------------------------

    @Override
    public void setDocumentLocator(Locator locator) {

        System.out.println("set document locator: " + locator);
    }

    @Override
    public void startDocument() throws SAXException {

        System.out.println("start document");

    }

    @Override
    public void endDocument() throws SAXException {

        System.out.println("end document");

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        throw new RuntimeException("startPrefixMapping() NOT YET IMPLEMENTED");
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        throw new RuntimeException("endPrefixMapping() NOT YET IMPLEMENTED");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        System.out.println("start element uri: " + uri + ", localName: " + localName + ", qName: " + qName);

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        System.out.println("end element uri: " + uri + ", localName: " + localName + ", qName: " + qName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        System.out.print("characters: ");

        for(int i = start; i < start + length; i ++) {

            System.out.print(ch[i]);

            if (i < start + length - 1) {

                System.out.print(", ");
            }
        }

        System.out.println();
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        throw new RuntimeException("ignorableWhitespace() NOT YET IMPLEMENTED");
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        throw new RuntimeException("processingInstruction() NOT YET IMPLEMENTED");
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        throw new RuntimeException("skippedEntity() NOT YET IMPLEMENTED");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
