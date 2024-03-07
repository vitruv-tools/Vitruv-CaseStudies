package tools.vitruv.applications.viewfilter.tests;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest;

@ExtendWith(RegisterMetamodelsInStandalone.class)
public class EmfTest extends ViewBasedVitruvApplicationTest {

	
	private static final ResourceSet RESOURCE_SET;

	static {
		RESOURCE_SET = new ResourceSetImpl();
	}
	
	
	@Test
	public void testtest() {
		
	}
	

	@Test
	public void testCreateFilteredUmlView() {
		Model model = UMLFactory.eINSTANCE.createModel();
		Model umlMetaModel = (Model) load(URI.createURI(UMLResource.UML_METAMODEL_URI));
		Class createClass = UMLFactory.eINSTANCE.createClass();
		System.out.println("Test");
		Model umlMetaModelCopy = EcoreUtil.copy(umlMetaModel);
		(umlMetaModelCopy.eClass()).getEPackage().getEFactoryInstance();
		
		//EFactory eFactoryInstance = ((EPackage) umlMetaModel).getEFactoryInstance();
		
		//EcoreUtil.create(umlMetaModelCopy);
		
		EObject root = umlMetaModelCopy;
		TreeIterator<EObject> content = root.eAllContents();
		List<EObject> contentList = convertTreeIterator2List(content);
		for (EObject object : contentList) {
			if (object instanceof org.eclipse.uml2.uml.Classifier) {
				org.eclipse.uml2.uml.Class classifierObject = (org.eclipse.uml2.uml.Class) object;
				classifierObject.getOwnedAttributes().removeAll(classifierObject.getOwnedAttributes());
			}
		}	
		
		EFactory eFactoryInstance = (umlMetaModelCopy.eClass()).getEPackage().getEFactoryInstance();
		
		
		
	}
	
	private List<EObject> convertTreeIterator2List(TreeIterator<EObject> content) {
		List<EObject> list = new LinkedList<EObject>();
		while(content.hasNext()) {
			list.add(content.next());
		}
		return list;
	}
	
	
	@Override
	protected Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications() {
		CombinedPcmToUmlClassReactionsChangePropagationSpecification _combinedPcmToUmlClassReactionsChangePropagationSpecification = new CombinedPcmToUmlClassReactionsChangePropagationSpecification();
		CombinedUmlClassToPcmReactionsChangePropagationSpecification _combinedUmlClassToPcmReactionsChangePropagationSpecification = new CombinedUmlClassToPcmReactionsChangePropagationSpecification();
		return Collections.<ChangePropagationSpecification>unmodifiableList(
				CollectionLiterals.<ChangePropagationSpecification>newArrayList(
						_combinedPcmToUmlClassReactionsChangePropagationSpecification,
						_combinedUmlClassToPcmReactionsChangePropagationSpecification));
	}

	
	private static org.eclipse.uml2.uml.Package load(URI uri) {
        //org.eclipse.uml2.uml.Package package_ = null;
		org.eclipse.uml2.uml.Package package_ = null;

        try {
        	//Stackoverflow
        	RESOURCE_SET.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
            // Load the requested resource
            Resource resource = RESOURCE_SET.getResource(uri, true);

            // Get the first (should be only) package from it  (Stackoverflow should take the first Package for non uml)
            package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
            //package_ =  resource.getContents().get(0);
        } catch (WrappedException we) {
            System.out.println(we.getMessage());
            System.exit(1);
        }

        return package_;
    }
	
	
//	protected void createBiggerUmlModel(final Procedure1<? super Model> modelInitialization) {
//		try {
//			final Consumer<CommittableView> firstChangeUmlFunction = (CommittableView it) -> {
//				final Model umlModel = UMLFactory.eINSTANCE.createModel();
//				createAndRegisterRoot(it, umlModel, this.getUri(getProjectModelPath(UML_MODEL_NAME)));
//				modelInitialization.apply(umlModel);
//			};
//			improvedViewTestFactory.changeUmlView(firstChangeUmlFunction);
//
//			getUserInteraction().addNextSingleSelection(1);
//			getUserInteraction().addNextTextInput("model/System.system");
//
//			final Consumer<CommittableView> secondChangeUmlFunction = (CommittableView it) -> {
//				org.eclipse.uml2.uml.Package _createPackage = UMLFactory.eINSTANCE.createPackage();
//				Procedure1<org.eclipse.uml2.uml.Package> setNameFunction = (org.eclipse.uml2.uml.Package it_1) -> {
//					it_1.setName("niklasPackage");
//				};
//
//				org.eclipse.uml2.uml.Package package1 = UMLFactory.eINSTANCE.createPackage();
//				package1.setName("niklasPackage");
//
//				org.eclipse.uml2.uml.Class class1 = package1.createOwnedClass("niklasClass1", false);
//
//				getUserInteraction().addNextSingleSelection(1);
//				getUserInteraction().addNextTextInput("model/System.system");
//				org.eclipse.uml2.uml.Package package2 = package1.createNestedPackage("niklasNestedPackage");
//
//				org.eclipse.uml2.uml.ClassClass class2 = package2.createOwnedClass("niklasClass2", false);
//				EList<PackageableElement> _packagedElements = getDefaultUmlModel(it).getPackagedElements();
//				_packagedElements.add(package1);
//
//				// create Attribute for class2
//				PrimitiveType stringPrimitiveType = package1.createOwnedPrimitiveType("niklasPrimitiveType1");
//				class2.createOwnedAttribute("niklasClass2Attribute", stringPrimitiveType, 0, 1);
//			};
//			improvedViewTestFactory.changeUmlView(secondChangeUmlFunction);
//
//		} catch (Throwable _e) {
//			throw Exceptions.sneakyThrow(_e);
//		}
//	}
	
}
