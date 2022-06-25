package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Javadoc;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class ChangeJavaDocEvent extends JavaDocEvent {

	Javadoc original;

	public ChangeJavaDocEvent(Javadoc original, Javadoc changed) {
		super(changed);
		this.original = original;
	}

	@Override
	public String toString() {
		return "ChangeJavaDocEvent [original=" + this.original.toString() + ", changed=" + this.comment.toString()
				+ "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
