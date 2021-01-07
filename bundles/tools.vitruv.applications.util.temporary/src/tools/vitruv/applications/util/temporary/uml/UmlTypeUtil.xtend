package tools.vitruv.applications.util.temporary.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import java.util.function.Function
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.UMLPackage
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel

@Utility
class UmlTypeUtil { // TODO TS merge with UmlClassifierAndPackagableUtil?
    static val UML_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml")
    public static val UML_PRIMITIVE_BOOLEAN_TAG = "Boolean"
    public static val UML_PRIMITIVE_REAL_TAG = "Real"
    public static val UML_PRIMITIVE_INTEGER_TAG = "Integer"
    public static val UML_PRIMITIVE_STRING_TAG = "String"

    static def List<PrimitiveType> getSupportedPredefinedUmlPrimitiveTypes(ResourceSet rs) {
        var List<PrimitiveType> umlPrimitiveTypes = #[]
        val uri = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml")
        val resource = rs.getResource(uri, true)
        umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
        return umlPrimitiveTypes
    }

    static def List<PrimitiveType> getSupportedPredefinedUmlPrimitiveTypes(Function<URI, Resource> resourceRetriever) {
        var List<PrimitiveType> umlPrimitiveTypes = #[]
        val uri = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml")
        val resource = resourceRetriever.apply(uri)
        if (resource !== null) {
            umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
        }
        return umlPrimitiveTypes
    }

    def static registerPredefinedUmlPrimitiveTypes(CorrespondenceModel cm, ResourceSet rs) {
        var List<PrimitiveType> umlPrimitiveTypes = getSupportedPredefinedUmlPrimitiveTypes(rs)
        for (primitive : umlPrimitiveTypes) {
            val alreadyRegistered = ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, UMLPackage.Literals.PRIMITIVE_TYPE, primitive.name,
                PrimitiveType).head
            if (alreadyRegistered === null)
                ReactionsCorrespondenceHelper.addCorrespondence(cm, UMLPackage.Literals.PRIMITIVE_TYPE, primitive, primitive.name)
        }
    }

    def static getUmlPrimitiveTypes(EObject alreadyPersistedObject) {
        return getUmlPrimitiveTypes(alreadyPersistedObject.eResource.resourceSet)
    }

    def static getUmlPrimitiveTypes(Function<URI, Resource> resourceRetriever) {
        val resource = resourceRetriever.apply(UML_PRIMITIVE_TYPES_URI)
        val umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
        return umlPrimitiveTypes
    }

    def static getUmlPrimitiveTypes(ResourceSet resourceSet) {
        var List<PrimitiveType> umlPrimitiveTypes = #[]
        val resource = resourceSet.getResource(UML_PRIMITIVE_TYPES_URI, true)
        umlPrimitiveTypes = resource.allContents.filter(PrimitiveType).toList
        return umlPrimitiveTypes
    }

    def static isSupportedUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
        return switch (umlPrimitiveType.name) {
            case "Boolean",
            case "Integer",
            case "Real",
            case "String": true
            default: false
        }
    }
}
