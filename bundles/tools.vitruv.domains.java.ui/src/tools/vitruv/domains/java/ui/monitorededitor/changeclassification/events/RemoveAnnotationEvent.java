package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RemoveAnnotationEvent extends AnnotationEvent {

	public final BodyDeclaration bodyAfterChange;

	public RemoveAnnotationEvent(final Annotation annotation, final BodyDeclaration bodyAfterChange) {
		super(annotation);
		this.bodyAfterChange = bodyAfterChange;
	}

	@Override
	public String toString() {
		return "RemoveMethodAnnotationEvent [annotation=" + this.annotation.getTypeName().getFullyQualifiedName() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
