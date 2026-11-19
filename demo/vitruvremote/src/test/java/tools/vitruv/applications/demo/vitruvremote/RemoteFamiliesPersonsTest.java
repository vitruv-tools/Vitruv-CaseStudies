package tools.vitruv.applications.demo.vitruvremote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import tools.vitruv.framework.remote.server.VitruvServer;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;

/**
 * Runs the families-persons V-SUM on a {@link VitruvServer} and changes it through a remote
 * {@link VitruvClient}: the changes made in remote views are propagated on the server and show up
 * in other remote views.
 */
class RemoteFamiliesPersonsTest {
  private static final String HOST = "localhost";

  private FamiliesPersonsVsumWrapper vsum;
  private VitruvServer server;
  private VitruvClient client;

  @BeforeEach
  void startServerAndClient(@TempDir Path serverRoot, @TempDir Path clientTemp) throws Exception {
    vsum = new FamiliesPersonsVsumWrapper();
    vsum.initialize(serverRoot);
    addFamilyMustermann();
    int port = freePort();
    server = new VitruvServer(vsum::getVsum, port, HOST);
    server.start();
    client = VitruvClientFactory.create(HOST, port, clientTemp);
  }

  @AfterEach
  void stopServer() {
    server.stop();
    vsum.close();
  }

  private void addFamilyMustermann() throws Exception {
    CommittableView view = vsum.getFamilyView().withChangeRecordingTrait();
    FamilyRegister register = view.getRootObjects(FamilyRegister.class).iterator().next();
    Family family = FamiliesFactory.eINSTANCE.createFamily();
    family.setLastName("Mustermann");
    family.setFather(member("Max"));
    family.setMother(member("Erika"));
    family.getSons().add(member("Kevin"));
    family.getDaughters().add(member("Chantal"));
    register.getFamilies().add(family);
    view.commitChanges();
    view.close();
  }

  private static edu.kit.ipd.sdq.metamodels.families.Member member(String firstName) {
    var member = FamiliesFactory.eINSTANCE.createMember();
    member.setFirstName(firstName);
    return member;
  }

  private static int freePort() throws IOException {
    try (ServerSocket socket = new ServerSocket(0)) {
      return socket.getLocalPort();
    }
  }

  private View remoteView(String viewTypeName) {
    var viewType = client.getViewTypes().stream()
        .filter(type -> type.getName().equals(viewTypeName))
        .findFirst()
        .orElseThrow();
    var selector = viewType.createSelector(null);
    selector.getSelectableElements().forEach(element -> selector.setSelected(element, true));
    return selector.createView();
  }

  private static FamilyRegister familyRegister(View view) {
    return view.getRootObjects(FamilyRegister.class).iterator().next();
  }

  private static Family firstFamily(View view) {
    return familyRegister(view).getFamilies().get(0);
  }

  /** The full names of all persons, read through a new remote persons view. */
  private Set<String> remotePersonNames() throws Exception {
    View personsView = remoteView(DemoUtility.PERSONS_VIEW_TYPE_NAME);
    try {
      return personsView.getRootObjects(PersonRegister.class).iterator().next()
          .getPersons().stream()
          .map(Person::getFullName)
          .collect(Collectors.toSet());
    } finally {
      personsView.close();
    }
  }

  static Stream<Arguments> traits() {
    Function<View, CommittableView> recording = View::withChangeRecordingTrait;
    Function<View, CommittableView> deriving = View::withChangeDerivingTrait;
    return Stream.of(Arguments.of("change recording", recording),
        Arguments.of("change deriving", deriving));
  }

  @Test
  void providesTheViewTypesOfTheVsum() {
    var names = client.getViewTypes().stream().map(type -> type.getName()).collect(Collectors.toSet());
    assertEquals(Set.of(DemoUtility.FAMILIES_VIEW_TYPE_NAME, DemoUtility.PERSONS_VIEW_TYPE_NAME), names);
  }

  @Test
  void remoteViewContainsTheFamilyOfTheServer() throws Exception {
    View view = remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME);
    Family family = firstFamily(view);
    assertEquals("Mustermann", family.getLastName());
    assertEquals("Max", family.getFather().getFirstName());
    assertEquals("Erika", family.getMother().getFirstName());
    assertEquals("Kevin", family.getSons().get(0).getFirstName());
    assertEquals("Chantal", family.getDaughters().get(0).getFirstName());
    view.close();
  }

  @Test
  void personsWereCreatedOnTheServer() throws Exception {
    assertEquals(Set.of("Max Mustermann", "Erika Mustermann", "Kevin Mustermann", "Chantal Mustermann"),
        remotePersonNames());
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("traits")
  void renamingAFatherIsPropagated(String trait, Function<View, CommittableView> withTrait) throws Exception {
    CommittableView view = withTrait.apply(remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME));
    firstFamily(view).getFather().setFirstName("Hans");
    view.commitChanges();
    view.close();

    assertTrue(remotePersonNames().contains("Hans Mustermann"));
    assertFalse(remotePersonNames().contains("Max Mustermann"));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("traits")
  void addingASonIsPropagated(String trait, Function<View, CommittableView> withTrait) throws Exception {
    CommittableView view = withTrait.apply(remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME));
    firstFamily(view).getSons().add(member("Tom"));
    view.commitChanges();
    view.close();

    assertTrue(remotePersonNames().contains("Tom Mustermann"));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("traits")
  void removingTheDaughtersIsPropagated(String trait, Function<View, CommittableView> withTrait)
      throws Exception {
    CommittableView view = withTrait.apply(remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME));
    firstFamily(view).getDaughters().clear();
    view.commitChanges();
    view.close();

    assertFalse(remotePersonNames().contains("Chantal Mustermann"));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("traits")
  void renamingAFamilyIsPropagated(String trait, Function<View, CommittableView> withTrait) throws Exception {
    CommittableView view = withTrait.apply(remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME));
    firstFamily(view).setLastName("Musterfrau");
    view.commitChanges();
    view.close();

    assertEquals(Set.of("Max Musterfrau", "Erika Musterfrau", "Kevin Musterfrau", "Chantal Musterfrau"),
        remotePersonNames());
  }

  @Test
  void changesInAViewMarkItModified() throws Exception {
    CommittableView view = remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME).withChangeRecordingTrait();
    assertFalse(view.isModified());
    firstFamily(view).setLastName("Musterfrau");
    assertTrue(view.isModified());
    view.close();
  }

  @Test
  void closingAViewClosesIt() throws Exception {
    View view = remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME);
    assertFalse(view.isClosed());
    view.close();
    assertTrue(view.isClosed());
  }

  @Test
  void updateFetchesChangesCommittedByAnotherView() throws Exception {
    View reader = remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME);
    CommittableView writer = remoteView(DemoUtility.FAMILIES_VIEW_TYPE_NAME).withChangeRecordingTrait();
    firstFamily(writer).setLastName("Musterfrau");
    writer.commitChanges();
    writer.close();

    assertEquals("Mustermann", firstFamily(reader).getLastName());
    reader.update();
    assertEquals("Musterfrau", firstFamily(reader).getLastName());
    reader.close();
  }
}
