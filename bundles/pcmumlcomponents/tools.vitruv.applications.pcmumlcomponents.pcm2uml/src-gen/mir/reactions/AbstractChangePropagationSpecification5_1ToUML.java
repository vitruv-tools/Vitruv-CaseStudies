package mir.reactions;

import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels http://palladiosimulator.org/PalladioComponentModel/5.1 and http://www.eclipse.org/uml2/5.0.0/UML.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecification5_1ToUML extends CompositeChangePropagationSpecification {
	private final tools.vitruv.framework.util.datatypes.MetamodelPair metamodelPair;
	
	public AbstractChangePropagationSpecification5_1ToUML() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor());
		this.metamodelPair = new tools.vitruv.framework.util.datatypes.MetamodelPair("http://palladiosimulator.org/PalladioComponentModel/5.1", "http://www.eclipse.org/uml2/5.0.0/UML");
		setup();
	}
	
	public tools.vitruv.framework.util.datatypes.MetamodelPair getMetamodelPair() {
		return metamodelPair;
	}	
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecification5_1ToUML}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactions5_1ToUML.pcmToUml.Executor5_1ToUML(getUserInteracting()));
	}
	
}
