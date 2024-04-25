package joana2confidentiality4cbsemodelpreservation.tests.joana2c4c;

import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.ConfidentialitySpecification;
import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.data.DataSet;
import edu.kit.ipd.sdq.metamodels.joana.JOANARoot;
import edu.kit.ipd.sdq.metamodels.joana.JoanaFactory;
import edu.kit.ipd.sdq.metamodels.joana.SecurityLevel;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.views.CommittableView;

public class SecurityLevelTest extends Joana2c4cTest{
	
	private static final String LEVEL_LOW = "LowLevel";
	private static final String LEVEL_HIGH = "HighLevel";
	private static final String LEVEL_MIDDLE = "MiddleLevel";
	
	protected void createJoanaModelWith3BasicSecLevs() {
		CommittableView view = getJoanaViewWithJOANARoot();
		getJoanaRoot(view).setLattice(JoanaFactory.eINSTANCE.createLattice());
		
		SecurityLevel levelHigh = JoanaFactory.eINSTANCE.createSecurityLevel();
		levelHigh.setName(LEVEL_HIGH);
		getJoanaRoot(view).getSecuritylevel().add(levelHigh);

		
		SecurityLevel levelLow = JoanaFactory.eINSTANCE.createSecurityLevel();
		levelLow.setName(LEVEL_LOW);
		getJoanaRoot(view).getSecuritylevel().add(levelLow);

		SecurityLevel levelMiddle = JoanaFactory.eINSTANCE.createSecurityLevel();
		levelMiddle.setName(LEVEL_MIDDLE);
		getJoanaRoot(view).getSecuritylevel().add(levelMiddle);
		
		view.commitChanges();
	}
	
	@Test
	void secLevCreationTest() {
		
		createJoanaModelWith3BasicSecLevs();
		CommittableView view = getJoanaView();
		JOANARoot joanaRoot = getJoanaRoot(view);

		assertTrue(joanaRoot.getSecuritylevel().size() == 7);
		assertTrue(joanaRoot.getSecuritylevel().get(0).getName().matches(LEVEL_HIGH));
		assertTrue(joanaRoot.getSecuritylevel().get(1).getName().matches(LEVEL_LOW));
		
		ConfidentialitySpecification c4cRoot = getC4CRoot(getC4CView());
		assertTrue(c4cRoot.getDataIdentifier().size() == 3);
		assertTrue(((DataSet) c4cRoot.getDataIdentifier().get(0)).getEntityName().matches(LEVEL_HIGH));
		assertTrue(((DataSet) c4cRoot.getDataIdentifier().get(1)).getEntityName().matches(LEVEL_LOW));
	}
	
	@Test
	void basicSecLevDeletionTest() {
		createJoanaModelWith3BasicSecLevs();
		CommittableView view = getJoanaView();
		
		EcoreUtil.delete(getJoanaRoot(view).getSecuritylevel().get(0));
//		getJoanaRoot(view).getSecuritylevel().remove(0);
		view.commitChanges();
		
		view = getJoanaView();
		JOANARoot joanaRoot = getJoanaRoot(view);

		assertTrue(joanaRoot.getSecuritylevel().size() == 3);
		assertTrue(joanaRoot.getSecuritylevel().get(0).getName().matches(LEVEL_LOW));
		assertTrue(joanaRoot.getSecuritylevel().get(1).getName().matches(LEVEL_MIDDLE));
		
		ConfidentialitySpecification c4cRoot = getC4CRoot(getC4CView());
		assertTrue(c4cRoot.getDataIdentifier().size() == 2);
		assertTrue(((DataSet) c4cRoot.getDataIdentifier().get(0)).getEntityName().matches(LEVEL_LOW));
		assertTrue(((DataSet) c4cRoot.getDataIdentifier().get(1)).getEntityName().matches(LEVEL_MIDDLE));
	}
	
	@Test
	void compositeSecLevDeletionTest() {
		createJoanaModelWith3BasicSecLevs();
		CommittableView view = getJoanaView();
		JOANARoot joanaRoot = getJoanaRoot(view);
		SecurityLevel toDelete = null;
		for(SecurityLevel level : joanaRoot.getSecuritylevel()) {
			if(level.getName().matches(LEVEL_HIGH + LEVEL_LOW)) {
				toDelete = level;
			}
		}
		joanaRoot.getSecuritylevel().remove(toDelete);
		view.commitChanges();
		
		assertTrue(joanaRoot.getSecuritylevel().size() == 6);
		boolean deletedLevelFound = false;
		for(SecurityLevel level : joanaRoot.getSecuritylevel()) {
			if(level.getName().matches(LEVEL_HIGH + LEVEL_LOW)) {
				deletedLevelFound = true;
			}
		}
		assertFalse(deletedLevelFound);
	}
}
