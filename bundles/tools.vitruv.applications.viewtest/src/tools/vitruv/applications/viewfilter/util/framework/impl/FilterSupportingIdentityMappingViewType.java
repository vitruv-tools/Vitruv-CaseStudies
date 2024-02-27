package tools.vitruv.applications.viewfilter.util.framework.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.internal.resource.UMLResourceImpl;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import tools.vitruv.application.viewfilter.change.FilterOnOriginalResolver;
import tools.vitruv.applications.viewfilter.util.framework.selectors.DirectViewElementSelector;
import tools.vitruv.applications.viewfilter.util.framework.selectors.FilterSupportingViewElementSelectorImpl;
import tools.vitruv.applications.viewfilter.views.BasicFilterView;
import tools.vitruv.applications.viewfilter.views.FilterableView;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.CompositeContainerChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSource;


/**
 * A view type that allows creating views based on a basic element-wise
 * selection mechanism and a filter-function. The filter-function can be used for 
 * selecting single objects in a model. 
 */
public class FilterSupportingIdentityMappingViewType extends AbstractFilterSupportingViewType<FilterSupportingViewElementSelectorImpl<HierarchicalId>, HierarchicalId> {
	
	public FilterSupportingIdentityMappingViewType(String name) {
		super(name);
	}


	//TODO nbr replace by Interface
	@Override
	public FilterSupportingViewElementSelectorImpl<HierarchicalId> createSelector(ChangeableViewSource viewSource) {
		return new FilterSupportingViewElementSelectorImpl<>(this, viewSource,
				viewSource.getViewSourceModels().stream().map(resource -> {
					if (!resource.getContents().isEmpty() && ResourceCopier.requiresFullCopy(resource)) {
						// Some resources (like UML) can only be copied as a whole, so no option to select
						// specific root elements
						return Stream.of(resource.getContents().get(0));
					}
					return resource.getContents().stream();
				}).flatMap(Function.identity()).filter(it -> it != null).toList());
	}
	

	//TODO nbr replace by interface
	@Override
	public ModifiableView createView(FilterSupportingViewElementSelectorImpl<HierarchicalId> selector) {
		checkArgument(selector.getViewType() == this, "cannot create view with selector for different view type");
		return new BasicFilterView(selector.getViewType(), selector.getViewSource(), selector.getSelection(), selector.getViewFilter());
	}

	@Override
	public void updateView(ModifiableView view) {
		view.modifyContents((viewResourceSet) -> {
			viewResourceSet.getResources().forEach(Resource::unload);
			viewResourceSet.getResources().clear();
			createViewResources(view, viewResourceSet);
		});
	}

	
	public void commitViewChanges(FilterableView view, VitruviusChange<HierarchicalId> viewChange) {
		ResourceSet viewSourceCopyResourceSet = withGlobalFactories(new ResourceSetImpl());
//		VitruviusChangeResolver<HierarchicalId> idChangeResolver = VitruviusChangeResolver.forHierarchicalIds(viewSourceCopyResourceSet);
		VitruviusChangeResolver<HierarchicalId> idChangeResolver = VitruviusChangeResolver.forHierarchicalIdsAndFilteredModel(viewSourceCopyResourceSet, view.getFilteredModelsInResourceSet(), view.getMapCopy2OriginalObject());
		UuidResolver viewSourceCopyUuidResolver = UuidResolver.create(viewSourceCopyResourceSet);
		VitruviusChangeResolver<Uuid> uuidChangeResolver = VitruviusChangeResolver.forUuids(viewSourceCopyUuidResolver);
		Map<Resource, Resource> mapping = createViewResources(view, viewSourceCopyResourceSet);
		mapChangeOnSourceIds(viewChange);
		view.getViewSource().getUuidResolver().resolveResources(mapping, viewSourceCopyUuidResolver);
		

//		FilterOnOriginalResolver filterOnOriginalResolver = new FilterOnOriginalResolver();
//		filterOnOriginalResolver.resolveFilteredChangesOnOriginalSources(viewChange);
		VitruviusChange<EObject> resolvedChange = idChangeResolver.resolveAndApply(viewChange);
		VitruviusChange<Uuid> unresolvedChanges = uuidChangeResolver.assignIds(resolvedChange);
		view.getViewSource().propagateChange(unresolvedChanges);
	}
	
	
	@Override
	public void commitViewChanges(ModifiableView view, VitruviusChange<HierarchicalId> viewChange) {
		if (view instanceof FilterableView filterableView) {
			commitViewChanges(filterableView, viewChange);
			return;
		}
		ResourceSet viewSourceCopyResourceSet = withGlobalFactories(new ResourceSetImpl());
		VitruviusChangeResolver<HierarchicalId> idChangeResolver = VitruviusChangeResolver.forHierarchicalIds(viewSourceCopyResourceSet);
		UuidResolver viewSourceCopyUuidResolver = UuidResolver.create(viewSourceCopyResourceSet);
		VitruviusChangeResolver<Uuid> uuidChangeResolver = VitruviusChangeResolver.forUuids(viewSourceCopyUuidResolver);
		Map<Resource, Resource> mapping = createViewResources(view, viewSourceCopyResourceSet);
		view.getViewSource().getUuidResolver().resolveResources(mapping, viewSourceCopyUuidResolver);

		VitruviusChange<EObject> resolvedChange = idChangeResolver.resolveAndApply(viewChange);
		VitruviusChange<Uuid> unresolvedChanges = uuidChangeResolver.assignIds(resolvedChange);
		view.getViewSource().propagateChange(unresolvedChanges);
	}

	private Map<Resource, Resource> createViewResources(ModifiableView view, ResourceSet viewResourceSet) {
		Collection<Resource> viewSources = view.getViewSource().getViewSourceModels();
		ViewSelection selection = ((FilterableView) view).getPreFilterSelection();
		List<Resource> resourcesWithSelectedElements = viewSources.stream()
				.filter(resource -> resource.getContents().stream().anyMatch(selection::isViewObjectSelected)).toList();
		return ResourceCopier.copyViewSourceResources(resourcesWithSelectedElements, viewResourceSet,
				selection::isViewObjectSelected);
	}
	
	
	private void mapChangeOnSourceIds(VitruviusChange<HierarchicalId> viewChange) {
		if (viewChange instanceof CompositeContainerChange<HierarchicalId> compositeChange) {
			
		} else {
			
		}
		
		
		viewChange.getAffectedEObjects();
		
	}

}
