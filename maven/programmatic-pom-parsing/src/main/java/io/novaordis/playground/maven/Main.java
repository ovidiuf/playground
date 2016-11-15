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

package io.novaordis.playground.maven;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuilder;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.File;
import java.io.FileReader;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 11/15/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Interprets the first argument as the path to a POM file and attempts to parse it.
     */
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            throw new Exception("specify the POM file as the first argument");
        }

        File f = new File(args[0]);

        Model model = simpleModelBuilding(f);

        System.out.println("Maven version: " + model.getVersion());
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    /**
     * This method does not take into account inheritance of the parent and interpolation of expressions.
     *
     * For a more complete model building see:
     *
     * @see Main#complexModelBuilding(File)
     */
    private static Model simpleModelBuilding(File file) throws Exception {

        MavenXpp3Reader reader = new MavenXpp3Reader();
        //noinspection UnnecessaryLocalVariable
        Model model = reader.read(new FileReader(file));
        return model;
    }

    /**
     *
     * TODO: This does not currently work.
     *
     */
    @SuppressWarnings("unused")
    private static Model complexModelBuilding(File file) throws Exception {

        ModelBuilder builder = new DefaultModelBuilder();
        ModelBuildingRequest request = new DefaultModelBuildingRequest();
        request.setProcessPlugins(false);
        request.setPomFile(file);
        // request.setModelResolver(new ModelResolver() {});
        request.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);

        //noinspection UnnecessaryLocalVariable
        Model model = builder.build(request).getEffectiveModel();
        return model;
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
