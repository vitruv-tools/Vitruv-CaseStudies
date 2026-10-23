package tools.vitruv.applications.c4cbse2joana;

import org.emftext.language.java.commons.NamedElement;

public final class JoanaAnnotationUtil {
	private JoanaAnnotationUtil() {
	}

	/**
	 * Checks whether a JOANA annotation refers to the given Java element, identified by its name.
	 * @param annotatedElement the element annotated by the JOANA annotation, may be {@code null}
	 * @param element the Java element
	 * @return whether the annotation refers to an element with the same name
	 */
	public static boolean annotates(NamedElement annotatedElement, NamedElement element) {
		return annotatedElement != null && annotatedElement.getName().equals(element.getName());
	}
}
