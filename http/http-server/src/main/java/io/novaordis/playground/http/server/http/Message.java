/*
 * Copyright (c) 2017 Nova Ordis LLC
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

package io.novaordis.playground.http.server.http;

import io.novaordis.playground.http.server.http.header.HttpHeader;
import io.novaordis.playground.http.server.http.header.HttpHeaderDefinition;

import java.util.List;

/**
 * The base interface for HTTP requests and responses.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public interface Message {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * If a header with the same name exists, the second value will be simply added to the list. According to the
     * specification, multiple headers with the same field name may be present in a message. In this case, the entire
     * value for that header is defined as a comma-separated list, where the value is the combination of the multiple
     * header fields into one "field-name: field-value" pair, without changing the semantics of the message, by
     * appending each subsequent field-value to the first, each separated by a comma. The order in which header fields
     * with the same field-name are received is therefore significant to the interpretation of the combined field value.
     *
     * An example of header that renders itself to this kind of processing is Via
     * @{linktourl https://kb.novaordis.com/index.php/HTTP_Entity_Header_Via}
     */
    void addHeader(HttpHeaderDefinition d, String fieldBody);

    /**
     * Use it for non-standard headers.
     *
     * If a header with the same name exists, the second value will be simply added to the list. According to the
     * specification, multiple headers with the same field name may be present in a message. In this case, the entire
     * value for that header is defined as a comma-separated list, where the value is the combination of the multiple
     * header fields into one "field-name: field-value" pair, without changing the semantics of the message, by
     * appending each subsequent field-value to the first, each separated by a comma. The order in which header fields
     * with the same field-name are received is therefore significant to the interpretation of the combined field value.
     *
     * An example of header that renders itself to this kind of processing is Via
     * @{linktourl https://kb.novaordis.com/index.php/HTTP_Entity_Header_Via}
     */
    void addHeader(String fieldName, String fieldBody);


    /**
     * If a header with the same name exists, the second value will be simply added to the list. According to the
     * specification, multiple headers with the same field name may be present in a message. In this case, the entire
     * value for that header is defined as a comma-separated list, where the value is the combination of the multiple
     * header fields into one "field-name: field-value" pair, without changing the semantics of the message, by
     * appending each subsequent field-value to the first, each separated by a comma. The order in which header fields
     * with the same field-name are received is therefore significant to the interpretation of the combined field value.
     *
     * An example of header that renders itself to this kind of processing is Via
     * @{linktourl https://kb.novaordis.com/index.php/HTTP_Entity_Header_Via}
     */
    void addHeader(HttpHeader header);

    /**
     * @return the headers in the order they were read from the socket. May return an empty list, but never null.
     * eaders with the same names will be returned as distinct copies.
     *
     * @see Message#addHeader(HttpHeaderDefinition, String)
     */
    List<HttpHeader> getHeaders();

    /**
     * Use this method to query for non-standard headers. The query is case insensitive.
     *
     * @param fieldName the header's field name.
     *
     * @return Will return an empty list if there is not such a header. Will return a list containing a single element
     * if there is a single header with the specified name. Will return distinct copies if headers with the same name
     * exist.
     *
     * @see Message#addHeader(HttpHeaderDefinition, String)
     */
    List<HttpHeader> getHeader(String fieldName);

    /**
     * @param headerDefinition the header definition.
     *
     * @return Will return an empty list if there is not such a header. Will return a list containing a single element
     * if there is a single header with the specified name. Will return distinct copies if headers with the same name
     * exist.
     *
     * @see Message#addHeader(HttpHeaderDefinition, String)
     */
    List<HttpHeader> getHeader(HttpHeaderDefinition headerDefinition);


}
