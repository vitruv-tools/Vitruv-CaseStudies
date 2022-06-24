package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class MoveMethodEvent extends ChangeClassifyingEvent {

	public TypeDeclaration typeMovedFromAfterRemove, typeMovedToBeforeAdd;
	public MethodDeclaration original, moved;

	public MoveMethodEvent(TypeDeclaration typeMovedFromAfterRemove, TypeDeclaration typeMovedToBeforeAdd,
			MethodDeclaration original, MethodDeclaration moved) {
		this.typeMovedFromAfterRemove = typeMovedFromAfterRemove;
		this.typeMovedToBeforeAdd = typeMovedToBeforeAdd;
		this.original = original;
		this.moved = moved;
	}

	@Override
	public String toString() {
		return "MoveMethodEvent [original=" + this.original.getName().getIdentifier() + ", moved="
				+ this.moved.getName().getIdentifier() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
