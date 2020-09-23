/*
 * Copyright (c) 2018 Nova Ordis LLC
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

package io.novaordis.playground;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Pattern p = Pattern.compile("\"reason\" *: *\"Forbidden\"");

        String s = "{\"kind\":\"Status\",\"apiVersion\":\"v1\",\"metadata\":{},\"status\":\"Failure\",\"message\":\"namespaces \\\"c3\\\" is forbidden: User \\\"system:serviceaccount:c3:default\\\" cannot get resource \\\"namespaces\\\" in API group \\\"\\\" in the namespace \\\"c3\\\"\",\"reason\":\"Forbidden\",\"details\":{\"name\":\"c3\",\"kind\":\"namespaces\"},\"code\":403}\n";

        Matcher m = p.matcher(s);

        System.out.println("found: " + m.find());




    }

}
