package tools.vitruv.applications.viewfilter.util.framework.selectors;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;

import com.google.common.base.Preconditions;

import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.UmlViewBuilder;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

public class FilterSupportingViewElementSelector<Id extends Object> implements ViewSelector {
	
	private ModifiableViewSelection viewSelection;
	
	private ChangeableViewSource viewSource;
	
	private final ViewCreatingViewType<FilterSupportingViewElementSelector<Id>, Id> viewType;


	public FilterSupportingViewElementSelector(ViewCreatingViewType<FilterSupportingViewElementSelector<Id>, Id> viewType, ChangeableViewSource viewSource, Collection<EObject> selectableElements) {
	    Preconditions.checkArgument((selectableElements != null), "selectable elements must not be null");
	    Preconditions.checkArgument((viewType != null), "view type must not be null");
	    Preconditions.checkArgument((viewSource != null), "view source must not be null");
		this.viewType = viewType;
		this.viewSource = viewSource;
		//Copy underlying model
		Collection<EObject> selectableElementsCopy = EcoreUtil.copyAll(selectableElements);
	    ElementViewSelection _elementViewSelection = new ElementViewSelection(selectableElementsCopy);
	    this.viewSelection = _elementViewSelection;
		selectAllElements();
	}
	

	@Override
	public View createView() {
		//TODO nbruening ggf noch anpassen
	    Preconditions.checkState(this.isValid(), "the current selection is invalid, thus a view cannot be created");
	    return this.viewType.createView(this);
	}
	
	public void selectElementsOfRootType(Collection<Class<?>> rootTypes) {
		getSelectableElements().stream()
			.filter(element -> !(rootTypes.stream().anyMatch(it -> it.isInstance(element))))
			.forEach(element -> setSelected(element, false));
	}
	
	public void filterForTypeClass() {
		getSelectableElements().stream()
			.filter(element -> !(element instanceof org.eclipse.uml2.uml.Class))
			.forEach(element -> setSelected(element, false));
		
		for(EObject element : getSelectableElements()) {
			if (element instanceof NamedElement) {
				System.out.println(((NamedElement) element).getName() + ": " + isSelected(element));
			} else {
				System.out.println(element.getClass() + ": " + isSelected(element));
			}
		}
		
		//getSelectableElements().stream().forEach(element -> deselect(element));
	}
	
	public void filterForName(String name) {
		getSelectableElements().stream()
			.filter(element -> !(element instanceof NamedElement))
			.forEach(element -> setSelected(element, false));
		
		getSelectableElements().stream()
			.filter(element -> (element instanceof NamedElement))
			.filter(element -> !name.equals(((NamedElement) element).getName()))
			.forEach(element -> setSelected(element, false));
	}
	
	public void removeAttributes() {
		for(EObject object : getSelectableElements()) {
			if (isSelected(object) && (object instanceof Classifier)) {	
				Classifier currentClassifier = (Classifier) object;
				EcoreUtil.removeAll(currentClassifier.getAllAttributes());
			}
		}	
	}
	
	
	
	private void deselect(EObject element) {
		setSelected(element, false);
	}
	
	
	
	
	
	private void selectAllElements() {
		for (EObject element : getSelectableElements()) {
			setSelected(element, true);
		}
		//getSelectableElements().stream().forEach(element -> setSelected(element, true));
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

}
