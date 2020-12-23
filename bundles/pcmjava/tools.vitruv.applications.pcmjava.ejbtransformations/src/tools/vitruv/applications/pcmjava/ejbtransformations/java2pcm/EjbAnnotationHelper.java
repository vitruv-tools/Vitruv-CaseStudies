package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;

public final class EjbAnnotationHelper {

    private static final Logger logger = Logger.getLogger(EjbAnnotationHelper.class.getSimpleName());

    private static final String STATELESS_ANNOTATION_NAME = "Stateless";
    private static final String STATEFUL_ANNOTATION_NAME = "Stateful";
    private static final String MESSAGE_DRIVEN_ANNOTATION_NAME = "MessageDriven";
    private static final String LOCAL_ANNOTATION_NAME = "Local";
    private static final String REMOTE_ANNOTATION_NAME = "Remote";
    private static final String EJB_ANNOTATION_NAME = "EJB";

    static final Set<String> EJB_COMPONENT_ANNOTATION_NAMES = new HashSet<String>(
            Arrays.asList(EjbAnnotationHelper.STATELESS_ANNOTATION_NAME, EjbAnnotationHelper.STATEFUL_ANNOTATION_NAME,
                    EjbAnnotationHelper.MESSAGE_DRIVEN_ANNOTATION_NAME));
    static final Set<String> EJB_BUISNESS_INTERFACE_ANNOTATION_NAMES = new HashSet<String>(
            Arrays.asList(EjbAnnotationHelper.LOCAL_ANNOTATION_NAME, EjbAnnotationHelper.REMOTE_ANNOTATION_NAME));

    private EjbAnnotationHelper() { 
    }

    public static boolean isEjbClass(final EObject eObject) {
    	if(!(eObject instanceof Class)){
    		return false;
    	}
    	Class jamoppClass = (Class) eObject;
        final boolean found = EjbAnnotationHelper.hasAnnoations(jamoppClass,
                EjbAnnotationHelper.EJB_COMPONENT_ANNOTATION_NAMES);
        if (found) {
            logger.info("Found EJB class: " + jamoppClass.getQualifiedName());
        }
        return found;
    }

    public static boolean filterAnnotationName(final AnnotationInstance annotation,
            final Set<String> annotationClassifiersToCheck) {
        final Classifier annotationClassifier = annotation.getAnnotation();
        if (null != annotationClassifier) {
            return annotationClassifiersToCheck.contains(annotationClassifier.getName());
        }
        return false;
    }

    public static boolean hasAnnoations(final AnnotableAndModifiable annotableAndModifiable,
            final Set<String> annotationClassifiersToCheck) {
        return annotableAndModifiable.getAnnotationsAndModifiers().stream()
                .filter(annotationOrModifier -> annotationOrModifier instanceof AnnotationInstance)
                .map(annotation -> (AnnotationInstance) annotation)
                .filter(annotation -> EjbAnnotationHelper.filterAnnotationName(annotation, annotationClassifiersToCheck))
                .collect(Collectors.toList()).size() > 0;
    }

    public static boolean isEjbBuisnessInterface(final EObject eObject) {
    	if(!(eObject instanceof Interface)){
    		return false;
    	}
    	final Interface implemententedInterface = (Interface) eObject;
        final boolean found = EjbAnnotationHelper.hasAnnoations(implemententedInterface,
                EjbAnnotationHelper.EJB_BUISNESS_INTERFACE_ANNOTATION_NAMES);
        if (found) {
            logger.info("Found EJB buisness interface " + implemententedInterface.getQualifiedName());
        }
        return found;
    }

    public static boolean hasEjbAnnotation(final EObject eObject) {
    	if(!(eObject instanceof Field)){
    		return false;
    	}
    	final Field field = (Field) eObject;
        final boolean found = EjbAnnotationHelper.hasAnnoations(field,
                new HashSet<String>(Arrays.asList(EjbAnnotationHelper.EJB_ANNOTATION_NAME)));
        if (found) {
            logger.info("Found field with EJB annotation: " + field.getName() + " in class: "
                    + field.getContainingConcreteClassifier().getQualifiedName());
        }
        return found;
    }

}
