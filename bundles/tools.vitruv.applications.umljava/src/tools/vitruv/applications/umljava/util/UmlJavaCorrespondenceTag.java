package tools.vitruv.applications.umljava.util;

import edu.kit.ipd.sdq.activextendannotations.Utility;

/** Collection of various Strings used to tag correspondences in the UML Java application. */
@Utility
public class UmlJavaCorrespondenceTag {
	/** The tag marks the correspondence between an UML property and a Java getter method for the corresponding Java field. */
	public static final String GETTER = "getter";
	
	/** The tag marks the correspondence between an UML property and a Java setter method for the corresponding Java field. */
	public static final String SETTER = "setter";
}
