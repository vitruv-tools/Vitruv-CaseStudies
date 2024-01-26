package tools.vitruv.applications.viewfilter.tests;

import com.niklas.niklasproject.niklasdomain.InformationStructure;
import com.niklas.niklasproject.niklasdomain.NiklasdomainFactory;

import java.nio.file.Path;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification;
import tools.vitruv.applications.testutility.uml.UmlQueryUtil;
import tools.vitruv.applications.viewfilter.util.framework.impl.FilterSupportingIdentityMappingViewType;
import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView;
import tools.vitruv.applications.viewfilter.utils.PcmQueryUtil;
import tools.vitruv.applications.viewfilter.utils.PcmUmlClassApplicationTestHelper;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest;

@ExtendWith(RegisterMetamodelsInStandalone.class)
public class BasicViewFilterTest extends ViewBasedVitruvApplicationTest {

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

	@Test
	public void testCreateSystemConcept_PCM() {
		System.out.println("Hello world");
	}

	@Test
	public void testView() {
		improvedViewTestFactory.createUmlView();
	}

	@Test
	public void testCreateUmlPcmView() {
		improvedViewTestFactory.createUmlAndPcmClassesView();
	}

	@Test
	public void testCreateFilteredUmlView() {
		View view = improvedViewTestFactory.createFilteredUmlView(this);
	//	View view = improvedViewTestFactory.createFilteredPcmView();
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
	
	@Test
	public void testCreateCountingView() {
		Function<EObject, Boolean> function = (EObject object) -> hasNoAttribute(object, "niklasClass2");
		View view = improvedViewTestFactory.createCountUmlElementsView(function);
		view.getSelection();
		view.getRootObjects();
		
		modifyModel();
		
		view.update();
		
		view.getSelection();
	}
	
	

//	  @Test
//	  public void testFilterForName() {
//	    final Procedure1<Model> _function = (Model it) -> {
//	      it.setName((FirstTest.UML_MODEL_NAME + "big"));
//	    };
//	    this.createAdvancedUmlModel(_function);
//	  }

	@Override
	protected Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications() {
		CombinedPcmToUmlClassReactionsChangePropagationSpecification _combinedPcmToUmlClassReactionsChangePropagationSpecification = new CombinedPcmToUmlClassReactionsChangePropagationSpecification();
		CombinedUmlClassToPcmReactionsChangePropagationSpecification _combinedUmlClassToPcmReactionsChangePropagationSpecification = new CombinedUmlClassToPcmReactionsChangePropagationSpecification();
		return Collections.<ChangePropagationSpecification>unmodifiableList(
				CollectionLiterals.<ChangePropagationSpecification>newArrayList(
						_combinedPcmToUmlClassReactionsChangePropagationSpecification,
						_combinedUmlClassToPcmReactionsChangePropagationSpecification));
	}
	

	@BeforeEach
	public void setup() {
		improvedViewTestFactory = new ViewTestFactory(getVirtualModel());
		final Procedure1<Model> setNameFunction = (Model it) -> {
			it.setName(UML_MODEL_NAME);
		};
		createBiggerUmlModel(setNameFunction);
		//createPcmModel();
	}
	
	protected void createUmlModel(final Procedure1<? super Model> modelInitialization) {
		try {
			final Consumer<CommittableView> firstChangeUmlFunction = (CommittableView it) -> {
				final Model umlModel = UMLFactory.eINSTANCE.createModel();
				createAndRegisterRoot(it, umlModel, this.getUri(getProjectModelPath(UML_MODEL_NAME)));
				modelInitialization.apply(umlModel);
			};
			improvedViewTestFactory.changeUmlView(firstChangeUmlFunction);
			
		} catch (Throwable _e) {
			throw Exceptions.sneakyThrow(_e);
		}
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
			createBasicComponent.setEntityName("Niklas Basic PCM component 1");
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

	@Pure
	public org.eclipse.uml2.uml.Class getClass1() {
		return this.class1;
	}

	@Pure
	public org.eclipse.uml2.uml.Class getClass2() {
		return this.class2;
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
