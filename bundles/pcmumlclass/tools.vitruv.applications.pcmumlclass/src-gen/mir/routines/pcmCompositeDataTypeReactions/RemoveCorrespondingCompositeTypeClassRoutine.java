package mir.routines.pcmCompositeDataTypeReactions;

import java.io.IOException;
import mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType pcmType, final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPackage, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      return umlDatatypesPackage;
    }
    
    public void update0Element(final CompositeDataType pcmType, final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPackage, final org.eclipse.uml2.uml.Class umlCompositeClass) {
      EList<PackageableElement> _packagedElements = umlDatatypesPackage.getPackagedElements();
      _packagedElements.remove(umlCompositeClass);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeClass(final CompositeDataType pcmType, final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPackage) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmType, final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlDatatypesPackage(final CompositeDataType pcmType, final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public String getRetrieveTag2(final CompositeDataType pcmType, final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPackage) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public RemoveCorrespondingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.RemoveCorrespondingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.pcmRepo = pcmRepo;
  }
  
  private CompositeDataType pcmType;
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingCompositeTypeClassRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   pcmRepo: " + this.pcmRepo);
    
    org.eclipse.uml2.uml.Package umlDatatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlDatatypesPackage(pcmType, pcmRepo), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType, pcmRepo), 
    	false // asserted
    	);
    if (umlDatatypesPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlDatatypesPackage);
    org.eclipse.uml2.uml.Class umlCompositeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompositeClass(pcmType, pcmRepo, umlDatatypesPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmType, pcmRepo, umlDatatypesPackage), 
    	false // asserted
    	);
    if (umlCompositeClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlCompositeClass);
    // val updatedElement userExecution.getElement1(pcmType, pcmRepo, umlDatatypesPackage, umlCompositeClass);
    userExecution.update0Element(pcmType, pcmRepo, umlDatatypesPackage, umlCompositeClass);
    
    postprocessElements();
    
    return true;
  }
}
