package tools.vitruv.applications.cbs.operators.domaincommon.operators;

import org.apache.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AbstractAttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeType;
import tools.vitruv.dsls.reactions.runtime.state.ReactionExecutionState;

/**
 * Converts between {@code "<prefix><suffix>"} on the participation side and
 * {@code "<prefix>"} on the commonality side.
 */
@AttributeMappingOperator(name = "prefix", commonalityAttributeType = @AttributeType(multiValued = false, type = String.class), participationAttributeType = @AttributeType(multiValued = false, type = String.class))
public class PrefixOperator extends AbstractAttributeMappingOperator<String, String> {

    private static final Logger logger = Logger.getLogger(PrefixOperator.class);
    private final String suffix;

    /**
     * Creates a new PrefixOperator.
     * 
     * @param executionState the reactions execution state
     * @param suffix         the suffix, can be empty
     * @throws IllegalArgumentException if suffix is null
     */
    public PrefixOperator(ReactionExecutionState executionState, String suffix) {
        super(executionState);
        checkNotNull(suffix, "suffix is null");
        this.suffix = suffix;
    }

    @Override
    public String applyTowardsCommonality(String fullText) {
        // Extract prefix by removing suffix if present
        String prefix = fullText;
        if (fullText.endsWith(suffix)) {
            prefix = fullText.substring(0, fullText.length() - suffix.length());
        }

        logger.debug(String.format("Mapping full text '%s' to prefix '%s'.",
                fullText, prefix));

        return prefix;
    }

    @Override
    public String applyTowardsParticipation(String prefix) {
        // Append suffix to create full text
        String fullText = prefix + suffix;

        logger.debug(String.format("Mapping prefix '%s' to full text '%s'.",
                prefix, fullText));

        return fullText;
    }
}