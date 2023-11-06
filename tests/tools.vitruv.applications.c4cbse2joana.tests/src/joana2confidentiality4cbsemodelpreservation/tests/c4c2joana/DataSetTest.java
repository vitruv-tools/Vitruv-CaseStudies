package joana2confidentiality4cbsemodelpreservation.tests.c4c2joana;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;
import edu.kit.kastel.scbs.confidentiality.data.DataFactory;
import edu.kit.kastel.scbs.confidentiality.data.DataSet;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JOANARoot;
import tools.vitruv.framework.views.CommittableView;

public class DataSetTest extends C4C2joanaTestSetup {
	
	private static final String MY_SET = "MySet";
	private static final String SECOND_SET = "SecondSet";
	
	protected void createDataSetFromEnum(String name) {
		CommittableView view = getCombinedPCMC4CView();		
		DataSet newSet = DataFactory.eINSTANCE.createDataSet();
		newSet.setEntityName(name);
		getC4CRoot(view).getDataIdentifier().add(newSet);
		view.commitChanges();
	}
	
	@Test
	void singleDataSetCreationTest() {
		JOANARoot joanaRoot = getJoanaRoot(getCombinedJavaJoanaView());
		assertTrue(joanaRoot.getSecuritylevel().size() == 1);
		assertTrue(joanaRoot.getSecuritylevel().get(0).getName().matches(MY_SET));
		assertEquals(0, joanaRoot.getLattice().getFlowrelation().size());
	}
	
	@Test
	void multipleDataSetCreationTest() {
		createDataSetFromEnum(SECOND_SET);
		JOANARoot joanaRoot = getJoanaRoot(getCombinedJavaJoanaView());
		assertEquals(3, joanaRoot.getSecuritylevel().size());
		assertTrue(joanaRoot.getSecuritylevel().get(0).getName().matches(MY_SET));
		assertEquals(joanaRoot.getSecuritylevel().get(1).getName(), SECOND_SET);
		assertTrue(joanaRoot.getSecuritylevel().get(2).getName().matches(MY_SET + "," + SECOND_SET));
		assertEquals(2, joanaRoot.getLattice().getFlowrelation().size());
	}
	
	@Test
	void singleDataSetDeleted() {
		CommittableView view = getCombinedPCMC4CView();
		EcoreUtil.remove(getC4CRoot(view).getDataIdentifier().get(0));
		view.commitChanges();
		assertEquals(0, getJoanaRoot(getCombinedJavaJoanaView()).getSecuritylevel().size());
	}
	
	@Test
	void multipleDataSetDeleted() {
		createDataSetFromEnum(SECOND_SET);
		CommittableView view = getCombinedPCMC4CView();
		assertEquals(3, getJoanaRoot(getCombinedJavaJoanaView()).getSecuritylevel().size());
		EcoreUtil.remove(getC4CRoot(view).getDataIdentifier().get(0));
		view.commitChanges();
		assertEquals(1, getJoanaRoot(getCombinedJavaJoanaView()).getSecuritylevel().size());
		assertEquals(getJoanaRoot(getCombinedJavaJoanaView()).getSecuritylevel().get(0).getName(), SECOND_SET);
		view = getCombinedPCMC4CView();
		EcoreUtil.remove(getC4CRoot(view).getDataIdentifier().get(0));
		view.commitChanges();
		JOANARoot joanaRoot = getJoanaRoot(getCombinedJavaJoanaView());
		assertEquals(0, joanaRoot.getSecuritylevel().size());
	}

}
