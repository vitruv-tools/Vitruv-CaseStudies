package tools.vitruv.applications.viewfilter.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification;
import tools.vitruv.applications.testutility.uml.UmlQueryUtil;
import tools.vitruv.applications.viewfilter.util.framework.impl.BasicView;
import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest;

@ExtendWith(RegisterMetamodelsInStandalone.class)
public class InstanceFilterTest extends ViewBasedVitruvApplicationTest {
	
	@Accessors(AccessorType.PROTECTED_GETTER)
	private static final String UML_MODEL_NAME = "model";
	
	@Accessors(AccessorType.PROTECTED_GETTER)
	private static final String PCM_MODEL_NAME = "Repository";
	

	private static final String PCM_REPOSITORY_FILE_EXTENSION = "repository";

	@Accessors(AccessorType.PROTECTED_GETTER)
	private static final String MODEL_FOLDER_NAME = "model";

	@Accessors(AccessorType.PROTECTED_GETTER)
	private static final String MODEL_FILE_EXTENSION = "uml";

	@Accessors(AccessorType.PUBLIC_GETTER)
	private org.eclipse.uml2.uml.Class class1;

	@Accessors(AccessorType.PUBLIC_GETTER)
	private org.eclipse.uml2.uml.Class class2;

	protected ViewTestFactory improvedViewTestFactory;
	
	@BeforeEach
	public void setup() {
		improvedViewTestFactory = new ViewTestFactory(getVirtualModel());
		final Procedure1<Model> setNameFunction = (Model it) -> {
			it.setName(UML_MODEL_NAME);
		};
		createBiggerUmlModel(setNameFunction);
		//createPcmModel();
	}
	
	
	//--- Actual tests ---
	@Test
	public void testView() throws NoSuchMethodException, InvocationTargetException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		View createUmlView = improvedViewTestFactory.createUmlView();
		Collection<EObject> rootObjects = createUmlView.getRootObjects();
		ViewSelection selection = createUmlView.getSelection();
		
		
		
//		Field declaredField = selection.getClass().getSuperclass().getDeclaredField("elementsSelection");
//		declaredField.setAccessible(true);
//		HashMap<EObject, Boolean> map = (HashMap<EObject, Boolean>) declaredField.get(createUmlView);
		
		//Selection should contain ModelImpl, and two SystemImpl. Only the ModelImpl should be 
		//selected.

		
		assert(rootObjects.stream().anyMatch(it -> it instanceof Model));
		assertEquals(rootObjects.size(), 1);
	}
	
	
	@Test
	public void testCreateFilteredUmlView() {
		//View view = improvedViewTestFactory.createFilteredUmlView(this);
		View view = improvedViewTestFactory.createFilteredPcmView();
//		((FilterSupportingIdentityMappingViewType) improvedViewTestFactory.viewType)
//				.updateView(((ModifiableView) view));
		view.update();
		view.update();
		view.getRootObjects();
		
		modifyModel();
		
		view.update();
		
		//view.getRootObjects();
		//Object object = view.withChangeDerivingTrait().getRootObjects().toArray()[0];
		view.getSelection();
		view.getRootObjects();
		view.getViewType();
	}
	
	
	
	
	protected void createBiggerUmlModel(final Procedure1<? super Model> modelInitialization) {
		try {
			final Consumer<CommittableView> firstChangeUmlFunction = (CommittableView it) -> {
				final Model umlModel = UMLFactory.eINSTANCE.createModel();
				createAndRegisterRoot(it, umlModel, this.getUri(getProjectModelPath(UML_MODEL_NAME)));
				modelInitialization.apply(umlModel);
			};
			improvedViewTestFactory.changeUmlView(firstChangeUmlFunction);

			getUserInteraction().addNextSingleSelection(1);
			getUserInteraction().addNextTextInput("model/System.system");

			final Consumer<CommittableView> secondChangeUmlFunction = (CommittableView it) -> {
				org.eclipse.uml2.uml.Package _createPackage = UMLFactory.eINSTANCE.createPackage();
				Procedure1<org.eclipse.uml2.uml.Package> setNameFunction = (org.eclipse.uml2.uml.Package it_1) -> {
					it_1.setName("niklasPackage");
				};

				org.eclipse.uml2.uml.Package package1 = UMLFactory.eINSTANCE.createPackage();
				package1.setName("niklasPackage");

				class1 = package1.createOwnedClass("niklasClass1", false);

				getUserInteraction().addNextSingleSelection(1);
				getUserInteraction().addNextTextInput("model/System.system");
				org.eclipse.uml2.uml.Package package2 = package1.createNestedPackage("niklasNestedPackage");

				class2 = package2.createOwnedClass("niklasClass2", false);
				EList<PackageableElement> _packagedElements = getDefaultUmlModel(it).getPackagedElements();
				_packagedElements.add(package1);

				// create Attribute for class2
				PrimitiveType stringPrimitiveType = package1.createOwnedPrimitiveType("niklasPrimitiveType1");
				class2.createOwnedAttribute("niklasClass2Attribute", stringPrimitiveType, 0, 1);
			};
			improvedViewTestFactory.changeUmlView(secondChangeUmlFunction);

		} catch (Throwable _e) {
			throw Exceptions.sneakyThrow(_e);
		}
	}
	
	private void modifyModel() {
		try {
			getUserInteraction().addNextSingleSelection(1);
			getUserInteraction().addNextTextInput("model/System.system");
			
			Consumer<CommittableView> changeUmlFunction = (CommittableView it) -> {
				org.eclipse.uml2.uml.Package package3 = UMLFactory.eINSTANCE.createPackage();
				package3.setName("niklasPackage3");
				
				org.eclipse.uml2.uml.Class class3 = package3.createOwnedClass("niklasClass3", false);
				org.eclipse.uml2.uml.Class class4 = package3.createOwnedClass("niklasClass2", false);
				
				class2.addKeyword("subsequentlyAddedKeyword");
				
				final Comment comment = this.class2.createOwnedComment();
				comment.setBody("niklasCommentClass2");
				String searchedName = "niklasClass2";
				comment.addKeyword("bla");
				
				TreeIterator<EObject> umlIterator = getDefaultUmlModel(it).eAllContents();
				org.eclipse.uml2.uml.Class searchedClass = null;
				while ((searchedClass == null) && (umlIterator.hasNext())) {
					EObject next = umlIterator.next();
					if (next instanceof org.eclipse.uml2.uml.Class) {
						if (searchedName.equals(((Classifier) next).getName())) {
							searchedClass = (org.eclipse.uml2.uml.Class) next;
						}
					}
				}
				searchedClass.getOwnedComments().add(comment);
				
				getDefaultUmlModel(it).getPackagedElements().add(package3);
			};
			
			improvedViewTestFactory.changeUmlView(changeUmlFunction);
		} catch (Throwable _e) {
			throw Exceptions.sneakyThrow(_e);
		}
	}
	
	private Path getPcmProjectModelPath(String modelName, String modelFileExtension) {
		return Path.of("pcm").resolve(modelName + "." + modelFileExtension);
	}
	
	protected void createAndRegisterRoot(final View view, final EObject rootObject, final URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri);
	}

	protected Path getProjectModelPath(final String modelName) {
		return Path.of(MODEL_FOLDER_NAME).resolve(((modelName + ".") + MODEL_FILE_EXTENSION));
	}

	protected Model getDefaultUmlModel(final View view) {
		return UmlQueryUtil.claimUmlModel(view, UML_MODEL_NAME);
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

}
