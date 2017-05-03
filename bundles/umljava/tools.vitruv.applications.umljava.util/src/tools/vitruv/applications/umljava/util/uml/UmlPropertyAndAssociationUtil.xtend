package tools.vitruv.applications.umljava.util.uml

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.AggregationKind
import org.eclipse.uml2.uml.Association
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.ValueSpecification
import org.eclipse.uml2.uml.VisibilityKind
import static tools.vitruv.applications.umljava.util.CommonUtil.*

import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.setName

class UmlPropertyAndAssociationUtil {
    private static val logger = Logger.getLogger(UmlPropertyAndAssociationUtil.simpleName)
    
    /**
     * Prevents instantiation
     */
    private new () {}
    
    /**
     * @throws IllegalArgumentException if name or visibility is null
     */
    def static createUmlAttribute(String name, Type type, VisibilityKind visibility, boolean fin, boolean stat) {
        val uAttribute = UMLFactory.eINSTANCE.createProperty;
        setName(uAttribute, name)
        uAttribute.visibility = visibility;
        uAttribute.isReadOnly = fin;
        uAttribute.isStatic = stat;
        uAttribute.type = type;

        return uAttribute;
    }
    
    def static Association createDirectedAssociation(Class fromClass, Class toClass, int lowerLimit, int upperLimit) {
        fromClass.createAssociation(true, AggregationKind.NONE_LITERAL, firstLettertoLowercase(toClass.name), lowerLimit, upperLimit, toClass, false, AggregationKind.NONE_LITERAL, firstLettertoLowercase(fromClass.name), 0, 1)
    }
    
    def static ValueSpecification createValueSpecificationWithIntValue(int value) {
        val valueSpecification = UMLFactory.eINSTANCE.createLiteralInteger
        valueSpecification.value = value
        return valueSpecification
    }
}