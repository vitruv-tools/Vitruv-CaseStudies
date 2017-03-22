package tools.vitruv.applications.umljava.java2uml

import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.Model
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.datatypes.VURI
import org.emftext.language.java.containers.ContainersFactory
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.domains.java.JavaNamespace
import org.emftext.language.java.containers.JavaRoot
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.eclipse.uml2.uml.UMLFactory
import org.apache.log4j.PropertyConfigurator
import tools.vitruv.framework.correspondence.Correspondence
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.modifiers.ModifiersFactory
import org.eclipse.emf.common.util.EList
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypesFactory
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.common.util.BasicEList
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.members.MembersFactory
import tools.vitruv.applications.umljava.java2uml.AbstractJavaUmlTest.JavaVisibility
import org.emftext.language.java.parameters.ParametersFactory
import org.eclipse.uml2.uml.VisibilityKind
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import org.emftext.language.java.members.Method
import org.emftext.language.java.members.Field

class AbstractJavaUmlTest extends VitruviusChangePropagationTest {
    private static val MODEL_FILE_EXTENSION = "uml";
    private static val MODEL_NAME = "model";
	
    private def String getProjectModelPath(String modelName) {
        "model/" + modelName + "." + MODEL_FILE_EXTENSION;
    }
	
	protected def Model getRootElement() {
	    return MODEL_NAME.projectModelPath.root as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new JavaToUmlChangePropagationSpecification()]; 
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new JavaDomain().metamodel];
	}
	
	override protected initializeTestModel() {
        PropertyConfigurator.configure("log4j.properties")
	}
	

    
    def protected createJavaClassWithCompilationUnit(String cName, JavaVisibility vis, boolean abstr, boolean fin) {
        val cu = createCompilationUnit(cName);
        
        val cls = ClassifiersFactory.eINSTANCE.createClass;
        if (cName == null) {
            throw new IllegalArgumentException("Classname is null");
        }
        cls.name = cName;
        if (vis != null) {
            cls.addModifier(getJavaVisibility(vis))
        }
        if (abstr) {
            cls.addModifier(ModifiersFactory.eINSTANCE.createAbstract)
        }
        if (fin) {
            cls.addModifier(ModifiersFactory.eINSTANCE.createFinal)
        }
        cu.classifiers += cls;
        saveAndSynchronizeChanges(cu);
        this.changeRecorder.beginRecording(VURI.getInstance(cls.eResource), #[cls.eResource])
        saveAndSynchronizeChanges(cls)
        return cls;
    }
    
    def private getJavaVisibility(JavaVisibility vis) {
        switch vis {
                case JavaVisibility.PUBLIC: return ModifiersFactory.eINSTANCE.createPublic
                case JavaVisibility.PRIVATE: return ModifiersFactory.eINSTANCE.createPrivate
                case JavaVisibility.PROTECTED: return ModifiersFactory.eINSTANCE.createProtected
                default: throw new IllegalArgumentException("Invalid Visibility: " + vis)
            }
    }
    
    
    def protected createSimpleJavaClassWithCompilationUnit(String name) {
        return createJavaClassWithCompilationUnit(name, null, false, false);
    }
    
    def protected createSimpleJavaInterfaceWithCompilationUnit(String name) {
        return createJavaInterfaceWithCompilationUnit (name, null);
    }
    
    /**
     * Sichtbarkeit wird automatisch auf Public gesetzt
     */
    def protected createJavaInterfaceWithCompilationUnit (String name, EList<Interface> superInterfaces) {
        val cu = createCompilationUnit(name);
        val jI = ClassifiersFactory.eINSTANCE.createInterface;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create JavalInterface - name is null");
        }
        jI.name = name;
        jI.makePublic;
        if (!superInterfaces.nullOrEmpty) {
            jI.extends.addAll(createTypeReferenceFromList(superInterfaces));
        }
        cu.classifiers += jI;
        saveAndSynchronizeChanges(cu);
        this.changeRecorder.beginRecording(VURI.getInstance(jI.eResource), #[jI.eResource])
        return jI;
    }
    
    /**
     * @return public Operation mit Namen; kein Return, Params oder Modifier
     */
    def protected createSimpleJavaOperation(String name) {
        return createJavaClassMethod(name, null, JavaVisibility.PUBLIC, false, false, null)
    }
    
    /**
     * Bei vis == null : wird auf Package-private gesetzt
     * return und params kann null sein.
     */
    def protected createJavaClassMethod(String name, TypeReference returnType, JavaVisibility vis, boolean abstr, boolean stat, EList<Parameter> params) {
        val jMeth = MembersFactory.eINSTANCE.createClassMethod;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create Java Method - name is null");
        }
        jMeth.name = name;
        if (returnType != null) {
            jMeth.typeReference = returnType;
        }
        if (vis != null) {
            jMeth.addModifier(getJavaVisibility(vis));
        }
        if (abstr) {
            jMeth.addModifier(ModifiersFactory.eINSTANCE.createAbstract)
        }
        if (stat) {
            jMeth.addModifier(ModifiersFactory.eINSTANCE.createStatic)
        }
        if (!params.nullOrEmpty) {
            jMeth.parameters.addAll(params);
        }
        return jMeth;
    }
    
    def protected createJavaInterfaceMethod(String name, TypeReference returnType, EList<Parameter> params) {
        val jMeth = MembersFactory.eINSTANCE.createInterfaceMethod;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create Java Method - name is null");
        }
        jMeth.name = name;
        if (returnType != null) {
            jMeth.typeReference = returnType;
        }
        jMeth.makePublic;
        if (!params.nullOrEmpty) {
            jMeth.parameters.addAll(params);
        }
        return jMeth;
    }
    
    def protected createJavaAttribute(String name, TypeReference type, JavaVisibility vis, boolean fin, boolean stat) {
        val attr = MembersFactory.eINSTANCE.createField;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create Java Field - name is null");
        }
        attr.name = name;
        if (vis != null) {
            attr.addModifier(getJavaVisibility(vis))
        }
        if (fin) {
            attr.addModifier(ModifiersFactory.eINSTANCE.createFinal)
        }
        if (stat) {
            attr.addModifier(ModifiersFactory.eINSTANCE.createStatic)
        }
        if (type != null) {
            attr.typeReference = type;
        }
        return attr;
    }
    
    def protected createJavaParameter(String name, TypeReference type) {
        val param = ParametersFactory.eINSTANCE.createOrdinaryParameter;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create JavaParameter - name is null");
        }
        param.name = name
        if (type != null) {
            param.typeReference = type;
        }
        return param;
    }
    
    /**
     * .java Endung wird in der Methode eingefügt
     */
    def private createCompilationUnit(String name) {
        val cu = ContainersFactory.eINSTANCE.createCompilationUnit
        cu.name = name + ".java";
        createAndSynchronizeModel(TestUtil.SOURCE_FOLDER + "/" + cu.name, cu)
        return cu
    }
    /**
     * @return das erste correspondierende Objekt. Null, wenn keins vorhanden.
     */
    def protected getCorrespondingObject(EObject obj) {
        val corrList = getCorrespondenceModel.getCorrespondingEObjects(#[obj]);
        if (corrList.nullOrEmpty) {
            return null
        } else if (corrList.head.nullOrEmpty) {
            return null;
        }
        return corrList.head.head
    }
    def protected <T> getCorrespondingObject(EObject obj, Class<T> c) {
        val corr = getCorrespondingObject(obj)
        if (corr == null) {
            return null;
        }
        return corr as T
    }
    
    enum JavaVisibility {
        PUBLIC, PRIVATE, PROTECTED
    }
    
    /**
     * @param uElem Uml-Klasse, Interface, Methode, Attribute, ...
     * @param vis Null, falls package-private
     */
    def protected void assertHasVisibility(org.eclipse.uml2.uml.NamedElement uElem, JavaVisibility vis) {
        switch vis {
            case PUBLIC: assertEquals(VisibilityKind.PUBLIC_LITERAL, uElem.visibility)
            case PRIVATE: assertEquals(VisibilityKind.PRIVATE_LITERAL, uElem.visibility)
            case PROTECTED: assertEquals(VisibilityKind.PROTECTED_LITERAL, uElem.visibility)
            case null: assertEquals(VisibilityKind.PACKAGE_LITERAL, uElem.visibility)
            default: fail("Unknown visibility: " + vis)
        }
        
    }
    def protected void assertUmlOperationHasUniqueParameter(org.eclipse.uml2.uml.Operation uOp, org.emftext.language.java.parameters.Parameter jParam) {
        val paramList = uOp.ownedParameters.filter[name == jParam.name]
        assertTrue(paramList.size == 1)
        val uParam = paramList.head
        assertEquals(getClassifierfromTypeRef(jParam.typeReference).name, uParam.type.name);
    }
   def protected assertNameAndReturnUniqueUmlMethod(Method meth) {
       val umlOperation = getCorrespondingObject(meth, org.eclipse.uml2.uml.Operation)
       assertEquals(meth.name, umlOperation.name)
       return umlOperation
   }
   
   def protected assertNameAndReturnUniqueUmlAttribute(Field jAttr) {
       val uAttr = getCorrespondingObject(jAttr, org.eclipse.uml2.uml.Property)
       assertEquals(jAttr.name, uAttr.name)
       return uAttr
   }
   
    def EList<TypeReference> createTypeReferenceFromList(EList<? extends org.emftext.language.java.classifiers.ConcreteClassifier> list) {
        val typeReferences = new BasicEList<TypeReference>
        for (org.emftext.language.java.classifiers.ConcreteClassifier i : list) {
            typeReferences += createNamespaceReferenceFromClassifier(i)
        }
        return typeReferences
    }

}
