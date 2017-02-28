package mir.routines.pcm2javaEjb;

import java.io.IOException;
import java.util.Collections;
import mir.routines.pcm2javaEjb.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Public;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.applications.pcmjava.util.transformations.pcm2java.helper.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaInterfaceEffect extends AbstractEffectRealization {
  public CreateJavaInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(responseExecutionState, calledBy);
    				this.sourceElementMappedToClass = sourceElementMappedToClass;this.containingPackage = containingPackage;this.className = className;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  private EObject getElement0(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
    return javaInterface;
  }
  
  private EObject getElement1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
    return sourceElementMappedToClass;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   Package: " + this.containingPackage);
    getLogger().debug("   String: " + this.className);
    
    Interface javaInterface = ClassifiersFactoryImpl.eINSTANCE.createInterface();
    initializeCreateElementState(javaInterface);
    
    addCorrespondenceBetween(getElement0(sourceElementMappedToClass, containingPackage, className, javaInterface), getElement1(sourceElementMappedToClass, containingPackage, className, javaInterface), "");
    preprocessElementStates();
    new mir.routines.pcm2javaEjb.CreateJavaInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceElementMappedToClass, containingPackage, className, javaInterface);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2javaEjb.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      javaInterface.setName(className);
      Public _createPublic = ModifiersFactory.eINSTANCE.createPublic();
      javaInterface.addModifier(_createPublic);
      Pcm2JavaHelper.addAnnotationToAnnotableAndModifiable(javaInterface, "Remote");
      this.effectFacade.callCreateCompilationUnit(sourceElementMappedToClass, javaInterface, containingPackage);
      Pcm2JavaHelper.addImportToClassFromString(javaInterface, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("javax", "ejb")), "Remote");
    }
  }
}
