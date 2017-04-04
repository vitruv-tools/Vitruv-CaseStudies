package mir.reactions;

import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels http://palladiosimulator.org/PalladioComponentModel/5.1 and http://www.emftext.org/java.
 * To add further change processors overwrite the setup method.
 */
<<<<<<< HEAD:bundles/pcmjava/tools.vitruv.applications.pcmjava.pojotransformations.pcm2java/src-gen/mir/reactions/AbstractChangePropagationSpecification5_1ToJava.java
public abstract class AbstractChangePropagationSpecification5_1ToJava extends CompositeChangePropagationSpecification {
	private final tools.vitruv.framework.util.datatypes.MetamodelPair metamodelPair;
	
	public AbstractChangePropagationSpecification5_1ToJava() {
=======
public abstract class AbstractChangePropagationSpecificationPcmToJava extends CompositeChangePropagationSpecification {
	private final tools.vitruv.framework.util.datatypes.MetamodelPair metamodelPair;
	
	public AbstractChangePropagationSpecificationPcmToJava() {
>>>>>>> master:bundles/pcmjava/tools.vitruv.applications.pcmjava.depinjecttransformations.pcm2java/src-gen/mir/reactions/AbstractChangePropagationSpecificationPcmToJava.java
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor());
		this.metamodelPair = new tools.vitruv.framework.util.datatypes.MetamodelPair("http://palladiosimulator.org/PalladioComponentModel/5.1", "http://www.emftext.org/java");
		setup();
	}
	
	public tools.vitruv.framework.util.datatypes.MetamodelPair getMetamodelPair() {
		return metamodelPair;
	}	
	
	/**
<<<<<<< HEAD:bundles/pcmjava/tools.vitruv.applications.pcmjava.pojotransformations.pcm2java/src-gen/mir/reactions/AbstractChangePropagationSpecification5_1ToJava.java
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecification5_1ToJava}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactions5_1ToJava.pcm2java.Executor5_1ToJava(getUserInteracting()));
=======
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationPcmToJava}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ExecutorPcmToJava(getUserInteracting()));
>>>>>>> master:bundles/pcmjava/tools.vitruv.applications.pcmjava.depinjecttransformations.pcm2java/src-gen/mir/reactions/AbstractChangePropagationSpecificationPcmToJava.java
	}
	
}
