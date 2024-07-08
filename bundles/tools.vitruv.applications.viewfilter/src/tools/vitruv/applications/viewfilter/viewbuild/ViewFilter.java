package tools.vitruv.applications.viewfilter.viewbuild;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * A ViewFilter filters the model objects contained in a {@link View}. What kind of 
 * elements will be filtered by an instance, depends on the implementation of the interface.
 * When {@link filterElements} is executed, the given models are filtered and the results 
 * are returned.
 * It also depends on the implementation, how the Filter deals with objects, if their parent
 * has been filtered out. One possibility is, that the filter creates a stub-model-root 
 * and mounts all filtered elements under the stub-model-root.
 */
public interface ViewFilter {
	
	/**
	 * This method executes the actual filter mechanism on the models which belong
	 * to the given model roots. It depends on the implementation, how the method deals 
	 * with objects, if their parent
	 * has been filtered out. One possibility is, that the filter creates a stub-model-root 
	 * and mounts all filtered elements under the stub-model-root.
	 * @param roots The roots of the models that are supposed to be filtered
	 * @return The remaining model objects after all other objects have been filtered out according 
	 * to the used filter.
	 */
	Set<EObject> filterElements(Collection<EObject> roots);
	
	
	/**
	 * Returns a {@link Map} which can be used to map the copied {@link EObject} in a Filter-Model
	 * to its original {@link EObject}. The map can only be used to map {@link EObject}s which have 
	 * been created by the same {@link ViewFilter} instance on which this method is then called.
	 * 
	 * @return A map which maps the copied {@link EObject} on its original {@link EObject} in the unfiltered model
	 */
	public Map<EObject, EObject> getMapCopy2Original();
	
	
	
}
