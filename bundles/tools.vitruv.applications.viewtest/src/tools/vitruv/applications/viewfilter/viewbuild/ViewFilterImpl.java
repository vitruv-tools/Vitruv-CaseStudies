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
import tools.vitruv.framework.views.ViewSelection;

public class ViewFilterImpl implements ViewFilter {
	
	private Set<EObject> rootListForView;
		
	private final ViewFilterBuilder builder;
	
	private Map<EObject, EObject> mapCopy2Original;
	
	private ViewFilterImpl(ViewFilterBuilder viewFilterBuilder) {
		builder = viewFilterBuilder;
	}
	

	public Set<EObject> filterElements(Collection<EObject> roots) {
		mapCopy2Original = new HashMap<EObject, EObject>();
		//Collection<EObject> rootsCopy = EcoreUtil.copyAll(roots);
		rootListForView = new HashSet<EObject>();
		
		copyElementsOfRootTypeToSelectionByLambda(roots);
		removeOwnedAttributesFromUmlClasses();
		return rootListForView;
	}
	
	
	private void copyElementsOfRootTypeToSelectionByLambda(Collection<EObject> roots) {
		if (!builder.filterByLambdaActive) {
			return;
		}
		for (EObject root : roots) {
			filterAllContents(root, builder.filter, rootListForView);
		}
	}
	
	
	private void removeOwnedAttributesFromUmlClasses() {
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
	
	
	private void filterAllContents(EObject root, Function<EObject, Boolean> filter, Set<EObject> rootListForView) {
		EObject filteredModelRootStub = null;
		Iterator<EObject> contentIterator = root.eAllContents();
		while(contentIterator.hasNext()) {
			EObject contentElement = contentIterator.next();
			if (filter.apply(contentElement)) {
				filteredModelRootStub = createAndRegisterModelRootIfNotExistent(filteredModelRootStub, root);
				EObject copyOfContentElement = copyEObject(contentElement);
				attachElementToRoot(filteredModelRootStub, copyOfContentElement);
			}
		}
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
	
	private EObject copyEObject(EObject object) {
		EObject copyOfContentElement = EcoreUtil.copy(object);
		getMapCopy2Original().put(copyOfContentElement, object);
		return copyOfContentElement;
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
	
	private EObject createAndRegisterModelRootIfNotExistent(EObject filteredRoot, EObject root) {
		if (filteredRoot == null) {
			EObject modelRoot = createFilteredModelRootIfNotExistent(filteredRoot, root);
			//Map root stub to original root
			mapCopy2Original.put(modelRoot, root);
			rootListForView.add(modelRoot);
			return modelRoot;
		} else {
			return filteredRoot;
		}
		
	}
	
	
	private EObject createFilteredModelRootIfNotExistent(EObject filteredRoot, EObject root) {
		if (root instanceof Model) {
			Model modelCopy = UMLFactory.eINSTANCE.createModel();
			modelCopy.setName(((Model) root).getName());
			return modelCopy;	
		}  else if (root instanceof Repository) {
			Repository repositoryCopy = RepositoryFactory.eINSTANCE.createRepository();
			repositoryCopy.setEntityName(((Repository) root).getEntityName());
			return repositoryCopy;
		}
		else {
			throw new ImplementationError("nbruening: Not implemented yet");
		}
	}
	

	public Map<EObject, EObject> getMapCopy2Original() {
		return mapCopy2Original;
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
		
		public ViewFilterBuilder removeOwnedUmlAttributes() {
			this.removeAttributesActive = true;
			return this;
		}
		
		public ViewFilterImpl build() {
			return new ViewFilterImpl(this);
		}
		
		
	}

}
