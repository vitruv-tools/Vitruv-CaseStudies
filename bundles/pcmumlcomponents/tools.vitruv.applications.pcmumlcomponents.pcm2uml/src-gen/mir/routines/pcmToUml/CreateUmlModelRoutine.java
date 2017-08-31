package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlModelRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePackageImportElement(final Repository pcmRepository, final PackageImport packageImport) {
      final ResourceSetImpl resourceSet = new ResourceSetImpl();
      final URI primitiveTypesUri = URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI).appendFragment("_0");
      final EObject primitiveTypes = resourceSet.getEObject(primitiveTypesUri, true);
      packageImport.setImportedPackage(((org.eclipse.uml2.uml.Package) primitiveTypes));
    }
    
    public EObject getElement1(final Repository pcmRepository, final PackageImport packageImport, final Model umlModel) {
      return pcmRepository;
    }
    
    public EObject getElement2(final Repository pcmRepository, final PackageImport packageImport, final Model umlModel) {
      return umlModel;
    }
    
    public void updateUmlModelElement(final Repository pcmRepository, final PackageImport packageImport, final Model umlModel) {
      umlModel.setName(pcmRepository.getEntityName());
      EList<PackageImport> _packageImports = umlModel.getPackageImports();
      _packageImports.add(packageImport);
      String _entityName = pcmRepository.getEntityName();
      String _plus = ("model/" + _entityName);
      String _plus_1 = (_plus + ".uml");
      this.persistProjectRelative(pcmRepository, umlModel, _plus_1);
    }
  }
  
  public CreateUmlModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository pcmRepository) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateUmlModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmRepository = pcmRepository;
  }
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlModelRoutine with input:");
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    org.eclipse.uml2.uml.PackageImport packageImport = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackageImport();
    notifyObjectCreated(packageImport);
    userExecution.updatePackageImportElement(pcmRepository, packageImport);
    
    org.eclipse.uml2.uml.Model umlModel = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createModel();
    notifyObjectCreated(umlModel);
    userExecution.updateUmlModelElement(pcmRepository, packageImport, umlModel);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmRepository, packageImport, umlModel), userExecution.getElement2(pcmRepository, packageImport, umlModel), "");
    
    postprocessElements();
    
    return true;
  }
}
