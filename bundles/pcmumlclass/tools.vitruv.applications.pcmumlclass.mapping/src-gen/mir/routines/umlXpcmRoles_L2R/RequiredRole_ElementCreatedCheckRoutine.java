package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RequiredRole_ElementCreatedCheckRoutine extends AbstractRepairRoutineRealization {
  private RequiredRole_ElementCreatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof Property)) {
        Property property_ = ((Property)affectedEObject);
        {
          EObject implementation__candidate = property_.eContainer();
          if (((implementation__candidate != null) && (implementation__candidate instanceof org.eclipse.uml2.uml.Class))) {
            org.eclipse.uml2.uml.Class implementation_ = ((org.eclipse.uml2.uml.Class) implementation__candidate);
            {
              Type interface__candidate = property_.getType();
              if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
                Interface interface_ = ((Interface) interface__candidate);
                EList<EObject> _eCrossReferences = interface_.eCrossReferences();
                for (final EObject parameter__candidate : _eCrossReferences) {
                  if (((parameter__candidate != null) && (parameter__candidate instanceof Parameter))) {
                    Parameter parameter_ = ((Parameter) parameter__candidate);
                    Type _type = parameter_.getType();
                    boolean _tripleEquals = (interface_ == _type);
                    if (_tripleEquals) {
                      EObject operation__candidate = parameter_.eContainer();
                      if (((operation__candidate != null) && (operation__candidate instanceof Operation))) {
                        Operation operation_ = ((Operation) operation__candidate);
                        {
                          _routinesFacade.requiredRole_CreateMapping(property_, parameter_, implementation_, interface_, operation_);
                          return;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof Parameter)) {
        Parameter parameter_ = ((Parameter)affectedEObject);
        {
          EObject operation__candidate = parameter_.eContainer();
          if (((operation__candidate != null) && (operation__candidate instanceof Operation))) {
            Operation operation_ = ((Operation) operation__candidate);
            {
              Type interface__candidate = parameter_.getType();
              if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
                Interface interface_ = ((Interface) interface__candidate);
                EList<EObject> _eCrossReferences = interface_.eCrossReferences();
                for (final EObject property__candidate : _eCrossReferences) {
                  if (((property__candidate != null) && (property__candidate instanceof Property))) {
                    Property property__1 = ((Property) property__candidate);
                    Type _type = property__1.getType();
                    boolean _tripleEquals = (interface_ == _type);
                    if (_tripleEquals) {
                      EObject implementation__candidate = property__1.eContainer();
                      if (((implementation__candidate != null) && (implementation__candidate instanceof org.eclipse.uml2.uml.Class))) {
                        org.eclipse.uml2.uml.Class implementation_ = ((org.eclipse.uml2.uml.Class) implementation__candidate);
                        {
                          _routinesFacade.requiredRole_CreateMapping(property__1, parameter_, implementation_, interface_, operation_);
                          return;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Class)) {
        org.eclipse.uml2.uml.Class implementation_ = ((org.eclipse.uml2.uml.Class)affectedEObject);
        EList<Property> _ownedAttributes = implementation_.getOwnedAttributes();
        for (final Property property__candidate : _ownedAttributes) {
          if (((property__candidate != null) && (property__candidate instanceof Property))) {
            Property property__1 = ((Property) property__candidate);
            {
              Type interface__candidate = property__1.getType();
              if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
                Interface interface_ = ((Interface) interface__candidate);
                EList<EObject> _eCrossReferences = interface_.eCrossReferences();
                for (final EObject parameter__candidate : _eCrossReferences) {
                  if (((parameter__candidate != null) && (parameter__candidate instanceof Parameter))) {
                    Parameter parameter__1 = ((Parameter) parameter__candidate);
                    Type _type = parameter__1.getType();
                    boolean _tripleEquals = (interface_ == _type);
                    if (_tripleEquals) {
                      EObject operation__candidate = parameter__1.eContainer();
                      if (((operation__candidate != null) && (operation__candidate instanceof Operation))) {
                        Operation operation_ = ((Operation) operation__candidate);
                        {
                          _routinesFacade.requiredRole_CreateMapping(property__1, parameter__1, implementation_, interface_, operation_);
                          return;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof Interface)) {
        Interface interface_ = ((Interface)affectedEObject);
        EList<EObject> _eCrossReferences = interface_.eCrossReferences();
        for (final EObject parameter__candidate : _eCrossReferences) {
          if (((parameter__candidate != null) && (parameter__candidate instanceof Parameter))) {
            Parameter parameter__1 = ((Parameter) parameter__candidate);
            Type _type = parameter__1.getType();
            boolean _tripleEquals = (interface_ == _type);
            if (_tripleEquals) {
              EList<EObject> _eCrossReferences_1 = interface_.eCrossReferences();
              for (final EObject property__candidate_1 : _eCrossReferences_1) {
                if (((property__candidate_1 != null) && (property__candidate_1 instanceof Property))) {
                  Property property__2 = ((Property) property__candidate_1);
                  Type _type_1 = property__2.getType();
                  boolean _tripleEquals_1 = (interface_ == _type_1);
                  if (_tripleEquals_1) {
                    EObject implementation__candidate = property__2.eContainer();
                    if (((implementation__candidate != null) && (implementation__candidate instanceof org.eclipse.uml2.uml.Class))) {
                      org.eclipse.uml2.uml.Class implementation__1 = ((org.eclipse.uml2.uml.Class) implementation__candidate);
                      {
                        EObject operation__candidate = parameter__1.eContainer();
                        if (((operation__candidate != null) && (operation__candidate instanceof Operation))) {
                          Operation operation_ = ((Operation) operation__candidate);
                          {
                            _routinesFacade.requiredRole_CreateMapping(property__2, parameter__1, implementation__1, interface_, operation_);
                            return;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof Operation)) {
        Operation operation_ = ((Operation)affectedEObject);
        EList<Parameter> _ownedParameters = operation_.getOwnedParameters();
        for (final Parameter parameter__candidate_1 : _ownedParameters) {
          if (((parameter__candidate_1 != null) && (parameter__candidate_1 instanceof Parameter))) {
            Parameter parameter__2 = ((Parameter) parameter__candidate_1);
            {
              Type interface__candidate = parameter__2.getType();
              if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
                Interface interface__1 = ((Interface) interface__candidate);
                EList<EObject> _eCrossReferences_2 = interface__1.eCrossReferences();
                for (final EObject property__candidate_2 : _eCrossReferences_2) {
                  if (((property__candidate_2 != null) && (property__candidate_2 instanceof Property))) {
                    Property property__3 = ((Property) property__candidate_2);
                    Type _type_2 = property__3.getType();
                    boolean _tripleEquals_2 = (interface__1 == _type_2);
                    if (_tripleEquals_2) {
                      EObject implementation__candidate_1 = property__3.eContainer();
                      if (((implementation__candidate_1 != null) && (implementation__candidate_1 instanceof org.eclipse.uml2.uml.Class))) {
                        org.eclipse.uml2.uml.Class implementation__2 = ((org.eclipse.uml2.uml.Class) implementation__candidate_1);
                        {
                          _routinesFacade.requiredRole_CreateMapping(property__3, parameter__2, implementation__2, interface__1, operation_);
                          return;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public RequiredRole_ElementCreatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.RequiredRole_ElementCreatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_ElementCreatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
