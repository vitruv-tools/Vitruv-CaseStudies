package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

/**
 * Extension event for a method body change event.
 * 
 * @author Stephan Seifermann
 *
 */
public class MethodBodyChangedEvent extends ChangeClassifyingEvent {
	public final MethodDeclaration originalMethodDeclaration;
	public final MethodDeclaration changedMethodDeclaration;
	
    /**
     * Constructor.
     * 
     * @param originalMethodDeclaration
     *            The method declaration, which contains the unchanged body.
     * @param changedMethodDeclaration
     *            The method declaration, which contains the changed body.
     */
    public MethodBodyChangedEvent(MethodDeclaration originalMethodDeclaration,
            MethodDeclaration changedMethodDeclaration) {
        this.originalMethodDeclaration = originalMethodDeclaration;
        this.changedMethodDeclaration = changedMethodDeclaration;
    }

    @Override
	public String toString() {
		return "MethodBodyChangedEvent [name=" + this.originalMethodDeclaration.getName().getIdentifier() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
