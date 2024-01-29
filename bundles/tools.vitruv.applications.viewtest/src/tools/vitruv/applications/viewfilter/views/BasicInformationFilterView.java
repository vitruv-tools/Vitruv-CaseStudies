package tools.vitruv.applications.viewfilter.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.niklas.niklasproject.niklasdomain.InformationStructure;
import com.niklas.niklasproject.niklasdomain.NiklasdomainFactory;
import com.niklas.niklasproject.niklasdomain.SingleInformation;

import tools.vitruv.application.viewfilter.transformation.UmlToInformationTransformator;
import tools.vitruv.applications.viewfilter.helpers.ViewFilterHelper;
import tools.vitruv.applications.viewfilter.util.framework.impl.FilteredViewCreatingViewType;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.viewbuild.ViewFilter;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ModifiableViewSelection;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

public class BasicInformationFilterView extends BasicFilterView {

	public BasicInformationFilterView(FilteredViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType,
			ChangeableViewSource viewSource, ViewSelection selection, ViewFilter viewFilter) {
		super(viewType, viewSource, selection, viewFilter);
	}
	
	
	@Override
	protected void updateSelectedElements(ModifiableViewSelection selection) {
		if (selection == null) {
			throw new NullPointerException("selection is null");
		}
		UmlToInformationTransformator transformator = new UmlToInformationTransformator();
		InformationStructure informationStructure = NiklasdomainFactory.eINSTANCE.createInformationStructure();
		EList<SingleInformation> singleinformationList = informationStructure.getSingleinformation();
		
		//TODO nbr Hier weitermachen! die selectableElements entsprechen den gefilterten Elementen => daraus muss ich meine Informationen berechnen
		for(EObject root : selection.getSelectableElements()) {
			if (selection.isSelected(root)) {
				SingleInformation transformResult = transformator.transform(root);
				if (transformResult != null) {
					singleinformationList.add(transformResult);
	//				mapOriginalRoot2RootStub.
	//				mapOriginalRoot2RootStub_.put(mapOriginalRoot2RootStub.get(root)), )
				}
			}
		}
		
//		for (EObject key : mapOriginalRoot2FilteredRootStub.keySet()) {
//			getMapOriginalRoot2InformationRoot().put(key, informationStructure);
//		}
		
		List<EObject> selectionList = new ArrayList();
		selectionList.add(informationStructure);
		ElementViewSelection elementViewSelection = new ElementViewSelection(selectionList);
		elementViewSelection.setSelected(informationStructure, true);
		setSelection(elementViewSelection);
	}

}
