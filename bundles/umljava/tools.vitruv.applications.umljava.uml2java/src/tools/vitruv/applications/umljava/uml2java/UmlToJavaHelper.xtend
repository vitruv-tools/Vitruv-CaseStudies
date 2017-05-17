package tools.vitruv.applications.umljava.uml2java

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.EnumerationLiteral
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import static tools.vitruv.applications.umljava.util.java.JavaStandardType.*
import static extension tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*
import org.emftext.language.java.members.MembersFactory
import java.util.Set
import java.util.HashSet
import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType
import org.emftext.language.java.members.Field
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.emftext.language.java.containers.CompilationUnit

class UmlToJavaHelper {
    
	private static val logger = Logger.getLogger(UmlToJavaHelper);
	private new() {
	}
    
    def static void setJavaVisibility(AnnotableAndModifiable jModifiable, VisibilityKind uVisibility) {
        setJavaVisibilityModifier(jModifiable, getJavaVisibilityConstantFromUmlVisibilityKind(uVisibility))
    }
    
    /**
 	 * Creates a Java-NamespaceReference if cType != null
     * Creates a PrimitiveType if dType != null && cType == null
     * Creates Void if both Types are null
     * 
     * @param dType uml-DataType
     * @param cType java-Class
     */
	def static TypeReference createTypeReferenceAndUpdateImport(Type dType, ConcreteClassifier cType, CompilationUnit compUnit, UserInteracting userInteracting) {
		if (dType === null && cType === null) {
		    return TypesFactory.eINSTANCE.createVoid();
		} else if (cType !== null) {
		    val namespaceOftype = cType.javaNamespace.join
		    if (!namespaceOftype.equals(compUnit.javaNamespace.join)) {
		        compUnit.imports += createJavaClassImport(namespaceOftype)
		    }
	        return createNamespaceReferenceFromClassifier(cType);
	    } else if (dType instanceof PrimitiveType) {
	        return mapToJavaPrimitiveType(dType);
	    } else {// dType is not null and not primitive, but a unknown Classifier.
	        val dClass = ClassifiersFactory.eINSTANCE.createClass;
	        dClass.name = dType.name;
	        userInteracting.showMessage(UserInteractionType.MODAL, "Added unknown Type " + dType + " in " +  compUnit.name + ". Please check the validity of imports.")
	        return createNamespaceReferenceFromClassifier(dClass)
		}
	} 
	
	/**
	 * Returns the corresponding Java-PrimitiveType by checking the given Uml-PrimitiveType's name.
	 * (Case-sensitive)
	 * Returns null if no corresponding Java-PrimitiveType could be found.
	 */
	def static org.emftext.language.java.types.TypeReference mapToJavaPrimitiveType(PrimitiveType pType) {
	    try {
	        createJavaPrimitiveType(pType.name)
	    } catch (IllegalArgumentException i){
	        return null
	    }
	}
	
    def static letUserSelectCollectionTypeName(UserInteracting userInteracting) {
    	var List<java.lang.Class<?>> collectionDataTypes = new ArrayList
		collectionDataTypes += #[ArrayList, LinkedList, HashSet]
		val List<String> collectionDataTypeNames = new ArrayList<String>(collectionDataTypes.size)
		for (collectionDataType : collectionDataTypes) {
			collectionDataTypeNames.add(collectionDataType.name)
		}
		val String selectTypeMsg = "Select a Collectiontype for the association end"
		val int selectedType = userInteracting.selectFromMessage(UserInteractionType.MODAL, selectTypeMsg,
			collectionDataTypeNames)
		val java.lang.Class<?> selectedClass = collectionDataTypes.get(selectedType)
	    return selectedClass.name
    }
    
    def static createGetterForAttribute(Field jAttribute) {
        val jGetter = createJavaGetterForAttribute(jAttribute, JavaVisibility.PUBLIC)
        (jAttribute.eContainer as org.emftext.language.java.classifiers.Class).members += jGetter
    }
    
    def static createSetterForAttribute(Field jAttribute) {
        val jSetter = createJavaSetterForAttribute(jAttribute, JavaVisibility.PUBLIC)
        (jAttribute.eContainer as org.emftext.language.java.classifiers.Class).members += jSetter
    }
    
    def static createSetterForAttributeWithNullCheck(Field jAttribute) {
        val jSetter = createJavaSetterForAttributeWithNullCheck(jAttribute, JavaVisibility.PUBLIC)
        (jAttribute.eContainer as org.emftext.language.java.classifiers.Class).members += jSetter
    }
    
    def static showMessage(UserInteracting userInteracting, String message) {
        userInteracting.showMessage(message)
    }
	
}