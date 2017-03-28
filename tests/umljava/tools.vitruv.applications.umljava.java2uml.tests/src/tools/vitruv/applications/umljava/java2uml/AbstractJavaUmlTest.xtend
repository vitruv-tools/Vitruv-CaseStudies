package tools.vitruv.applications.umljava.java2uml

import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.Model
import tools.vitruv.domains.java.JavaDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusChangePropagationTest
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.datatypes.VURI
import org.apache.log4j.PropertyConfigurator
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList
import org.emftext.language.java.classifiers.Interface
import org.eclipse.uml2.uml.VisibilityKind
import static tools.vitruv.applications.umljava.util.JavaUtil.*
import org.emftext.language.java.members.Method
import org.emftext.language.java.members.Field
import tools.vitruv.applications.umljava.util.JavaUtil.JavaVisibility

class AbstractJavaUmlTest extends VitruviusChangePropagationTest {
    //private static val MODEL_FILE_EXTENSION = "uml";
    //private static val MODEL_NAME = "model";
	
    private def String getProjectModelPath(String modelName) {
        //"model/" + modelName + "." + MODEL_FILE_EXTENSION;
    }
	
	protected def Model getRootElement() {
	    //return MODEL_NAME.projectModelPath.root as Model;
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
        val cu = createCompilationUnitAsModel(cName);
        val cls = createJavaClass(cName, vis, abstr, fin)
        cu.classifiers += cls;
        saveAndSynchronizeChanges(cu);
        this.changeRecorder.beginRecording(VURI.getInstance(cls.eResource), #[cls.eResource])
        return cls;
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
        val cu = createCompilationUnitAsModel(name);
        val jI = createJavaInterface(name, superInterfaces)
        cu.classifiers += jI;
        saveAndSynchronizeChanges(cu);
        this.changeRecorder.beginRecording(VURI.getInstance(jI.eResource), #[jI.eResource])
        return jI;
    }

    /**
     * @param Name ohne .java
     */
    def private createCompilationUnitAsModel(String name) {
        val cu = createEmptyCompilationUnit(name)
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
    
    /**
     * @param uElem Uml-Klasse, Interface, Methode, Attribute, ...
     */
    def protected void assertHasVisibility(org.eclipse.uml2.uml.NamedElement uElem, JavaVisibility vis) {
        switch vis {
            case PUBLIC: assertEquals(VisibilityKind.PUBLIC_LITERAL, uElem.visibility)
            case PRIVATE: assertEquals(VisibilityKind.PRIVATE_LITERAL, uElem.visibility)
            case PROTECTED: assertEquals(VisibilityKind.PROTECTED_LITERAL, uElem.visibility)
            case PACKAGE: assertEquals(VisibilityKind.PACKAGE_LITERAL, uElem.visibility)
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

    def protected getUmlPackagedElementsbyName(String modelPath, Class<? extends org.eclipse.uml2.uml.Classifier> type, String className) {
        val m = getModelResource(modelPath).allContents.head as Model
        return m.packagedElements.filter(type).filter[it.name == className].toList;
    }

}
