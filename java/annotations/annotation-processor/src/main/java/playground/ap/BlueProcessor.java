package playground.ap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

/**
 * Processes @Blue annotations.
 */
@SupportedAnnotationTypes("playground.ap.Blue")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BlueProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //
        // called by the compiler for every source file containing matching annotations. The annotations
        // are passed as Set<? extends TypeElement>, and the information about the current processing
        // round is passed as roundEnv. The method should return true if the annotation processor has
        // processed all passed annotations.
        //

        for(TypeElement annotation: annotations) {

            // these are the elements annotated with @Blue

            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            for(Element e: annotatedElements) {

                //
                // select only annotated fields
                //

                ElementKind kind = e.getKind();

                if (ElementKind.FIELD.equals(kind)) {

                    Name name = e.getSimpleName();
                    String s = name.toString();
                    System.out.println("name: " + s);

                }
            }
        }

        return false;
    }
}
