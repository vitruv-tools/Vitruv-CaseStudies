package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RemoveImportEvent extends ChangeClassifyingEvent {

	public final CompilationUnit compilationUnitAfterRemove;
	public final ImportDeclaration importDeclaration;

	public RemoveImportEvent(CompilationUnit compilationUnitAfterRemove, ImportDeclaration importDeclaration) {
		this.compilationUnitAfterRemove = compilationUnitAfterRemove;
		this.importDeclaration = importDeclaration;
	}

	@Override
	public String toString() {
		return "RemoveImportEvent [import " + this.importDeclaration.getName().getFullyQualifiedName() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
