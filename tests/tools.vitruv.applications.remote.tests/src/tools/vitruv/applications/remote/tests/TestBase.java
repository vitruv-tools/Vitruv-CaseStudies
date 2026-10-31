package tools.vitruv.applications.remote.tests;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import tools.vitruv.framework.views.View;
import tools.vitruv.framework.remote.client.VitruvRemoteConnection;
import tools.vitruv.framework.remote.server.VitruvServer;
import tools.vitruv.applications.remote.tests.demo.FamiliesPersonsModel;

public abstract class TestBase {

    protected final VitruvRemoteConnection remote = new VitruvRemoteConnection("localhost");

    protected FamiliesPersonsModel model;
    protected VitruvServer server;

    @BeforeEach
    public void setup() {
        model = new FamiliesPersonsModel();
        var view = model.getTestView().withChangeRecordingTrait();
        var database = view.getRootObjects(FamilyRegister.class).iterator().next();

        var family = FamiliesFactory.eINSTANCE.createFamily();
        family.setLastName("Mustermann");
        var father = FamiliesFactory.eINSTANCE.createMember();
        father.setFirstName("Max");
        var mother = FamiliesFactory.eINSTANCE.createMember();
        mother.setFirstName("Erika");
        var son = FamiliesFactory.eINSTANCE.createMember();
        son.setFirstName("Kevin");
        var daughter = FamiliesFactory.eINSTANCE.createMember();
        daughter.setFirstName("Chantal");

        family.setFather(father);
        family.setMother(mother);
        son.setFamilySon(family);
        daughter.setFamilyDaughter(family);

        database.getFamilies().add(family);
        view.commitChanges();

        server = new VitruvServer(() -> model.get());
        server.start();
        server.awaitInit();
    }

    protected void changeFamilyName(View view) {
        var root = view.getRootObjects(FamilyRegister.class).iterator().next();
        root.getFamilies().get(0).setLastName("Musterfrau");
    }

    @AfterEach
    public void clean() {
        server.stop();
        model.delete();
    }
}
