package tools.vitruv.application.viewfilter.informationview.internal;


import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.applications.viewfilter.helpers.ViewFilterHelper;
import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.InfostructuremodelFactory;
import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.SingleInformation;

//TODO nbr add javadoc
public abstract class CountElementsTransformator implements InformationViewTransformator {
	

	public SingleInformation transform(List<EObject> roots) {
		SingleInformation createSingleInformation = InfostructuremodelFactory.eINSTANCE.createSingleInformation();
		createSingleInformation.setTitle(getTitle());
		int count = 0;
		
		for (EObject root : roots) {
			List<EObject> allElements = ViewFilterHelper.convertTreeIterator2List(root.eAllContents());
			
			for (EObject element : allElements) { 
				if (takeElementIntoAccount(element)) {
					count++;
				}
			}
		}
		
		createSingleInformation.setValue(count);
		return createSingleInformation;
	}
	
	
	protected abstract boolean takeElementIntoAccount(EObject object);
	
	protected abstract String getTitle();
	
	
}
