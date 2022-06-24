package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class CreateClassEvent extends CreateTypeEvent {

	public CreateClassEvent(CompilationUnit compilationUnitBeforeCreate, TypeDeclaration type) {
		super(compilationUnitBeforeCreate, type);
	}

	@Override
	public String toString() {
		return "CreateClassEvent [class=" + this.type.getName().getIdentifier() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
