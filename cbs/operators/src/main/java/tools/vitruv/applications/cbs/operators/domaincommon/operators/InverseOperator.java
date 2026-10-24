package tools.vitruv.applications.cbs.operators.domaincommon.operators;

import org.apache.log4j.Logger;

import tools.vitruv.dsls.commonalities.runtime.helper.XtendAssertHelper;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Converts between two boolean attributes, with one being the inverse of the
 * other.
 */
@AttributeMappingOperator(name = "inverse", commonalityAttributeType = @AttributeType(multiValued = false, type = Boolean.class), participationAttributeType = @AttributeType(multiValued = false, type = Boolean.class))
public class InverseOperator extends AbstractAttributeMappingOperator<Boolean, Boolean> {

    private static final Logger logger = Logger.getLogger(InverseOperator.class);

    /**
     * Creates a new InverseOperator.
     * 
     * @param executionState the reactions execution state
     */
    public InverseOperator(ReactionExecutionState executionState) {
        super(executionState);
    }

    @Override
    public Boolean applyTowardsCommonality(Boolean value) {
        XtendAssertHelper.assertTrue(value != null);
        Boolean result = !value;
        logger.debug(String.format("Mapping Boolean '%s' to Boolean '%s'.", value, result));
        return result;
    }

    @Override
    public Boolean applyTowardsParticipation(Boolean value) {
        XtendAssertHelper.assertTrue(value != null);
        Boolean result = !value;
        logger.debug(String.format("Mapping Boolean '%s' to Boolean '%s'.", value, result));
        return result;
    }
}