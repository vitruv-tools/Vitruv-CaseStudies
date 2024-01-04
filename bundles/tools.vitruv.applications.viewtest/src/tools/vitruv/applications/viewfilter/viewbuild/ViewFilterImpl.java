package tools.vitruv.applications.viewfilter.viewbuild;

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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

public class ViewFilterImpl implements ViewFilter {
	
	private Set<EObject> rootListForView;
	
	//TODO nbruening: Javadoc
	private Map<EObject, EObject> mapOriginalRoot2RootStub;
	
	private final ViewFilterBuilder builder;
	
	private ViewFilterImpl(ViewFilterBuilder viewFilterBuilder) {
		builder = viewFilterBuilder;
		rootListForView = new HashSet<EObject>();
		mapOriginalRoot2RootStub = new HashMap();
	}
	
	
	public Set<EObject> filterElements(Collection<EObject> roots) {
		addElementsToSelectionByLambda(roots);
		removeOwnedAttributesFromClasses();
		return rootListForView;
	}
	
	
	private void addElementsToSelectionByLambda(Collection<EObject> roots) {
		if (!builder.filterByLambdaActive) {
			return;
		}
		for (EObject root : roots) {
			filterAllContents(root, builder.filter, rootListForView);
		}
	}
	
	
	private void removeOwnedAttributesFromClasses() {
		if (!builder.removeAttributesActive) {
			return;
		}
		for (EObject root : rootListForView) {
			TreeIterator<EObject> content = root.eAllContents();
			List<EObject> contentList = convertTreeIterator2List(content);
			for (EObject object : contentList) {
				if (object instanceof org.eclipse.uml2.uml.Class) {
					org.eclipse.uml2.uml.Class classifierObject = (org.eclipse.uml2.uml.Class) object;
					classifierObject.getOwnedAttributes().removeAll(classifierObject.getOwnedAttributes());
				}
			}	
		}
	}
	
	
	private void filterAllContents(EObject root, Function<EObject, Boolean> filter, Set<EObject> rootListForView) {
		Model filteredModelRootStub = null;
		Iterator<EObject> contentIterator = root.eAllContents();
		while(contentIterator.hasNext()) {
			EObject contentElement = contentIterator.next();
			if (filter.apply(contentElement)) {
				//testListObjectsToDisplay.add(contentElement);
				
				filteredModelRootStub = createFilteredModelRootIfNotExistent(filteredModelRootStub, root);
				EObject copyOfContentElement = EcoreUtil.copy(contentElement);
				attachElementToRoot(filteredModelRootStub, copyOfContentElement);
				
				rootListForView.add(filteredModelRootStub);
				getMapOriginalRoot2RootStub().put(root, filteredModelRootStub);
			}
		}
	}
	
	
	private List<EObject> convertTreeIterator2List(TreeIterator<EObject> content) {
		List<EObject> list = new LinkedList<EObject>();
		while(content.hasNext()) {
			list.add(content.next());
		}
		return list;
	}
	
	
	private void attachElementToRoot(Model root, EObject object) {
		if (object instanceof Type) {
			EObject objectCopy = EcoreUtil.copy(object);
			root.getOwnedTypes().add((Type) objectCopy);
		} else {
			System.out.println("Warning: Undefined type: " + object.eClass());
		}
	}
	
	
	private Model createFilteredModelRootIfNotExistent(Model filteredRoot, EObject root) {
		if (filteredRoot == null) {
			if (root instanceof Model) {
				return UMLFactory.eINSTANCE.createModel();	
			} else {
				throw new ImplementationError("nbruening: Not implemented yet");
			}
		}
		return filteredRoot;
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
