package playground.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Files;

public class IncrementalBuildAwareTask extends DefaultTask {

   private final Logger logger;

   private String customVersion;
   private File output;

   public IncrementalBuildAwareTask() {
      this.logger = getProject().getLogger();
   }

   @Input
   public String getCustomVersion() {
      return customVersion;
   }

   public void setCustomVersion(String s) {
      this.customVersion = s;
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

      String s = "version: " + customVersion + "\n";

      try {
         Files.write(output.toPath(), s.getBytes());
         logger.quiet(output  + " file was created");
      }
      catch (Exception e) {
         throw new RuntimeException("failed to write output " + output + ": " + e.getMessage());
      }
   }
}
