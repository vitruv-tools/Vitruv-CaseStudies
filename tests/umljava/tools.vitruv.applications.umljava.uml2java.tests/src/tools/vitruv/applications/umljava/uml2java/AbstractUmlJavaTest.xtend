package tools.vitruv.applications.umljava.uml2java

import tools.vitruv.applications.umljava.uml2java.UmlToJavaChangePropagationSpecification
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import org.eclipse.uml2.uml.UMLFactory
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*;
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.uml2.uml.Parameter
import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.eclipse.uml2.uml.Interface
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
	
	/**
	 * Bei vis = null : Wird auf Package-private gesetzt
	 */
	def protected createSyncUmlClass(String name, VisibilityKind vis, boolean abstr, boolean fin) {
	    val uC = UMLFactory.eINSTANCE.createClass;
	    if (name == null) {
	        throw new IllegalArgumentException("Cannot create UmlClass - name is null");
	    }
        uC.name = name;
        if (vis != null) {
            uC.visibility = vis;
        } else {
            uC.visibility = VisibilityKind.PACKAGE_LITERAL;
        }
        uC.isAbstract = abstr;
        uC.isFinalSpecialization = fin;
     
        rootElement.packagedElements += uC;
        saveAndSynchronizeChanges(uC);
        
        return uC;
	}
	
	def protected createSyncSimpleUmlClass(String name) {
	    return createSyncUmlClass(name, null, false, false);
	}
	

    def protected createSyncSimpleUmlInterface(String name) {
        return createSyncUmlInterface(name, null);
    }
    /**
     * visibility wird automatisch auf public gesetzt.
     */
	def protected Interface createSyncUmlInterface(String name, EList<Interface> superInterfaces) {
	    val uI = UMLFactory.eINSTANCE.createInterface;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create UmlInterface - name is null");
        }
        uI.name = name;
        uI.visibility = VisibilityKind.PUBLIC_LITERAL;
        if (!superInterfaces.nullOrEmpty) {
            uI.generals.addAll(superInterfaces);
        }
        rootElement.packagedElements += uI;
        saveAndSynchronizeChanges(uI);
        
        return uI;
	}
	
	/**
	 * @return public Operation mit Namen; kein Return, Params oder Modifier
	 */
	def protected Operation createSimpleUmlOperation(String name) {
	    return createUmlOperation(name, null, VisibilityKind.PUBLIC_LITERAL, false, false, null)
	}
	
	/**
	 * Bei vis == null : wird auf Package-private gesetzt
	 * return und params kann null sein.
	 */
	def protected Operation createUmlOperation(String name, Type returnType, VisibilityKind vis, boolean abstr, boolean stat, EList<Parameter> params) {
	    val op = UMLFactory.eINSTANCE.createOperation;
	    if (name == null) {
            throw new IllegalArgumentException("Cannot create UmlOperation - name is null");
        }
	    op.name = name;
	    if (returnType != null) {
	        op.type = returnType;
	    }
	    if (vis != null) {
	        op.visibility = vis;
	    } else {
	        op.visibility = VisibilityKind.PACKAGE_LITERAL;
	    }
	    op.isAbstract = abstr;
	    op.isStatic = stat;
	    if (!params.nullOrEmpty) {
	        op.ownedParameters.addAll(params);
	    }
	    return op;
	}
	
	def protected Operation createUmlInterfaceOperation(String name, Type returnType, EList<Parameter> params) {
	    return createUmlOperation(name, returnType, VisibilityKind.PUBLIC_LITERAL, false, false, params);
	}
	
	def protected createUmlAttribute(String name, Type type, VisibilityKind vis, boolean fin, boolean stat) {
	    val attr = UMLFactory.eINSTANCE.createProperty;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create UmlProperty - name is null");
        }
	    attr.name = name;
	    attr.visibility = vis;
	    attr.isReadOnly = fin;
	    attr.isStatic = stat;
	    if (type != null) {
	        attr.type = type;
	    }
	    return attr;
	}
	
	/**
	 * Wenn name == null, wird es ein Returntyp.
	 */
	def protected createUmlParameter(String name, Type type) {
	    val param = UMLFactory.eINSTANCE.createParameter;
	    param.type = type;
	    if (name == null) {
	        param.direction = ParameterDirectionKind.RETURN_LITERAL;
	    } else {
	        param.name = name;
	        param.direction = ParameterDirectionKind.IN_LITERAL;
	    }
	    return param;
	}
	
	def protected createUmlPrimitiveType (String name) {
	    val pType = UMLFactory.eINSTANCE.createPrimitiveType;
	    if (name == null) {
	        throw new IllegalArgumentException("Invalid Primitive Type name: " + name);
	    }
        pType.name = name;
        rootElement.packagedElements += pType
        return pType;
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
     * @Exception ClassCastException wenn Cast zu Typ von memberKind ungültig.
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
