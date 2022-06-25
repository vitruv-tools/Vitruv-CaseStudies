package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class AddSuperInterfaceEvent extends ChangedSuperTypesEvent {

	public AddSuperInterfaceEvent(TypeDeclaration baseType, Type superInterface) {
		super(baseType, superInterface);
	}

	@Override
	public String toString() {
		return "AddSuperInterfaceEvent [baseType: " + this.baseType.getName() + ", superInterface: " + this.superType
				+ "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
