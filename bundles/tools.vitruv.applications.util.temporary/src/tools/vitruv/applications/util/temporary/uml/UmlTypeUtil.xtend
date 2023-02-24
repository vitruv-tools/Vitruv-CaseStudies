package tools.vitruv.applications.util.temporary.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.function.Function
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.uml2.uml.PrimitiveType

@Utility
class UmlTypeUtil {
    static val UML_JAVA_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://UML_LIBRARIES/JavaPrimitiveTypes.library.uml")
    static val UML_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml")

	def static getUmlPrimitiveTypes(ResourceSet resourceSet) {
		return getUmlPrimitiveTypes([resourceSet.getResource(it, true)])
	}

    def static getUmlPrimitiveTypes(Function<URI, Resource> resourceRetriever) {
//    	val reg = Resource.Factory.Registry.INSTANCE;
//		val m = reg.getExtensionToFactoryMap();
//		m.put("library\\.uml", new XMIResourceFactoryImpl());
		
        val resources = #[
        	resourceRetriever.apply(UML_JAVA_PRIMITIVE_TYPES_URI),
        	resourceRetriever.apply(UML_PRIMITIVE_TYPES_URI)
        ]
        val primitiveTypes = resources.flatMap[allContents.toList].filter(PrimitiveType).toList
        return primitiveTypes
    }

}
