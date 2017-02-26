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

package io.novaordis.playground.json.jackson.fulldatabinding.json2java.model;

import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/26/17
 */
public class Root {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String nullExample;
    private Boolean booleanExample;
    private String stringExample;
    private Integer intExample;
    private Float floatExample;
    private ObjectExample objectExample;
    private List<String> arrayExample;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public String getNullExample() {

        return nullExample;
    }

    public void setNullExample(String s) {

        this.nullExample = s;
    }

    public Boolean getBooleanExample() {

        return booleanExample;
    }

    public void setBooleanExample(Boolean b) {

        this.booleanExample = b;
    }

    public String getStringExample() {

        return stringExample;
    }

    public void setStringExample(String s) {

        this.stringExample = s;
    }

    public Integer getIntExample() {

        return intExample;
    }

    public void setIntExample(Integer i) {

        this.intExample = i;
    }

    public Float getFloatExample() {

        return floatExample;
    }

    public void setFloatExample(Float f) {

        this.floatExample = f;
    }

    public ObjectExample getObjectExample() {

        return objectExample;
    }

    public void setObjectExample(ObjectExample e) {

        this.objectExample = e;
    }

    public List<String> getArrayExample() {

        return arrayExample;
    }

    public void setArrayExample(List<String> a) {

        this.arrayExample = a;
    }

    @Override
    public String toString() {

        String s = "";

        s += "nullExample:     " + getNullExample() + "\n";
        s += "booleanExample:  " + getBooleanExample() + "\n";
        s += "stringExample:   " + getStringExample() + "\n";
        s += "intExample:      " + getIntExample() + "\n";
        s += "floatExample:    " + getFloatExample() + "\n";
        s += "objectExample:   " + getObjectExample() + "\n";
        s += "arrayExample:    " + getArrayExample() + "\n";

        return s;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
