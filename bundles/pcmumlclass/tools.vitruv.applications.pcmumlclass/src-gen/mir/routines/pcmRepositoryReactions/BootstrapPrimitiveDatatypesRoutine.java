package mir.routines.pcmRepositoryReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.List;
import mir.routines.pcmRepositoryReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class BootstrapPrimitiveDatatypesRoutine extends AbstractRepairRoutineRealization {
  private BootstrapPrimitiveDatatypesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPkg, @Extension final RoutinesFacade _routinesFacade) {
      final List<PrimitiveDataType> pcmPrimitiveTypes = PcmUmlClassHelper.getPcmPrimitiveTypes(pcmRepo);
      final List<PrimitiveType> umlPrimitiveTypes = PcmUmlClassHelper.getUmlPrimitiveTypes(pcmRepo);
      for (final PrimitiveDataType pcmType : pcmPrimitiveTypes) {
        {
          PrimitiveType _switchResult = null;
          PrimitiveTypeEnum _type = pcmType.getType();
          if (_type != null) {
            switch (_type) {
              case BOOL:
                final Function1<PrimitiveType, Boolean> _function = (PrimitiveType it) -> {
                  String _name = it.getName();
                  return Boolean.valueOf(Objects.equal(_name, "Boolean"));
                };
                _switchResult = IterableExtensions.<PrimitiveType>findFirst(umlPrimitiveTypes, _function);
                break;
              case INT:
                final Function1<PrimitiveType, Boolean> _function_1 = (PrimitiveType it) -> {
                  String _name = it.getName();
                  return Boolean.valueOf(Objects.equal(_name, "Integer"));
                };
                _switchResult = IterableExtensions.<PrimitiveType>findFirst(umlPrimitiveTypes, _function_1);
                break;
              case DOUBLE:
                final Function1<PrimitiveType, Boolean> _function_2 = (PrimitiveType it) -> {
                  String _name = it.getName();
                  return Boolean.valueOf(Objects.equal(_name, "Real"));
                };
                _switchResult = IterableExtensions.<PrimitiveType>findFirst(umlPrimitiveTypes, _function_2);
                break;
              case STRING:
                final Function1<PrimitiveType, Boolean> _function_3 = (PrimitiveType it) -> {
                  String _name = it.getName();
                  return Boolean.valueOf(Objects.equal(_name, "String"));
                };
                _switchResult = IterableExtensions.<PrimitiveType>findFirst(umlPrimitiveTypes, _function_3);
                break;
              case CHAR:
              case BYTE:
              default:
                _switchResult = null;
                break;
            }
          } else {
            _switchResult = null;
          }
          final PrimitiveType umlType = _switchResult;
          if ((umlType != null)) {
            ReactionsCorrespondenceHelper.addCorrespondence(this.correspondenceModel, pcmType, umlType, TagLiterals.DATATYPE__TYPE);
          }
        }
      }
    }
    
    public String getRetrieveTag1(final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlDatatypesPkg(final Repository pcmRepo) {
      return pcmRepo;
    }
  }
  
  public BootstrapPrimitiveDatatypesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryReactions.BootstrapPrimitiveDatatypesRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRepo = pcmRepo;
  }
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine BootstrapPrimitiveDatatypesRoutine with input:");
    getLogger().debug("   pcmRepo: " + this.pcmRepo);
    
    org.eclipse.uml2.uml.Package umlDatatypesPkg = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlDatatypesPkg(pcmRepo), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRepo), 
    	true // asserted
    	);
    if (umlDatatypesPkg == null) {
    	return false;
    }
    registerObjectUnderModification(umlDatatypesPkg);
    userExecution.executeAction1(pcmRepo, umlDatatypesPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
