package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class AddSuperClassEvent extends ChangedSuperTypesEvent {

	public AddSuperClassEvent(TypeDeclaration baseType, Type superClass) {
		super(baseType, superClass);
	}

	@Override
	public String toString() {
		return "AddSuperClassEvent [baseType: " + this.baseType.getName() + ", superClass: " + this.superType + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
