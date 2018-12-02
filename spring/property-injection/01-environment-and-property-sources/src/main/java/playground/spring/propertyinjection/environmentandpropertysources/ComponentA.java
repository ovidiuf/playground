package playground.spring.propertyinjection.environmentandpropertysources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ComponentA {

    @Autowired
    private Environment environment;

    public void inspectEnvironment() {

        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();

        System.out.print("active profiles: ");

        for(int i = 0; i < activeProfiles.length; i ++) {

            System.out.print(activeProfiles[i]);
            if (i < activeProfiles.length - 1) {

                System.out.print(", ");
            }
        }

        System.out.println();

        System.out.print("default profiles: ");

        for(int i = 0; i < defaultProfiles.length; i ++) {

            System.out.print(defaultProfiles[i]);
            if (i < defaultProfiles.length - 1) {

                System.out.print(", ");
            }
        }

        System.out.println();
    }

    public void checkProperty(String propertyName) {

        if (!environment.containsProperty(propertyName)) {

            System.out.println("the environment does not contain \"" + propertyName + "\"");
        }
        else {

            System.out.println(propertyName + "=" + environment.getProperty(propertyName));
        }
    }
}
