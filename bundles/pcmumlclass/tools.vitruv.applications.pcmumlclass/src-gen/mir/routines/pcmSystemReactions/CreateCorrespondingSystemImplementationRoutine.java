package mir.routines.pcmSystemReactions;

import java.io.IOException;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingSystemImplementationRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingSystemImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSource1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return pcmSystem;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return umlSystemImplementation;
    }
    
    public EObject getCorrepondenceSourceUmlSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public void updateUmlSystemImplementationElement(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      String _entityName = pcmSystem.getEntityName();
      String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
      umlSystemImplementation.setName(_plus);
      umlSystemImplementation.setIsFinalSpecialization(true);
      EList<PackageableElement> _packagedElements = umlSystemPackage.getPackagedElements();
      _packagedElements.add(umlSystemImplementation);
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Package umlSystemPackage, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
  }
  
  public CreateCorrespondingSystemImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.CreateCorrespondingSystemImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingSystemImplementationRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    org.eclipse.uml2.uml.Package umlSystemPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlSystemPackage(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem), 
    	false // asserted
    	);
    if (umlSystemPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlSystemPackage);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSystem, umlSystemPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSystem, umlSystemPackage)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Class umlSystemImplementation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(umlSystemImplementation);
    userExecution.updateUmlSystemImplementationElement(pcmSystem, umlSystemPackage, umlSystemImplementation);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemPackage, umlSystemImplementation), userExecution.getElement2(pcmSystem, umlSystemPackage, umlSystemImplementation), userExecution.getTag1(pcmSystem, umlSystemPackage, umlSystemImplementation));
    
    postprocessElements();
    
    return true;
  }
}
