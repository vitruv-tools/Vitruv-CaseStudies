package tools.vitruv.applications.umljava.uml2java

import tools.vitruv.applications.umljava.uml2java.UmlToJavaChangePropagationSpecification
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import org.eclipse.uml2.uml.UMLFactory
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*;
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.common.util.EList
import org.apache.log4j.Logger
import static org.junit.Assert.*
import org.apache.log4j.PropertyConfigurator
import org.emftext.language.java.members.Method
import org.emftext.language.java.members.Member
import org.eclipse.uml2.uml.DataType
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Private
import static tools.vitruv.applications.umljava.util.JavaUtil.*

class AbstractUmlJavaTest extends VitruviusChangePropagationTest {
    protected static val final Logger logger = Logger.getLogger(typeof(AbstractUmlJavaTest).simpleName);
	private static val MODEL_FILE_EXTENSION = "uml";
	private static val MODEL_NAME = "model";

	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.root as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new UmlToJavaChangePropagationSpecification()]; 
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new JavaDomain().metamodel];
	}
	
	override protected initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.name = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel);
		PropertyConfigurator.configure("log4j.properties")
	}

	def protected assertJavaFileExists(String fileName) {
	    assertModelExists(buildJavaFilePath(fileName));
	}
    def protected assertJavaFileNotExists(String fileName) {
        assertModelNotExists(buildJavaFilePath(fileName));
    }
    
    /**
     * Überprüft, ob Java-Classifiere cName exakt eine Java-Member namens memName besitzt und gibt sie als Typ von memberKind zurück.
     * 
     * @throws ClassCastException wenn Cast zu Typ von memberKind ungültig.
     */
    protected def <T extends Member> T assertUniqueJavaMemberExistsInClass(String cName, String memName, Class<T> memberKind) throws ClassCastException {
        val jMethods = getJavaMembersFromClassiferByName(cName, memName);
        assertNotNull(jMethods);
        assertTrue(jMethods.size == 1);
        return jMethods.head as T
    }
    
    /**
     * Überprüft, ob Java-Classifier cName keine Java-Methode namens methName besitzt.
     */
    protected def assertJavaMemberNotExistsInClass(String cName, String methName) {
        val jMems = getJavaMembersFromClassiferByName(cName, methName);
        assertTrue(jMems.empty);
    }
    
    /**
     * @param jMem Java-TypedElement (Attribute, Method, Parameter)
     * @param uType Uml-Typ
     */
    def protected void assertJavaMemberHasType(org.emftext.language.java.types.TypedElement jMem, org.eclipse.uml2.uml.Classifier uType) {
        if (uType instanceof org.eclipse.uml2.uml.PrimitiveType) {
            assertEquals(uType.name, jMem.typeReference.eClass.name.toLowerCase)
        } else if (uType instanceof org.eclipse.uml2.uml.Class) {
            assertEquals(uType.name, getClassifierfromTypeRef(jMem.typeReference).name)
        } else if (uType instanceof DataType) {
            //TODO 
        } else {
            throw new IllegalArgumentException("Invalid Uml-Type: " + uType.class)
        }
    }
    
    protected def <T extends Modifier> void assertJavaModifiableHasModifier(AnnotableAndModifiable jMem, Class<T> mod) {
        assertTrue(jMem.hasModifier(mod))
    }
    
    protected def  <T extends Modifier> void assertJavaModifiableDontHaveModifier(AnnotableAndModifiable jMem, Class<T> mod) {
        assertFalse(jMem.hasModifier(mod))
    }
    
    protected def void assertJavaModifiableHasVisibility(AnnotableAndModifiable jMem, VisibilityKind vis) {
        switch(vis) {
            case VisibilityKind.PUBLIC_LITERAL: {
                assertJavaModifiableHasModifier(jMem, Public);
                assertJavaModifiableDontHaveModifier(jMem, Private);
                assertJavaModifiableDontHaveModifier(jMem, Protected);
            }
            case VisibilityKind.PRIVATE_LITERAL: {
                assertJavaModifiableHasModifier(jMem, Private);
                assertJavaModifiableDontHaveModifier(jMem, Public);
                assertJavaModifiableDontHaveModifier(jMem, Protected);
            }
            case VisibilityKind.PROTECTED_LITERAL: {
                assertJavaModifiableHasModifier(jMem, Protected);
                assertJavaModifiableDontHaveModifier(jMem, Private);
                assertJavaModifiableDontHaveModifier(jMem, Public);
            }
            case VisibilityKind.PACKAGE_LITERAL: {
                assertJavaModifiableDontHaveModifier(jMem, Public);
                assertJavaModifiableDontHaveModifier(jMem, Private);
                assertJavaModifiableDontHaveModifier(jMem, Protected);
            }
            default: throw new IllegalArgumentException("Unknown VisibilityKind: " + vis)
        }
    }
	
	def protected org.emftext.language.java.classifiers.Class getJClassFromName(String name) {
	    return getJClassifFromName(org.emftext.language.java.classifiers.Class, name);
	}
    def protected org.emftext.language.java.classifiers.Interface getJInterfaceFromName(String name) {
        return getJClassifFromName(org.emftext.language.java.classifiers.Interface, name);
    }
	
	def private <T extends org.emftext.language.java.classifiers.ConcreteClassifier> T getJClassifFromName(java.lang.Class<T> c, String name) {
        val iter = getModelResource(buildJavaFilePath(name), true).allContents.filter(c);
        val returnClassif = iter.next() as T
        if (iter.hasNext()) {
            logger.info("Es gibt zum Namen " + name +  " des Typs " + c +  "mehr als eine Java-Datei")
        }
        return returnClassif
    }
    
    
    /**
     * Gibt Liste an Java-Member mit den Namen memName in Java-Classifier classifName zurück, Liste kann leer sein.
     */
    def protected EList<Member> getJavaMembersFromClassiferByName(String classifName, String memName) {
        return getJClassifFromName(org.emftext.language.java.classifiers.ConcreteClassifier, classifName).getMembersByName(memName);
    }
    
    /**
     * Gibt Liste an Java-Member mit den Namen methName in Java-Classifier classifName zurück und überprüft, ob diese Liste nicht leer ist.
     */
    def protected EList<Member> assertJavaMemberExistsInClassifierByName(String classifName, String memName) {
        val jMems = getJavaMembersFromClassiferByName(classifName, memName);
        assertNotNull(jMems);
        return jMems;
    }
    /**
     * @param jMethod Java-Methode
     * @param param Uml-Parameter
     */
    protected def assertJavaMethodHasUniqueParameter(Method jMethod, org.eclipse.uml2.uml.Parameter param) {
        assertFalse(jMethod.parameters.nullOrEmpty);
        assertTrue(jMethod.parameters.size == 1);
        val jparam = jMethod.parameters.head;
        assertEquals(param.type.name, (getClassifierfromTypeRef(jparam.typeReference).name));
        assertEquals(param.name, jparam.name);
    }

}
