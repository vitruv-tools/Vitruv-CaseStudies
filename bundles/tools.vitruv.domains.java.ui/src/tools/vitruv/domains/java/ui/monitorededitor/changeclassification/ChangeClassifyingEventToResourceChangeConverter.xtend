package tools.vitruv.domains.java.ui.monitorededitor.changeclassification

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeClassifyingEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.CreateInterfaceEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.CreateClassEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeMethodReturnTypeEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeMethodParameterEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveMethodEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.DeleteClassEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.DeleteInterfaceEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameMethodEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameInterfaceEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameClassEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddImportEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveImportEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.MoveMethodEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddSuperInterfaceEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveSuperInterfaceEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeMethodModifiersEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeClassModifiersEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeInterfaceModifiersEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddFieldEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveFieldEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeFieldModifiersEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeFieldTypeEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameFieldEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddAnnotationEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveAnnotationEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenamePackageEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.DeletePackageEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.CreatePackageEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenamePackageDeclarationEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangePackageDeclarationEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddJavaDocEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveJavaDocEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeJavaDocEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddSuperClassEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RemoveSuperClassEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.AddMethodEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameParameterEvent
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.core.resources.IResource
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ResourceChange
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.MethodBodyChangedEvent
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.RenameMethodParameterEvent
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.JavaModelException
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createPlatformResourceURI

class ChangeClassifyingEventToResourceChangeConverter {

	def ResourceChange convert(ChangeClassifyingEvent event) {
		event.accept(new ResourceChangeClassification())
	}

	private static class ResourceChangeClassification implements ChangeEventVisitor<ResourceChange> {

		private static def getURI(ASTNode nodeWithIResource) {
			if (null === nodeWithIResource) {
            	return null;
        	}
        	val astCompilationUnit = nodeWithIResource.getRoot()
        	if (!(astCompilationUnit instanceof org.eclipse.jdt.core.dom.CompilationUnit)) {
	            throw new IllegalStateException(nodeWithIResource.getRoot() + " is not a CompilationUnit")
        	}
        	val iCU = (astCompilationUnit as org.eclipse.jdt.core.dom.CompilationUnit)
                .getJavaElement() as ICompilationUnit
        	if (iCU === null) {
            	return null;
        	}
        	val resource = try {
            	iCU.getCorrespondingResource();
        	} catch (JavaModelException e) {
	            throw new IllegalStateException("resource for " + iCU + " could not be found")
        	}
        	resource.URI
		}

		private static def getURI(IResource resource) {
			resource.createPlatformResourceURI()
		}

		override visit(CreateInterfaceEvent addInterfaceEvent) {
			val typeURI = addInterfaceEvent.type.URI
			return new ResourceChange(null, typeURI)
		}

		override visit(CreateClassEvent addClassEvent) {
			val typeURI = addClassEvent.type.URI
			return new ResourceChange(null, typeURI)
		}

		override visit(ChangeMethodReturnTypeEvent changeMethodReturnTypeEvent) {
			val typeURI = changeMethodReturnTypeEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangeMethodParameterEvent changeMethodParameterEvent) {
			val typeURI = changeMethodParameterEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RemoveMethodEvent removeMethodEvent) {
			val typeURI = removeMethodEvent.method.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(DeleteClassEvent removeClassEvent) {
			val typeURI = removeClassEvent.type.URI
			return new ResourceChange(typeURI, null)
		}

		override visit(DeleteInterfaceEvent removeInterfaceEvent) {
			val typeURI = removeInterfaceEvent.type.URI
			return new ResourceChange(typeURI, null)
		}

		override visit(RenameMethodEvent renameMethodEvent) {
			val typeURI = renameMethodEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RenameInterfaceEvent renameInterfaceEvent) {
			val originalURI = renameInterfaceEvent.original.URI
			val renamedURI = renameInterfaceEvent.renamed.URI
			return new ResourceChange(originalURI, renamedURI)
		}

		override visit(RenameClassEvent renameClassEvent) {
			val originalURI = renameClassEvent.original.URI
			val renamedURI = renameClassEvent.renamed.URI
			return new ResourceChange(originalURI, renamedURI)
		}

		override visit(AddImportEvent addImportEvent) {
			val typeURI = addImportEvent.importDeclaration.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RemoveImportEvent removeImportEvent) {
			val typeURI = removeImportEvent.importDeclaration.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(MoveMethodEvent moveMethodEvent) {
			val originalURI = moveMethodEvent.original.URI
			val renamedURI = moveMethodEvent.moved.URI
			return new ResourceChange(originalURI, renamedURI)
		}

		override visit(AddSuperInterfaceEvent addSuperInterfaceEvent) {
			val typeURI = addSuperInterfaceEvent.baseType.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RemoveSuperInterfaceEvent removeSuperInterfaceEvent) {
			val typeURI = removeSuperInterfaceEvent.baseType.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangeMethodModifiersEvent changeMethodModifierEvent) {
			val typeURI = changeMethodModifierEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangeClassModifiersEvent changeClassModifiersEvent) {
			val typeURI = changeClassModifiersEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangeInterfaceModifiersEvent changeInterfaceModifiersEvent) {
			val typeURI = changeInterfaceModifiersEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(AddFieldEvent addFieldEvent) {
			val typeURI = addFieldEvent.field.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RemoveFieldEvent removeFieldEvent) {
			val typeURI = removeFieldEvent.field.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangeFieldModifiersEvent changeFieldModifiersEvent) {
			val typeURI = changeFieldModifiersEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangeFieldTypeEvent changeFieldTypeEvent) {
			val typeURI = changeFieldTypeEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RenameFieldEvent renameFieldEvent) {
			val typeURI = renameFieldEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(AddAnnotationEvent addAnnotationEvent) {
			val typeURI = addAnnotationEvent.annotation.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RemoveAnnotationEvent removeAnnotationEvent) {
			val typeURI = removeAnnotationEvent.annotation.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RenamePackageEvent renamePackageEvent) {
			val originalURI = renamePackageEvent.originalIResource.URI
			val renamedURI = renamePackageEvent.renamedIResource.URI
			return new ResourceChange(originalURI, renamedURI)
		}

		override visit(DeletePackageEvent removePackageEvent) {
			val typeURI = removePackageEvent.iResource.URI
			return new ResourceChange(typeURI, null)
		}

		override visit(CreatePackageEvent addPackageEvent) {
			val typeURI = addPackageEvent.iResource.URI
			return new ResourceChange(null, typeURI)
		}

		override visit(RenamePackageDeclarationEvent renamePackageDeclarationEvent) {
			val typeURI = renamePackageDeclarationEvent.packageDeclaration.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangePackageDeclarationEvent changePackageDeclarationEvent) {
			val typeURI = changePackageDeclarationEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(AddJavaDocEvent addJavaDocEvent) {
			val typeURI = addJavaDocEvent.comment.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RemoveJavaDocEvent removeJavaDocEvent) {
			val typeURI = removeJavaDocEvent.comment.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(ChangeJavaDocEvent changeJavaDocEvent) {
			val typeURI = changeJavaDocEvent.comment.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(AddSuperClassEvent addSuperClassEvent) {
			val typeURI = addSuperClassEvent.baseType.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RemoveSuperClassEvent removeSuperClassEvent) {
			val typeURI = removeSuperClassEvent.baseType.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(AddMethodEvent addMethodEvent) {
			val typeURI = addMethodEvent.method.URI
			return new ResourceChange(typeURI, typeURI)
		}

		override visit(RenameParameterEvent renameParameterEvent) {
			val typeURI = renameParameterEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}
		
		override visit(MethodBodyChangedEvent methodBodyChangedEvent) {
			val typeURI = methodBodyChangedEvent.originalMethodDeclaration.URI
			return new ResourceChange(typeURI, typeURI)
		}
		
		override visit(RenameMethodParameterEvent methodParameterNameChangedEvent) {
			val typeURI = methodParameterNameChangedEvent.original.URI
			return new ResourceChange(typeURI, typeURI)
		}

	}

}
