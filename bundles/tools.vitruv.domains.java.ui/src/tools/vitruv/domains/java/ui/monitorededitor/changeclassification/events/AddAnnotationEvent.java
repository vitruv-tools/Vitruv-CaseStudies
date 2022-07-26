package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class AddAnnotationEvent extends AnnotationEvent {

    public final BodyDeclaration bodyDeclaration;

    public AddAnnotationEvent(final BodyDeclaration bodyDeclaration, final Annotation annotation) {
        super(annotation);
        this.bodyDeclaration = bodyDeclaration;
    }

    @Override
    public String toString() {
        return "AddMethodAnnotationEvent [annotation=" + this.annotation.getTypeName().getFullyQualifiedName() + "]";
    }

    @Override
    public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
    }

}
