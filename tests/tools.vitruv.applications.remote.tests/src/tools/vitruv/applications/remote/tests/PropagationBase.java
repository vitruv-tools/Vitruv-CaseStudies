package tools.vitruv.applications.remote.tests;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class PropagationBase extends TestBase {

    protected CommittableView view;

    private void changeFathersNameToHans(View view) {
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();
        var family = root.getFamilies().get(0);
        var father = family.getFather();
        father.setFirstName("Hans");
    }

    private void addSonKevin(View view) {
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();
        var family = root.getFamilies().get(0);
        var son = FamiliesFactory.eINSTANCE.createMember();
        son.setFirstName("Kevin");
        son.setFamilySon(family);
    }

    private void removeDaughters(View view) {
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();
        var family = root.getFamilies().get(0);
        family.getDaughters().clear();
    }

    private void addFamilyMueller(View view) {
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();
        var family = FamiliesFactory.eINSTANCE.createFamily();
        family.setLastName("Mueller");
        root.getFamilies().add(family);
    }

    private void checkFathersNameChanged() {
        var changedView = model.getTestView();
        var changedRoot = changedView.getRootObjects(FamilyRegister.class).iterator().next();
        var changedFamily = changedRoot.getFamilies().get(0);
        var changedFather = changedFamily.getFather();

        assertEquals("Hans", changedFather.getFirstName());
    }

    private void checkSonAdded() {
        var changedView = model.getTestView();
        var changedRoot = changedView.getRootObjects(FamilyRegister.class).iterator().next();
        var changedFamily = changedRoot.getFamilies().get(0);
        var sons = changedFamily.getSons();

        assertEquals(2, sons.size());
        assertTrue(sons.stream().anyMatch(s -> s.getFirstName().equals("Kevin")));
    }

    private void checkDaughtersRemoved() {
        var changedView = model.getTestView();
        var changedRoot = changedView.getRootObjects(FamilyRegister.class).iterator().next();
        var changedFamily = changedRoot.getFamilies().get(0);

        assertEquals(0, changedFamily.getDaughters().size());
    }

    private void checkFamilyAdded() {
        var changedView = model.getTestView();
        var changedRoot = changedView.getRootObjects(FamilyRegister.class).iterator().next();

        assertEquals(2, changedRoot.getFamilies().size());
        assertTrue(changedRoot.getFamilies().stream().anyMatch(f -> f.getLastName().equals("Mueller")));
    }

    public abstract void initView();

    @BeforeEach
    public void before() {
        initView();
    }

    @Test
    public void testChangeFathersName() {
        changeFathersNameToHans(view);
        view.commitChanges();
        checkFathersNameChanged();
    }

    @Test
    public void testAddSon() {
        addSonKevin(view);
        view.commitChanges();
        checkSonAdded();
    }

    @Test
    public void testRemoveDaughters() {
        removeDaughters(view);
        view.commitChanges();
        checkDaughtersRemoved();
    }

    @Test
    public void testAddFamily() {
        addFamilyMueller(view);
        view.commitChanges();
        checkFamilyAdded();
    }
}
