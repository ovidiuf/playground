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

package io.novaordis.playground.jee.ejb2rest;

import io.novaordis.playground.jee.ejb2rest.callee.CalleeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
@SuppressWarnings("unused")
public class EJBToRestConversionApplication extends Application {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(EJBToRestConversionApplication.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Set<Class<?>> classes;

    // Constructors ----------------------------------------------------------------------------------------------------

    public EJBToRestConversionApplication() {

        classes = new HashSet<>();
        classes.add(CalleeImpl.class);

        log.info("EJBToRestConversionApplication deployed");
    }


    // Application overrides -------------------------------------------------------------------------------------------

    @Override
    public Set<Object> getSingletons() {

        return Collections.emptySet();
    }

    @Override
    public Set<Class<?>> getClasses() {

        return classes;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
