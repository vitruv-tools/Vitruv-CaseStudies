package tools.vitruv.applications.viewfilter.util.framework.selectors;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

import com.google.common.base.Preconditions;

import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter.ViewFilterBuilder;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

public class FilterSupportingViewElementSelector<Id extends Object> implements ViewSelector {
	
	private ModifiableViewSelection viewSelection;
	
	private ChangeableViewSource viewSource;
	
	private final ViewCreatingViewType<FilterSupportingViewElementSelector<Id>, Id> viewType;
	
	private List<EObject> testListObjectsToDisplay = new LinkedList();

	private Set<EObject> rootListForView;
	
	//TODO nbruening: Javadoc
	private Map<EObject, EObject> mapOriginalRoot2RootStub;
	
	private ViewFilterBuilder viewFilterBuilder;


	public FilterSupportingViewElementSelector(ViewCreatingViewType<FilterSupportingViewElementSelector<Id>, Id> viewType, ChangeableViewSource viewSource, Collection<EObject> selectableElements) {
	    Preconditions.checkArgument((selectableElements != null), "selectable elements must not be null");
	    Preconditions.checkArgument((viewType != null), "view type must not be null");
	    Preconditions.checkArgument((viewSource != null), "view source must not be null");
		this.viewType = viewType;
		this.viewSource = viewSource;
		//Copy underlying model
		//Collection<EObject> selectableElementsCopy = EcoreUtil.copyAll(selectableElements);
	    this.viewSelection = new ElementViewSelection(selectableElements);
		rootListForView = new HashSet<EObject>();
		mapOriginalRoot2RootStub = new HashMap();
		viewFilterBuilder = new ViewFilterBuilder();
		
	}
	

	@Override
	public View createView() {
		viewSelection = new ElementViewSelection(rootListForView);
		rootListForView.forEach(element -> setSelected(element, true));
		//TODO nbruening ggf noch anpassen
	    Preconditions.checkState(this.isValid(), "the current selection is invalid, thus a view cannot be created");
	    return this.viewType.createView(this);
	}
	
	public void selectElementsOfRootType(Collection<Class<?>> rootTypes) {
		getSelectableElements().stream()
			.filter(element -> !(rootTypes.stream().anyMatch(it -> it.isInstance(element))))
			.forEach(element -> setSelected(element, false));
	}
	
	
	public void addElementsToSelectionByLambda(Function<EObject, Boolean> filter) {
		for (EObject root : getSelectableElements()) {
			filterAllContents(root, filter, rootListForView);
		}
	}
	
	
	public void removeOwnedAttributesFromClasses() {		
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
				mapOriginalRoot2RootStub.put(root, filteredModelRootStub);
			}
		}
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
	
	
	private List<EObject> convertTreeIterator2List(TreeIterator<EObject> content) {
		List<EObject> list = new LinkedList<EObject>();
		while(content.hasNext()) {
			list.add(content.next());
		}
		return list;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	  public ViewSelection getSelection() {
	    return new ElementViewSelection(this.viewSelection);
	  }

	  public Collection<EObject> getSelectableElements() {
	    return this.viewSelection.getSelectableElements();
	  }

	  public boolean isSelectable(final EObject eObject) {
	    return this.viewSelection.isSelectable(eObject);
	  }

	  public boolean isSelected(final EObject eObject) {
	    return this.viewSelection.isSelected(eObject);
	  }

	  public boolean isViewObjectSelected(final EObject eObject) {
	    return this.viewSelection.isViewObjectSelected(eObject);
	  }

	  public void setSelected(final EObject eObject, final boolean selected) {
	    this.viewSelection.setSelected(eObject, selected);
	  }
	  
	  public ChangeableViewSource getViewSource() {
		    return this.viewSource;
	  }
	  
	  public ViewCreatingViewType<FilterSupportingViewElementSelector<Id>, Id> getViewType() {
		 return this.viewType;
	  }
	  
	  public Map<EObject, EObject> getMapOriginalRoot2RootStub() {
		  return mapOriginalRoot2RootStub;
	  }

}