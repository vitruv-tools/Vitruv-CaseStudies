package tools.vitruv.applications.viewfilter.viewbuild;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public interface ViewFilter {
	
	Set<EObject> filterElements(Collection<EObject> roots);
	
	Map<EObject, EObject> getMapOriginalRoot2RootStub();
}
