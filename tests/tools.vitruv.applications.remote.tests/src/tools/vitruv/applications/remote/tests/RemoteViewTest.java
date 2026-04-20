package tools.vitruv.applications.remote.tests;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.remote.client.RemoteView;
import tools.vitruv.framework.remote.client.exception.BadServerResponseException;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RemoteViewTest extends TestBase {

    private RemoteView view;

    @BeforeEach
    public void init() {
        view = remote.getView("test");
    }

    @Test
    public void testGetViewTypes() {
        var types = remote.getViewTypes();
        assertEquals(List.of("test"), types);
    }

    @Test
    public void testGetView() {
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();

        var families = root.getFamilies();
        assertEquals(1, families.size());

        var family = families.get(0);
        assertEquals("Mustermann", family.getLastName());

        var father = family.getFather();
        var mother = family.getMother();

        assertNotNull(father);
        assertNotNull(mother);
        assertEquals("Max", father.getFirstName());
        assertEquals("Erika", mother.getFirstName());

        var sons = family.getSons();
        var daughters = family.getDaughters();

        assertNotNull(sons);
        assertNotNull(daughters);
        assertEquals(1, sons.size());
        assertEquals(1, daughters.size());
        assertEquals("Kevin", sons.get(0).getFirstName());
        assertEquals("Chantal", daughters.get(0).getFirstName());
    }

    @Test
    public void testGetNotExistingView() {
        assertThrows(BadServerResponseException.class, () -> remote.getView("unicorns"));
    }

    @Test
    public void testIsClosed() {
        assertFalse(view.isClosed());
        view.close();
        assertTrue(view.isClosed());
    }

    @Test
    public void testIsModified() {
        assertFalse(view.isModified());
        changeFamilyName(view);
        assertTrue(view.isModified());
    }

    @Test
    public void testIsOutdated() {
        var internalView = model.getTestView().withChangeDerivingTrait();

        assertFalse(view.isOutdated());
        changeFamilyName(internalView);
        internalView.commitChanges();
        assertTrue(view.isOutdated());
    }

    @Test
    public void testRegisterRoot() {
        var view = this.view.withChangeDerivingTrait();
        var register = FamiliesFactory.eINSTANCE.createFamilyRegister();
        register.setId("Family Register 2");
        view.registerRoot(register, URI.createURI("root/model/register2.families"));
        view.commitChanges();

        assertTrue(new File("root/model/register2.families").exists());
        assertEquals(3, model.get().getViewSourceModels().size());
    }

    @Test
    public void testMoveRoot() {
        var view = this.view.withChangeDerivingTrait();
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();
        view.moveRoot(root, URI.createURI("root/model/register_new.families"));
        view.commitChanges();

        assertFalse(new File("root/model/register.families").exists());
        assertTrue(new File("root/model/register_new.families").exists());
    }

    @Test
    public void testGetSelection() {
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();
        var somethingElse = FamiliesFactory.eINSTANCE.createMember();

        assertTrue(view.getSelection().isViewObjectSelected(root));
        assertFalse(view.getSelection().isViewObjectSelected(somethingElse));
    }

    @Test
    public void testUpdate() {
        var internalView = model.getTestView().withChangeRecordingTrait();
        changeFamilyName(internalView);
        assertEquals("Mustermann", view.getRootObjects(FamilyRegister.class).iterator().next().getFamilies().get(0).getLastName());
        internalView.commitChanges();
        view.update();
        assertEquals("Musterfrau", view.getRootObjects(FamilyRegister.class).iterator().next().getFamilies().get(0).getLastName());
    }
}
