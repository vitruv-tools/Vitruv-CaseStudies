package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import tools.vitruv.framework.tests.*;

class Class2CompTestUtil {
	
	private static ConcurrentLinkedQueue<Integer> concurrentIntLinkedQueue = new ConcurrentLinkedQueue<Integer>();
	
	//Store given UserInteractions for later by appending them
	protected static void queueUserInteractionSelections(final Integer... nextSelections) {
		concurrentIntLinkedQueue.addAll(Arrays.asList(nextSelections));
	}
	
	//Commit currently pending UserInteractions
	protected static void sendCollectedUserInteractionSelections(TestUserInteractor userInteractor) {
	        userInteractor.addNextSelections(concurrentIntLinkedQueue.toArray(new Integer[0]));
	        concurrentIntLinkedQueue.clear();
	}
}
