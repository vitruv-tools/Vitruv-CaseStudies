package tools.vitruv.applications.util.temporary.uml;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import org.eclipse.uml2.uml.*;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * Utility class for operations related to UML properties and associations.
 *
 * Provides helper methods to create UML attributes and associations
 * with specific characteristics.
 *
 * Author: Fei
 */
@Utility
@SuppressWarnings("all")
public final class UmlPropertyAndAssociationUtil {

    private UmlPropertyAndAssociationUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a UML attribute (Property) with the specified characteristics.
     *
     * @param name       the name of the attribute
     * @param type       the UML type of the attribute
     * @param visibility the visibility of the attribute (e.g., public, private)
     * @param isFinal    true if the attribute is read-only
     * @param isStatic   true if the attribute is static
     * @return           a configured UML Property instance
     */
    public static Property createUmlAttribute(
            final String name,
            final Type type,
            final VisibilityKind visibility,
            final boolean isFinal,
            final boolean isStatic) {

        Property attribute = UMLFactory.eINSTANCE.createProperty();
        UmlClassifierAndPackageUtil.setName(attribute, name);
        attribute.setVisibility(visibility);
        attribute.setIsReadOnly(isFinal);
        attribute.setIsStatic(isStatic);
        attribute.setType(type);

        return attribute;
    }

    /**
     * Creates a directed UML association from one class to another.
     *
     * @param fromClass   the source class of the association
     * @param toClass     the target class of the association
     * @param lowerLimit  lower bound of the target's multiplicity
     * @param upperLimit  upper bound of the target's multiplicity
     * @return            a configured UML Association instance
     */
    public static Association createDirectedAssociation(
            final org.eclipse.uml2.uml.Class fromClass,
            final org.eclipse.uml2.uml.Class toClass,
            final int lowerLimit,
            final int upperLimit) {

        return fromClass.createAssociation(
                true,
                AggregationKind.NONE_LITERAL,
                StringExtensions.toFirstLower(toClass.getName()),
                lowerLimit,
                upperLimit,
                toClass,
                false,
                AggregationKind.NONE_LITERAL,
                StringExtensions.toFirstLower(fromClass.getName()),
                0,
                1
        );
    }

    /**
     * Creates a ValueSpecification representing an integer literal.
     *
     * @param value the integer value
     * @return      a UML LiteralInteger with the specified value
     */
    public static ValueSpecification createValueSpecificationWithIntValue(final int value) {
        LiteralInteger literal = UMLFactory.eINSTANCE.createLiteralInteger();
        literal.setValue(value);
        return literal;
    }
}
