package tools.vitruv.applications.cbs.commonalities.tests.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import java.util.List

@Utility
class ParameterizedTestUtil {

	/**
	 * Produces a list of all ordered pairwise combinations of the given
	 * elements, excluding pairs of elements with themselves.
	 * <p>
	 * The produced pairs correspond to the Cartesian product of the given List
	 * with itself, excluding all pairs of elements with themselves. For a List
	 * with elements <code>[e1, e2, e3]</code> this produces:
	 * <code>[(e1, e2), (e1, e3), (e2, e1), (e2, e3), (e3, e1), (e3, e2)]</code>
	 * <p>
	 * Each pair is represented as an array of two elements.
	 * 
	 * @param list the list of elements
	 * @return the list of element pairs
	 */
	static def List<Object[]> orderedPairs(List<?> list) {
		val List<Object[]> pairs = new ArrayList()
		for (Object element1 : list) {
			for (Object element2 : list) {
				if (element1 != element2) {
					pairs.add(#[element1, element2])
				}
			}
		}
		return pairs
	}
}
