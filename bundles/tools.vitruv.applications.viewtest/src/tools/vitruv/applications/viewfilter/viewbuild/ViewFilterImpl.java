package tools.vitruv.applications.viewfilter.viewbuild;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.palladiosimulator.pcm.PcmFactory;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.viewfilter.helpers.ViewFilterHelper;

public class ViewFilterImpl implements ViewFilter {
	
	private Set<EObject> rootListForView;
	
	//TODO nbruening: Javadoc
	private Map<EObject, EObject> mapOriginalRoot2RootStub;
	
	private final ViewFilterBuilder builder;
	
	private ViewFilterImpl(ViewFilterBuilder viewFilterBuilder) {
		builder = viewFilterBuilder;
		mapOriginalRoot2RootStub = new HashMap();
	}
	
	
	
	public Set<EObject> filterElements(Collection<EObject> roots, Collection<Resource> viewSources) {
		Map<EObject, EObject> newMapOriginalRoot2RootStub = new HashMap();
		rootListForView = new HashSet<EObject>();
		List<EObject> rootsInViewSource = extractRootsInViewSource(viewSources, roots);
		addElementsToSelectionByLambda(rootsInViewSource, newMapOriginalRoot2RootStub);
		removeOwnedAttributesFromClasses();
		mapOriginalRoot2RootStub = newMapOriginalRoot2RootStub;
		return rootListForView;
	}
	
	
	
	
	private void addElementsToSelectionByLambda(List<EObject> roots, Map<EObject, EObject> newMapOriginalRoot2RootStub) {
		if (!builder.filterByLambdaActive) {
			return;
		}
		for (EObject root : roots) {
			filterAllContents(root, builder.filter, rootListForView, newMapOriginalRoot2RootStub);
		}
	}
	
	
	private void removeOwnedAttributesFromClasses() {
		if (!builder.removeAttributesActive) {
			return;
		}
		for (EObject root : rootListForView) {
			TreeIterator<EObject> content = root.eAllContents();
			List<EObject> contentList = ViewFilterHelper.convertTreeIterator2List(content);
			for (EObject object : contentList) {
				if (object instanceof org.eclipse.uml2.uml.Class) {
					org.eclipse.uml2.uml.Class classifierObject = (org.eclipse.uml2.uml.Class) object;
					classifierObject.getOwnedAttributes().removeAll(classifierObject.getOwnedAttributes());
				}
			}	
		}
	}
	
	
	private void filterAllContents(EObject root, Function<EObject, Boolean> filter, Set<EObject> rootListForView, Map<EObject, EObject> newMapOriginalRoot2RootStub) {
		EObject filteredModelRootStub = null;
		Iterator<EObject> contentIterator = root.eAllContents();
		while(contentIterator.hasNext()) {
			EObject contentElement = contentIterator.next();
			if (filter.apply(contentElement)) {
				
				filteredModelRootStub = createAndRegisterModelRootIfNotExistent(filteredModelRootStub, root, newMapOriginalRoot2RootStub);
				EObject copyOfContentElement = EcoreUtil.copy(contentElement);
				attachElementToRoot(filteredModelRootStub, copyOfContentElement);
			}
		}
	}
	
	private List<EObject> extractRootsInViewSource(Collection<Resource> viewSources, Collection<EObject> roots) {
		List<Resource> resourcesWithSelectedElements = viewSources.stream()
				.filter(resource -> resource.getContents().stream()
				.anyMatch(element -> mapOriginalRoot2RootStub.get(element) != null ? roots.contains(mapOriginalRoot2RootStub.get(element)) : false))
				.toList();
		List<EObject> extractedRoots = new ArrayList();
		resourcesWithSelectedElements.forEach(it -> extractedRoots.addAll(it.getContents()));
		return extractedRoots;
	}
	
	
	private void attachElementToRoot(EObject root, EObject object) {
		if (root instanceof Model) {
			attachElementToRootUml((Model) root, object);
		} else if (root instanceof Repository) {
			attachElementToRootPcm((Repository) root, object);
		} else {
			throw new ImplementationError("Not implemented yet! Undefined type: " + object.eClass());
		}
	}
	
	
	private void attachElementToRootUml(Model root, EObject object) {
		if (object instanceof Type) {
			EObject objectCopy = EcoreUtil.copy(object);
			root.getOwnedTypes().add((Type) objectCopy);
		} else {
			System.out.println("Warning: Undefined type: " + object.eClass());
		}
	} 
	
	
	private void attachElementToRootPcm(Repository root, EObject object) {
		if (object instanceof RepositoryComponent) {
			root.getComponents__Repository().add((RepositoryComponent) object);
		} else if (object instanceof DataType) {
			root.getDataTypes__Repository().add((DataType) object);
		} else {
			System.out.println("Warning: Undefined type: " + object.eClass());
		}
	}
	
	private EObject createAndRegisterModelRootIfNotExistent(EObject filteredRoot, EObject root, Map<EObject, EObject> newMapOriginalRoot2RootStub) {
		if (filteredRoot == null) {
			EObject modelRoot = createFilteredModelRootIfNotExistent(filteredRoot, root);
			rootListForView.add(modelRoot);
			newMapOriginalRoot2RootStub.put(root, modelRoot);
			return modelRoot;
		} else {
			return filteredRoot;
		}
		
	}
	
	
	private EObject findOriginalRoot(EObject root) {
		
		
		if ((!mapOriginalRoot2RootStub.isEmpty()) && mapOriginalRoot2RootStub.containsValue(root)) {
			Set<EObject> keySet = mapOriginalRoot2RootStub.keySet();
			for (EObject key : keySet) {
				if (mapOriginalRoot2RootStub.get(key).equals(root)) {
					return key;
				}
			}
			throw new IllegalStateException("Value should be in map, but could not be found");
		} 
		return root;
	}
	
	
	private EObject createFilteredModelRootIfNotExistent(EObject filteredRoot, EObject root) {
		if (root instanceof Model) {
			return UMLFactory.eINSTANCE.createModel();	
		}  else if (root instanceof Repository) {
			return RepositoryFactory.eINSTANCE.createRepository();
		}
		else {
			throw new ImplementationError("nbruening: Not implemented yet");
		}
	}
	
	
	

	public Map<EObject, EObject> getMapOriginalRoot2RootStub() {
		return mapOriginalRoot2RootStub;
	}


	//---------------------------------
	/**
	 * Builder for ViewFilterImpl
	 */
	public static class ViewFilterBuilder {
		
		//optional parameters
		private Boolean filterByLambdaActive = false;
		
		private Boolean removeAttributesActive = false;
		
		private Function<EObject, Boolean> filter;
		
		
		public ViewFilterBuilder() {	
		}
		
		public ViewFilterBuilder filterByLambda(Function<EObject, Boolean> filter) {
			this.filterByLambdaActive = true;
			this.filter = filter;
			return this;
		}
		
		public ViewFilterBuilder removeOwnedAttributes() {
			this.removeAttributesActive = true;
			return this;
		}
		
		public ViewFilterImpl build() {
			return new ViewFilterImpl(this);
		}
		
		
	}

}
