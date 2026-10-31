package tools.vitruv.applications.cbs.operators.domaincommon.operators;

import org.apache.log4j.Logger;
import org.eclipse.xtext.xbase.lib.StringExtensions;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Converts between a String with a lower case character at the front on the
 * participation side and a String with an upper case character at the front on
 * the commonality side.
 */
@AttributeMappingOperator(name = "firstUpper", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class FirstUpperOperator extends AbstractAttributeMappingOperator<String, String> {

    private static final Logger logger = Logger.getLogger(FirstUpperOperator.class);

    /**
     * Creates a new FirstUpperOperator.
     * 
     * @param executionState the reactions execution state
     */
    public FirstUpperOperator(ReactionExecutionState executionState) {
        super(executionState);
    }

    @Override
    public String applyTowardsCommonality(String string) {
        String firstUpper = StringExtensions.toFirstUpper(string);
        logger.debug(String.format("Mapping string '%s' to firstUpper '%s'.",
                string, firstUpper));
        return firstUpper;
    }

    @Override
    public String applyTowardsParticipation(String string) {
        String firstLower = StringExtensions.toFirstLower(string);
        logger.debug(String.format("Mapping string '%s' to firstLower '%s'.",
                string, firstLower));
        return firstLower;
    }
}