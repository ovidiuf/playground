package playground.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

public class IncrementalBuildAwareTask extends DefaultTask {

   private final Logger logger;
   private File output;

   public IncrementalBuildAwareTask() {
      this.logger = getProject().getLogger();
   }

   @OutputFile
   public File getOutput() {
      return output;
   }

   public void setOutput(File f) {
      this.output = f;
   }

   @TaskAction
   void impl() {

      try {
         Files.write(output.toPath(), new Date().toString().getBytes());
         logger.quiet(output  + " file was created");
      }
      catch (Exception e) {
         throw new RuntimeException("failed to write output " + output + ": " + e.getMessage());
      }
   }
}
