package tools.vitruv.applications.viewfilter.helpers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

public class ViewFilterHelper {
	
	private ViewFilterHelper() {
		//static helper class - should not be instantiated
	}
	
	public static List<EObject> convertTreeIterator2List(TreeIterator<EObject> content) {
		List<EObject> list = new LinkedList<EObject>();
		while(content.hasNext()) {
			list.add(content.next());
		}
		return list;
	}

}
