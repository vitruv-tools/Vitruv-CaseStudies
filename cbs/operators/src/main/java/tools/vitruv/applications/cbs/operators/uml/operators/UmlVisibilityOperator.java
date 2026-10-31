package tools.vitruv.applications.cbs.operators.uml.operators;

import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.VisibilityKind;

import tools.vitruv.applications.cbs.operators.oo.Visibility;
import tools.vitruv.applications.cbs.operators.uml.UmlVisibilityHelper;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

@AttributeMappingOperator(name = "umlVisibility", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = VisibilityKind.class))
public class UmlVisibilityOperator extends AbstractAttributeMappingOperator<String, VisibilityKind> {

    private static final Logger logger = Logger.getLogger(UmlVisibilityOperator.class);

    public UmlVisibilityOperator(ReactionExecutionState executionState) {
        super(executionState);
    }

    @Override
    public String applyTowardsCommonality(VisibilityKind umlVisibility) {
        Visibility visibility = UmlVisibilityHelper.toVisibility(umlVisibility); // can be null
        String visibilityName = (visibility == null) ? null : visibility.name();
        logger.debug(String.format("Mapping UML VisibilityKind %s to visibility %s.", umlVisibility, visibilityName));
        return visibilityName;
    }

    @Override
    public VisibilityKind applyTowardsParticipation(String visibilityName) {
        Visibility visibility = Visibility.byName(visibilityName); // can be null
        VisibilityKind umlVisibility = UmlVisibilityHelper.toUmlVisibility(visibility); // can be null
        logger.debug(String.format("Mapping visibility %s to UML VisibilityKind %s.", visibilityName, umlVisibility));
        return umlVisibility;
    }
}