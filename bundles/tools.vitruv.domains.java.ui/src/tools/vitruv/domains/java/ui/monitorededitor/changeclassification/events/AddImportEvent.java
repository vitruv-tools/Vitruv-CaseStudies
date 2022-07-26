package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class AddImportEvent extends ChangeClassifyingEvent {

	public final CompilationUnit compilationUnitBeforeAdd;
	public final ImportDeclaration importDeclaration;

	public AddImportEvent(CompilationUnit compilationUnitBeforeImport, ImportDeclaration importDeclaration) {
		this.compilationUnitBeforeAdd = compilationUnitBeforeImport;
		this.importDeclaration = importDeclaration;
	}

	@Override
	public String toString() {
		return "AddImportEvent [import " + this.importDeclaration.getName().getFullyQualifiedName() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
