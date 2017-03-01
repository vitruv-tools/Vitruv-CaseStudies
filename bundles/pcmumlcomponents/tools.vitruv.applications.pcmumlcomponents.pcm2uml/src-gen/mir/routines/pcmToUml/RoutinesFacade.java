package mir.routines.pcmToUml;

import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void renameUmlElement(final NamedElement pcmElement) {
    mir.routines.pcmToUml.RenameUmlElementRoutine effect = new mir.routines.pcmToUml.RenameUmlElementRoutine(this.executionState, calledBy,
    	pcmElement);
    effect.applyRoutine();
  }
  
  public void createUmlModel(final Repository pcmRepository) {
    mir.routines.pcmToUml.CreateUmlModelRoutine effect = new mir.routines.pcmToUml.CreateUmlModelRoutine(this.executionState, calledBy,
    	pcmRepository);
    effect.applyRoutine();
  }
  
  public void createPrimitiveDataType(final PrimitiveDataType dataType) {
    mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine effect = new mir.routines.pcmToUml.CreatePrimitiveDataTypeRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void deleteDataType(final DataType dataType) {
    mir.routines.pcmToUml.DeleteDataTypeRoutine effect = new mir.routines.pcmToUml.DeleteDataTypeRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void createCompositeDataType(final CompositeDataType dataType) {
    mir.routines.pcmToUml.CreateCompositeDataTypeRoutine effect = new mir.routines.pcmToUml.CreateCompositeDataTypeRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void createInnerDeclaration(final InnerDeclaration innerDeclaration) {
    mir.routines.pcmToUml.CreateInnerDeclarationRoutine effect = new mir.routines.pcmToUml.CreateInnerDeclarationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void deleteInnerDeclaration(final InnerDeclaration innerDeclaration) {
    mir.routines.pcmToUml.DeleteInnerDeclarationRoutine effect = new mir.routines.pcmToUml.DeleteInnerDeclarationRoutine(this.executionState, calledBy,
    	innerDeclaration);
    effect.applyRoutine();
  }
  
  public void createCollectionDataType(final CollectionDataType dataType) {
    mir.routines.pcmToUml.CreateCollectionDataTypeRoutine effect = new mir.routines.pcmToUml.CreateCollectionDataTypeRoutine(this.executionState, calledBy,
    	dataType);
    effect.applyRoutine();
  }
  
  public void createUmlPropertyForDatatype(final org.eclipse.uml2.uml.DataType type, final InnerDeclaration counterpart, final org.eclipse.uml2.uml.DataType owner) {
    mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine effect = new mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine(this.executionState, calledBy,
    	type, counterpart, owner);
    effect.applyRoutine();
  }
  
  public void createUmlComponent(final RepositoryComponent pcmComponent) {
    mir.routines.pcmToUml.CreateUmlComponentRoutine effect = new mir.routines.pcmToUml.CreateUmlComponentRoutine(this.executionState, calledBy,
    	pcmComponent);
    effect.applyRoutine();
  }
}
