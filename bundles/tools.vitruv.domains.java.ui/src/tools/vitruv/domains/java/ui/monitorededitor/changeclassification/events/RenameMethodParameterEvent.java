package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RenameMethodParameterEvent extends ChangeMethodSignatureEvent {

	private final SingleVariableDeclaration paramOriginal;
	private final SingleVariableDeclaration paramChanged;

	/**
	 * Constructor.
	 * 
	 * @param methodOriginal
	 *            The original method.
	 * @param methodChanged
	 *            The changed method.
	 * @param line
	 * 			  The changed line.
	 * @param paramOriginal
	 *            The original parameter.
	 * @param paramChanged
	 *            The changed parameter.
	 */
	public RenameMethodParameterEvent(MethodDeclaration methodOriginal,
			MethodDeclaration methodChanged,
			SingleVariableDeclaration paramOriginal,
			SingleVariableDeclaration paramChanged,
			int line) {
		super(methodOriginal, methodChanged, line);
		this.paramOriginal = paramOriginal;
		this.paramChanged = paramChanged;
	}

	@Override
	public String toString() {
		return "RenameMethodParameterEvent [original=" + this.original.getName().getIdentifier() + ", changed="
				+ this.renamed.getName().getIdentifier() + ", parameter=" + this.paramOriginal.getName().getIdentifier() 
				+ ", changed=" + this.paramChanged.getName().getIdentifier() + ", line=" + this.line + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
