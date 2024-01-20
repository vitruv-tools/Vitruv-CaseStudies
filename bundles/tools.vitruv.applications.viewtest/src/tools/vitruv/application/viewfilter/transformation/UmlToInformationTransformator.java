package tools.vitruv.application.viewfilter.transformation;

import com.niklas.niklasproject.niklasdomain.NiklasdomainFactory;
import com.niklas.niklasproject.niklasdomain.SingleInformation;

import tools.vitruv.applications.viewfilter.helpers.ViewFilterHelper;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;

public class UmlToInformationTransformator {

	public SingleInformation transform(EObject root) {
		if (!(root instanceof Model)) {
			//not an Uml model
			return null;
		}
		//TODO nbruening hier noch generischer machen
		SingleInformation createSingleInformation = NiklasdomainFactory.eINSTANCE.createSingleInformation();
		createSingleInformation.setTitle("Anzahl Elemente");
		
		List<EObject> allElements = ViewFilterHelper.convertTreeIterator2List(root.eAllContents());
		
		int count = 0;
		
		for (EObject element : allElements) { 
			if (element instanceof org.eclipse.uml2.uml.Class) {
				count++;
			}
		}
		
		createSingleInformation.setValue(count);
		return createSingleInformation;
	}
}
