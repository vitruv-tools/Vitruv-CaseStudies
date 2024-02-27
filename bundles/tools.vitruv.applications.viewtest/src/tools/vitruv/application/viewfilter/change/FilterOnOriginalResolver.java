package tools.vitruv.application.viewfilter.change;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.net4j.util.ImplementationError;

import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.CompositeContainerChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;

public class FilterOnOriginalResolver implements VitruviusChangeResolver<HierarchicalId> {
	
	
	public VitruviusChange<HierarchicalId> resolveFilteredChangesOnOriginalSources(VitruviusChange<HierarchicalId> change) {
		
//		if (change instanceof CompositeContainerChange compositeChange) {
//			//TODO nbr implement
//			throw new ImplementationError("Not implemented");
//		} else if (change instanceof TransactionalChange transactionalChange) {
//			transactionalChange.
//		}
		throw new IllegalStateException(
				"trying to transform unknown change of class " + change.getClass().getSimpleName());
	}

	@Override
	public VitruviusChange<EObject> resolveAndApply(VitruviusChange<HierarchicalId> change) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VitruviusChange<HierarchicalId> assignIds(VitruviusChange<EObject> change) {
		// TODO Auto-generated method stub
		throw new ImplementationError("Not implemented yet");

	}

}
