package tools.vitruv.applications.viewfilter.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import com.niklas.niklasproject.niklasdomain.InformationStructure;
import com.niklas.niklasproject.niklasdomain.SingleInformation;

import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification;
import tools.vitruv.applications.testutility.uml.UmlQueryUtil;
import tools.vitruv.applications.viewfilter.util.framework.impl.BasicView;
import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView;
import tools.vitruv.applications.viewfilter.utils.PcmQueryUtil;
import tools.vitruv.applications.viewfilter.utils.PcmUmlClassApplicationTestHelper;
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
	
	private static final String PCM_BASIC_COMPONENT_NAME = "Niklas Basic PCM component 1";
	
	private static final String UML_MODEL_URI = "1234uri1234";

	@Accessors(AccessorType.PUBLIC_GETTER)
	private org.eclipse.uml2.uml.Class class1;

	@Accessors(AccessorType.PUBLIC_GETTER)
	private org.eclipse.uml2.uml.Class class2;

	protected ViewTestFactory improvedViewTestFactory;

	private Model umlModel;

	
	
	@BeforeEach
	public void setup() {
		improvedViewTestFactory = new ViewTestFactory(getVirtualModel());
		final Procedure1<Model> setNameFunction = (Model it) -> {
			it.setName(UML_MODEL_NAME);
		};
		createBiggerUmlModel(setNameFunction);
	}
	
	
	//--- Actual tests ---
	@Test
	public void testUmlView() throws NoSuchMethodException, InvocationTargetException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		View createUmlView = improvedViewTestFactory.createUmlView();
		Collection<EObject> rootObjects = createUmlView.getRootObjects();
		//Selection should contain ModelImpl, and two SystemImpl. Only the ModelImpl should be 
		//selected.
		//Root Objects should only contain ModelImpl
		assertEquals(countContainedModelImplInstances(rootObjects), 1);
		assertEquals(1, rootObjects.size());
		for (EObject root : rootObjects) {
			assertTrue(root instanceof ModelImpl);
			assertEquals(UML_MODEL_URI, ((ModelImpl) root).getURI());
		}		
	}
	
	
	@Test
	public void testCreateFilteredUmlView() {
		Function<EObject, Boolean> function = (EObject object) -> hasNoAttribute(object, "niklasClass2");
		View view = improvedViewTestFactory.createFilteredUmlView(function);
		
		view.update();
		view.update();
		Collection<EObject> rootObjects = view.getRootObjects();
		assertEquals(rootObjects.size(), 1);
		//TODO nbr: remove comments..
		//Selection: Nur ModelImpl mit niklasClass2 als einzigs PackagedElement
		//Root Objects: ModelImpl (aber nicht nur mit niklasClass2, sondern mit allem)
		for (EObject root : rootObjects) {
			assertTrue(root instanceof Model);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 1);
			EObject eObject = root.eContents().get(0);
			assertTrue(eObject instanceof org.eclipse.uml2.uml.Class);
			org.eclipse.uml2.uml.Class classObject = (Class) eObject;
			assertEquals(classObject.getName(), "niklasClass2");
		}
		
		modifyModel();
		view.update();
		view.getSelection();
		//Selection: Nur ModelImpl mit zwei Klassen die jeweils niklasClass2 als Namen haben, als PackagedElement
		//ACHTUNG: Die zwei Klassen sind als PackagedElements in der ModelImpl!
		//Root Objects: ModelImpl
		Collection<EObject> modifiedRootObjects = view.getRootObjects();
		assertEquals(modifiedRootObjects.size(), 1);
		for (EObject root : modifiedRootObjects) {
			assertTrue(root instanceof Model);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 2);
			for (EObject eObject : root.eContents()) {
				assertTrue(eObject instanceof org.eclipse.uml2.uml.Class);
				org.eclipse.uml2.uml.Class classObject = (Class) eObject;
				assertEquals(classObject.getName(), "niklasClass2");
			}			
		}
	}
	
	
	@Test
	public void testCreateFilteredUmlViewWithAdditionalPcmElementsInVsum() {
		createPcmModel();
		Function<EObject, Boolean> function = (EObject object) -> hasNoAttribute(object, "niklasClass2");
		
		View view = improvedViewTestFactory.createFilteredUmlView(function);
		view.update();
		view.update();
		//Selection: Nur ModelImpl mit niklasClass2 als einzigs PackagedElement
		//Root Objects: ModelImpl (aber nicht nur mit niklasClass2, sondern mit allem) und zwei weitere ModelImpls (aber andere)
		Collection<EObject> rootObjects = view.getRootObjects();
		assertEquals(rootObjects.size(), 1);
		for (EObject root : rootObjects) {
			assertTrue(root instanceof Model);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 1);
			for (EObject eObject : root.eContents()) {
				assertTrue(eObject instanceof org.eclipse.uml2.uml.Class);
				org.eclipse.uml2.uml.Class classObject = (Class) eObject;
				assertEquals(classObject.getName(), "niklasClass2");
			}			
		}
		
		modifyModel();
		view.update();
		view.getSelection();
		//Selection: Nur ModelImpl mit zwei Klassen die jeweils niklasClass2 als Namen haben, als PackagedElement
		//ACHTUNG: Die zwei Klassen sind als PackagedElements in der ModelImpl!
		//Root Objects: ModelImpl, ModelImpl, ModelImpl
		Collection<EObject> modifiedRootObjects = view.getRootObjects();
		assertEquals(modifiedRootObjects.size(), 1);
		for (EObject root : modifiedRootObjects) {
			assertTrue(root instanceof Model);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 2);
			for (EObject eObject : root.eContents()) {
				assertTrue(eObject instanceof org.eclipse.uml2.uml.Class);
				org.eclipse.uml2.uml.Class classObject = (Class) eObject;
				assertEquals(classObject.getName(), "niklasClass2");
			}			
		}

		view.getViewType();
	}
	
	
	@Test
	public void testPcmView() throws NoSuchMethodException, InvocationTargetException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		createPcmModel();
		View createPcmView = improvedViewTestFactory.createPcmView();
		Collection<EObject> rootObjects = createPcmView.getRootObjects();
		ViewSelection selection = createPcmView.getSelection();
		//Selection should contain 20 elements (0-19) with two RepositoryImpl selected.
		//Root elements should be two RepositoryImpl
		assertEquals(2, rootObjects.size());
		for (EObject root : rootObjects) {
			assertTrue(root instanceof Repository);
		}	
	}
	
	
	@Test
	public void testCreateFilteredPcmView() {
		createPcmModel();
		View view = improvedViewTestFactory.createFilteredPcmView((EObject object) -> hasInstanceName(object, PCM_BASIC_COMPONENT_NAME));
		view.update();
		view.update();
		
		Collection<EObject> rootObjects = view.getRootObjects();
		assertEquals(rootObjects.size(), 1);
		for (EObject root : rootObjects) {
			assertTrue(root instanceof Repository);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 1);
			EObject eObject = root.eContents().get(0);
			assertTrue(eObject instanceof BasicComponent);
			BasicComponent classObject = (BasicComponent) eObject;
			assertEquals(PCM_BASIC_COMPONENT_NAME, classObject.getEntityName());
		}
		
		//Selection: Nur ModelImpl mit niklasClass2 als einzigs PackagedElement
		//Root Objects: ModelImpl (aber nicht nur mit niklasClass2, sondern mit allem) und zwei weitere ModelImpls (aber andere)
		modifyModel();
		view.update();
		view.getSelection();
		//Selection: Nur ModelImpl mit zwei Klassen die jeweils niklasClass2 als Namen haben, als PackagedElement
		//ACHTUNG: Die zwei Klassen sind als PackagedElements in der ModelImpl!
		//Root Objects: ModelImpl, ModelImpl, ModelImpl
		Collection<EObject> modifiedRootObjects = view.getRootObjects();
		assertEquals(modifiedRootObjects.size(), 1);
		for (EObject root : modifiedRootObjects) {
			assertTrue(root instanceof Repository);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 1);
			EObject eObject = root.eContents().get(0);
			assertTrue(eObject instanceof BasicComponent);
			BasicComponent classObject = (BasicComponent) eObject;
			assertEquals(PCM_BASIC_COMPONENT_NAME, classObject.getEntityName());
		}
	}
	
	@Test
	public void testCreateCountingView() {
		Function<EObject, Boolean> function = (EObject object) -> hasNoAttribute(object, "niklasClass2");
		View view = improvedViewTestFactory.createCountUmlElementsView(function);
		Assertions.assertNotNull(view.getSelection(), "selection must not be null");
		
		Collection<EObject> rootObjects = view.getRootObjects();
		assertEquals(rootObjects.size(), 1);
		for (EObject root : rootObjects) {
			assertTrue(root instanceof InformationStructure);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 1);
			EObject eObject = root.eContents().get(0);
			assertTrue(eObject instanceof SingleInformation);
			SingleInformation singleInformation = (SingleInformation) eObject;
			assertEquals("Anzahl Elemente", singleInformation.getTitle());
			assertEquals(1, singleInformation.getValue());
		}
		
		
		modifyModel();
		view.update();
		
		rootObjects = view.getRootObjects();
		assertEquals(rootObjects.size(), 1);
		for (EObject root : rootObjects) {
			assertTrue(root instanceof InformationStructure);
			assertTrue(view.getSelection().isViewObjectSelected(root));
			assertEquals(root.eContents().size(), 1);
			EObject eObject = root.eContents().get(0);
			assertTrue(eObject instanceof SingleInformation);
			SingleInformation singleInformation = (SingleInformation) eObject;
			assertEquals("Anzahl Elemente", singleInformation.getTitle());
			assertEquals(2, singleInformation.getValue());
		}
		
		view.getSelection();
	}
	
	
	
	
	protected void createBiggerUmlModel(final Procedure1<? super Model> modelInitialization) {
		try {
			final Consumer<CommittableView> firstChangeUmlFunction = (CommittableView it) -> {
				umlModel = UMLFactory.eINSTANCE.createModel();
				createAndRegisterRoot(it, umlModel, this.getUri(getProjectModelPath(UML_MODEL_NAME)));
				modelInitialization.apply(umlModel);
				umlModel.setURI(UML_MODEL_URI);
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
	
	
	protected void createPcmModel() {
		
		getUserInteraction().addNextTextInput(PcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		Consumer<CommittableView> createPcmRepoFunction = (CommittableView it) -> {
			Repository repository = RepositoryFactory.eINSTANCE.createRepository();
			repository.setEntityName(PCM_MODEL_NAME);
			it.registerRoot(repository, getUri(getPcmProjectModelPath(repository.getEntityName(), PCM_REPOSITORY_FILE_EXTENSION)));
			//createAndRegisterRoot(it, repository, this.getUri(getProjectModelPath(PCM_MODEL_NAME)));		
		};
		
		try {
			improvedViewTestFactory.changePcmView(createPcmRepoFunction);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Consumer<CommittableView> changePcmFunction = (CommittableView it) -> {
			Repository repository = getDefaultPcmRepository(it);
			BasicComponent createBasicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
			createBasicComponent.setEntityName(PCM_BASIC_COMPONENT_NAME);
			repository.getComponents__Repository().add(createBasicComponent);
			
			CompositeDataType compositeDataType1 = RepositoryFactory.eINSTANCE.createCompositeDataType();
			compositeDataType1.setEntityName("niklasPcmCompositeDataType1");
			repository.getDataTypes__Repository().add(compositeDataType1);

		};
		
		try {
			improvedViewTestFactory.changePcmView(changePcmFunction);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean hasInstanceName(EObject object, String name) {
		if (object instanceof org.palladiosimulator.pcm.core.entity.NamedElement) {
			if (name.equals(((org.palladiosimulator.pcm.core.entity.NamedElement) object).getEntityName())) {
				return true;
			}
		}
		return false;
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

	private Repository getDefaultPcmRepository(View view) {
		return PcmQueryUtil.claimPcmRepository(view, PCM_MODEL_NAME);
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
	
	
	private int countContainedModelImplInstances(Collection<EObject> collection) {
		int count = 0;
		Iterator<EObject> iterator = collection.iterator();
		while(iterator.hasNext()) {
			EObject next = iterator.next();
			if (next instanceof ModelImpl) {
				count++;
			}
		}
		return count;
	}
	
	
	private boolean hasNoAttribute(EObject object, String name) {
		if (object instanceof org.eclipse.uml2.uml.Class) {
			if (object instanceof NamedElement) {
				if (name.equals(((NamedElement) object).getName())) {
					return true;
				}
			}
		}
		return false;
	}

}
