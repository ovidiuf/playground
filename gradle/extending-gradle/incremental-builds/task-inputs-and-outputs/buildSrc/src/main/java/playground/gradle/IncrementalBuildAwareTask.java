package playground.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class IncrementalBuildAwareTask extends DefaultTask {

   @TaskAction
   void impl() {
      System.out.println("this is an custom enhanced task developed in buildSrc");
   }
}
