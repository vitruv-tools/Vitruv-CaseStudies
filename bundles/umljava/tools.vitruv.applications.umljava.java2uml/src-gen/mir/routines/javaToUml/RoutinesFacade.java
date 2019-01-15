package mir.routines.javaToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
    this.javaToUmlAttribute = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("javaToUmlAttribute")));
    this.javaToUmlClassifier = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("javaToUmlClassifier")));
    this.javaToUmlMethod = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("javaToUmlMethod")));
  }
  
  public final mir.routines.javaToUmlAttribute.RoutinesFacade javaToUmlAttribute;
  
  public final mir.routines.javaToUmlClassifier.RoutinesFacade javaToUmlClassifier;
  
  public final mir.routines.javaToUmlMethod.RoutinesFacade javaToUmlMethod;
}
