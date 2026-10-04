package tools.vitruv.applications.demo.familiespersons.persons2families;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import edu.kit.ipd.sdq.metamodels.persons.Person;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality;
import tools.vitruv.change.interaction.UserInteractor;

public final class PersonsToFamiliesHelper {

	public static final String EXCEPTION_MESSAGE_FIRSTNAME_NULL = "A person's fullname is not allowed to be null.";
	public static final String EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE = "A person's fullname has to contain at least one non-whitespace character.";
	public static final String EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES = "A person's fullname cannot contain any whitespace escape sequences.";

	private PersonsToFamiliesHelper() {
	}

	/**Returns the eContainer of a Person casted as Personregister, if it is contained in a Personregister.
	 * @return <code>person.eContainer</code> as PersonRegister, if it actually is one; <code>null</code>, else.
	 */
	public static PersonRegister getRegister(Person person) {
		return person.eContainer() instanceof PersonRegister register ? register : null;
	}

	/**Creates a string representation of a family which is used to label the different options during user interactions.
	 * Representation contains names and positions of all members. Person model informations such as birthdates are not used.
	 * @param family Family to represent as string.
	 * @return String representation.
	 */
	public static String stringifyFamily(Family family) {
		StringBuilder builder = new StringBuilder().append(family.getLastName()).append(": ");
		if (family.getFather() != null) { builder.append("F: ").append(family.getFather().getFirstName()).append(";"); }
		if (family.getMother() != null) { builder.append("M: ").append(family.getMother().getFirstName()).append(";"); }
		if (family.getSons() != null && family.getSons().size() > 0) { builder.append(joinFirstNames(family.getSons(), "S: (")); }
		if (family.getDaughters() != null && family.getDaughters().size() > 0) { builder.append(joinFirstNames(family.getDaughters(), "D: (")); }
		return builder.toString();
	}

	private static String joinFirstNames(List<Member> members, String prefix) {
		return members.stream().map(Member::getFirstName).collect(Collectors.joining(", ", prefix, ")"));
	}

	/**Extension, which returns the part of the person's fullname which represents the firstname. This is currently by convention
	 * everything except the last part which is separated by a space from the rest of the name. If the name does not contain a whitespace,
	 * the whole name is considered to be the firstname, which symbolizes, that the person has no lastname.
	 * @param person Person to retrieve the firstname from.
	 * @return As firstname interpreted part of the fullname.
	 */
	public static String getFirstname(Person person) {
		String[] nameParts = person.getFullName().split(" ");
		String firstName = null;
		if (nameParts.length == 1) {
			firstName = person.getFullName();
		} else {
			firstName = String.join(" ", Arrays.asList(nameParts).subList(0, nameParts.length - 1));
		}
		return firstName;
	}

	/**Extension, which returns the part of the person's fullname which represents the lastname. This is currently by convention
	 * the last part which is separated by a space from the rest of the name. If the name does not contain a whitespace,
	 * the empty string is returned, which symbolizes, that the person has only a firstname and no lastname.
	 * @param person Person to retrieve the lastname from.
	 * @return As lastname interpreted part of the fullname or <code>""</code>
	 */
	public static String getLastname(Person person) {
		if (!person.getFullName().contains(" ")) {
			return "";
		} else {
			String[] nameParts = person.getFullName().split(" ");
			return nameParts[nameParts.length - 1];
		}
	}

	public static FamilyRole askUserWhetherPersonIsParentOrChild(UserInteractor userInteractor, Person newPerson) {

		StringBuilder parentOrChildMessageBuilder = new StringBuilder()
			.append("You have inserted ")
			.append(newPerson.getFullName())
			.append(" into the persons register which results into the creation of a corresponding member into the family register.")
			.append(" Is this member supposed to be a parent or a child in the family register?");

		List<String> parentOrChildOptions = List.of("Parent", "Child");

		int parentOrChildSelection = userInteractor
			.getSingleSelectionDialogBuilder()
			.message(parentOrChildMessageBuilder.toString())
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction();

		return parentOrChildSelection == parentOrChildOptions.indexOf("Child") ? FamilyRole.Child : FamilyRole.Parent;
	}

	public static FamilyRole askUserWhetherPersonIsParentOrChildDuringRenaming(UserInteractor userInteractor,
		String oldFullname, String newFullname, boolean wasChildBefore) {

		StringBuilder parentOrChildMessageBuilder = new StringBuilder()
			.append("You have renamed ")
			.append(oldFullname)
			.append(" to ")
			.append(newFullname)
			.append(", which might cause the corresponding member in the families model to change its position inside a family.")
			.append(" Which position should this member, who was a ")
			.append(wasChildBefore ? "child" : "parent")
			.append(" before, have after the renaming?");

		List<String> parentOrChildOptions = List.of("Parent", "Child");

		int parentOrChildSelection = userInteractor
			.getSingleSelectionDialogBuilder()
			.message(parentOrChildMessageBuilder.toString())
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction();

		return parentOrChildSelection == parentOrChildOptions.indexOf("Child") ? FamilyRole.Child : FamilyRole.Parent;
	}

	/**Filters out families which already have a parent of the same sex as the given person.
	 * @param person Person whose last name is used for the filter.
	 * @return Lambda to filter out all families whose last name is different from the newPerson's.
	 */
	public static Function1<Family, Boolean> sameLastname(Person person) {
		String newPersonLastname = getLastname(person);
		return (Family family) -> family.getLastName().equals(newPersonLastname);
	}

	/**Filters out families which already have a parent of the same sex as the given person.
	 * @param person Person whose sex determines whether families with father or with mother are filtered out.
	 * @return Lambda to filter out all families which already have a parent with the same sex as the newPerson.
	 */
	public static Function1<Family, Boolean> noParent(Person person) {
		return (Family family) -> person instanceof Male ? family.getFather() == null : family.getMother() == null;
	}

	/**Sets up an user interaction to ask the user if he wants to insert the corresponding member to the {@code newPerson}
	 * either into a new family or into one of the {@code selectableFamilies} and if so, in which.
	 * @return chosen family, if existing family was selected; <code>null</code>, if users wants to create and insert into a new family
	 */
	public static Family askUserWhichFamilyToInsertTheMemberIn(UserInteractor userInteractor, Person newPerson,
		Iterable<Family> selectableFamilies) {
		// Let user select the family
		StringBuilder whichFamilyMessageBuilder = new StringBuilder()
			.append("Please choose whether you want to create a new family or insert ")
			.append(newPerson.getFullName())
			.append(" into one of the existing families.");

		// Prepare options to select from
		Collection<String> whichFamilyOptions = new ArrayList<>();
		whichFamilyOptions.add("insert in a new family");
		for (Family family : selectableFamilies) {
			whichFamilyOptions.add(stringifyFamily(family));
		}

		// Start interaction
		int whichFamilyIndex = userInteractor
			.getSingleSelectionDialogBuilder()
			.message(whichFamilyMessageBuilder.toString())
			.choices(whichFamilyOptions)
			.title("New or Existing Family?")
			.windowModality(WindowModality.MODAL)
			.startInteraction();

		return whichFamilyIndex == 0 ? null : Iterables.get(selectableFamilies, whichFamilyIndex - 1);
	}


	/**Checks if a persons fullname is <code>null</code>, empty or contains escape sequences.
	 * @param person The person of which the fullname has to be valid.
	 * @throws IllegalStateException if the persons fullname in invalid.
	 */
	public static void assertValidFullname(Person person) {
		if (person.getFullName() == null) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_NULL);
		} else if (person.getFullName().trim().isEmpty()) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE);
		} else if (person.getFullName().contains("\n") || person.getFullName().contains("\t")
			|| person.getFullName().contains("\r")) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES);
		}
	}
}
