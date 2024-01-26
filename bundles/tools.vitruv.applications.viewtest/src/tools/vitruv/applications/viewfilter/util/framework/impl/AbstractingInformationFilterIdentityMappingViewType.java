package tools.vitruv.applications.viewfilter.util.framework.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.niklas.niklasproject.niklasdomain.InformationStructure;
import com.niklas.niklasproject.niklasdomain.NiklasdomainFactory;
import com.niklas.niklasproject.niklasdomain.SingleInformation;

import tools.vitruv.application.viewfilter.transformation.UmlToInformationTransformator;
import tools.vitruv.applications.viewfilter.util.framework.selection.ElementViewSelection;
import tools.vitruv.applications.viewfilter.util.framework.selectors.FilterSupportingViewElementSelector;
import tools.vitruv.applications.viewfilter.views.BasicFilterView;
import tools.vitruv.applications.viewfilter.views.BasicInformationFilterView;
import tools.vitruv.change.atomic.hid.HierarchicalId;

public class AbstractingInformationFilterIdentityMappingViewType extends FilterSupportingIdentityMappingViewType {

	public AbstractingInformationFilterIdentityMappingViewType(String name) {
		super(name);
	}

	
	@Override
	public ModifiableView createView(FilterSupportingViewElementSelector<HierarchicalId> selector) {
		checkArgument(selector.getViewType() == this, "cannot create view with selector for different view type");
		//mapOriginalRoot2RootStub = selector.getMapOriginalRoot2RootStub();
//		List<EObject> roots = new ArrayList();
//		selector.getViewSource().getViewSourceModels().forEach(it -> roots.addAll(it.getContents()));
		
//		UmlToInformationTransformator transformator = new UmlToInformationTransformator();
//		InformationStructure informationStructure = NiklasdomainFactory.eINSTANCE.createInformationStructure();
//		EList<SingleInformation> singleinformationList = informationStructure.getSingleinformation();
//		
//		for(EObject root : roots) {
//			SingleInformation transformResult = transformator.transform(root);
//			if (transformResult != null) {
//				singleinformationList.add(transformResult);
//			}
//		}
//		
//		List<EObject> selectionList = new ArrayList();
//		selectionList.add(informationStructure);
//		ElementViewSelection elementViewSelection = new ElementViewSelection(selectionList);
//		elementViewSelection.setSelected(informationStructure, true);
		
		//Hier in SingleInformation-Meta-Modell übertragen
		BasicInformationFilterView view = new BasicInformationFilterView(selector.getViewType(), selector.getViewSource(), selector.getSelection(), selector.getViewFilter());
		return view;
	}
	
}
                