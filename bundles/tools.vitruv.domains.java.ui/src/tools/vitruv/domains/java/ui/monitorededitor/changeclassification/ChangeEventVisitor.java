package tools.vitruv.domains.java.ui.monitorededitor.changeclassification;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddAnnotationEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddFieldEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddImportEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddJavaDocEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddMethodEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddSuperClassEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddSuperInterfaceEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeClassModifiersEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeClassifyingEventExtension;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeFieldModifiersEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeFieldTypeEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeInterfaceModifiersEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeJavaDocEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeMethodModifiersEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeMethodParameterEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeMethodReturnTypeEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangePackageDeclarationEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.CreateClassEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.CreateInterfaceEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.CreatePackageEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.DeleteClassEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.DeleteInterfaceEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.DeletePackageEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.MethodBodyChangedEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.MoveMethodEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveAnnotationEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveFieldEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveImportEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveJavaDocEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveMethodEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveSuperClassEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveSuperInterfaceEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameClassEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameFieldEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameInterfaceEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameMethodEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameMethodParameterEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenamePackageDeclarationEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenamePackageEvent;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameParameterEvent;

/**
 * Visitor for {@link ChangeClassifyingEvent}s. Contains one visit method for
 * {@link ChangeClassifyingEventExtension} which should be implemented as a
 * dispatch method that delegates the change to a for its type registered
 * ChangeEventExtendedVisitor.
 */
public interface ChangeEventVisitor<T> {

	T visit(CreateInterfaceEvent addInterfaceEvent);

	T visit(CreateClassEvent addClassEvent);

	T visit(ChangeMethodReturnTypeEvent changeMethodReturnTypeEvent);

	T visit(ChangeMethodParameterEvent changeMethodParameterEvent);

	T visit(RemoveMethodEvent removeMethodEvent);

	T visit(DeleteClassEvent removeClassEvent);

	T visit(DeleteInterfaceEvent removeInterfaceEvent);

	T visit(RenameMethodEvent renameMethodEvent);

	T visit(RenameInterfaceEvent renameInterfaceEvent);

	T visit(RenameClassEvent renameClassEvent);

	T visit(AddImportEvent addImportEvent);

	T visit(RemoveImportEvent removeImportEvent);

	T visit(MoveMethodEvent moveMethodEvent);

	T visit(AddSuperInterfaceEvent addSuperInterfaceEvent);

	T visit(RemoveSuperInterfaceEvent removeSuperInterfaceEvent);

	T visit(ChangeMethodModifiersEvent changeMethodModifierEvent);

	T visit(ChangeClassModifiersEvent changeClassModifiersEvent);

	T visit(ChangeInterfaceModifiersEvent changeInterfaceModifiersEvent);

	T visit(AddFieldEvent addFieldEvent);

	T visit(RemoveFieldEvent removeFieldEvent);

	T visit(ChangeFieldModifiersEvent changeFieldModifiersEvent);

	T visit(ChangeFieldTypeEvent changeFieldTypeEvent);

	T visit(RenameFieldEvent renameFieldEvent);

	T visit(AddAnnotationEvent addAnnotationEvent);

	T visit(RemoveAnnotationEvent removeAnnotationEvent);

	T visit(RenamePackageEvent renamePackageEvent);

	T visit(DeletePackageEvent removePackageEvent);

	T visit(CreatePackageEvent addPackageEvent);

	T visit(RenamePackageDeclarationEvent renamePackageDeclarationEvent);

	T visit(ChangePackageDeclarationEvent changePackageDeclarationEvent);

	T visit(AddJavaDocEvent addJavaDocEvent);

	T visit(RemoveJavaDocEvent removeJavaDocEvent);

	T visit(ChangeJavaDocEvent changeJavaDocEvent);

	T visit(AddSuperClassEvent addSuperClassEvent);

	T visit(RemoveSuperClassEvent removeSuperClassEvent);

	T visit(AddMethodEvent addMethodEvent);

	T visit(RenameParameterEvent renameParameterEvent);
	
	T visit(MethodBodyChangedEvent methodBodyChangedEvent);
	
	T visit(RenameMethodParameterEvent renameMethodParameterEvent);

}
