package tools.vitruv.applications.demo.insurancefamilies.insurance2families;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.insurance.Gender;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality;
import tools.vitruv.change.interaction.UserInteractor;

public final class InsuranceToFamiliesHelper {
	public static final String EXCEPTION_MESSAGE_FIRSTNAME_NULL = "A insurance clients's name is not allowed to be null.";
	public static final String EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE = "A insurance clients's name has to contain at least one non-whitespace character.";
	public static final String EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES = "A insurance clients's name cannot contain any whitespace escape sequences.";

	private InsuranceToFamiliesHelper() {
	}

	public static InsuranceDatabase getInsuranceDatabase(InsuranceClient insuranceClient) {
		return insuranceClient.eContainer() instanceof InsuranceDatabase database ? database : null;
	}

	public static String getLastName(InsuranceClient insuranceClient) {
		String[] nameParts = insuranceClient.getName().split(" ");
		return nameParts[nameParts.length - 1];
	}

	public static String getFirstName(InsuranceClient insuranceClient) {
		String[] nameParts = insuranceClient.getName().split(" ");
		return nameParts.length == 0 ? null : nameParts[0];
	}

	public static Function1<Family, Boolean> sameLastName(InsuranceClient insuranceClient) {
		String newPersonLastname = getLastName(insuranceClient);
		return (Family family) -> family.getLastName().equals(newPersonLastname);
	}

	public static Function1<Family, Boolean> noParent(InsuranceClient insuranceClient) {
		return (Family family) -> insuranceClient.getGender() == Gender.MALE
			? family.getFather() == null
			: family.getMother() == null;
	}

	public static void informUserAboutReplacementOfClient(UserInteractor userInteractor, InsuranceClient insuranceClient,
		Family oldFamily) {
		String message = "Insurance Client " + insuranceClient.getName()
			+ " has been replaced by another insurance client in his family (" + oldFamily.getLastName()
			+ "). Please decide in which family and role " + insuranceClient.getName() + " should be.";

		userInteractor.getNotificationDialogBuilder().message(message)
			.title("Insurance Client has been replaced in his original family").startInteraction();
	}

	public static PositionPreference askUserWhetherClientIsParentOrChild(UserInteractor userInteractor,
		InsuranceClient insuranceClient) {
		String parentOrChildMessage = "You have inserted " + insuranceClient.getName()
			+ " into the insurance database which results into the creation of a corresponding member into the family register. Is this member supposed to be a parent or a child in the family register?";

		List<String> parentOrChildOptions = List.of("Parent", "Child");

		int parentOrChildSelection = userInteractor
			.getSingleSelectionDialogBuilder()
			.message(parentOrChildMessage)
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction();

		return parentOrChildSelection == parentOrChildOptions.indexOf("Child") ? PositionPreference.Child : PositionPreference.Parent;
	}

	public static Family askUserWhichFamilyToInsertTheMemberIn(UserInteractor userInteractor, InsuranceClient newClient,
		Iterable<Family> selectableFamilies) {
		String whichFamilyMessage = "Please choose whether you want to create a new family or insert  "
			+ newClient.getName() + " into one of the existing families.";

		// Prepare options to select from
		Collection<String> whichFamilyOptions = new ArrayList<>();
		whichFamilyOptions.add("insert in a new family");
		for (Family family : selectableFamilies) {
			whichFamilyOptions.add(stringifyFamily(family));
		}

		// Start interaction
		int whichFamilyIndex = userInteractor
			.getSingleSelectionDialogBuilder()
			.message(whichFamilyMessage)
			.choices(whichFamilyOptions)
			.title("New or Existing Family?")
			.windowModality(WindowModality.MODAL)
			.startInteraction();

		if (whichFamilyIndex == 0) {
			return null;
		}
		List<Family> selectableFamilyList = new ArrayList<>();
		selectableFamilies.forEach(selectableFamilyList::add);
		return selectableFamilyList.get(whichFamilyIndex - 1);
	}

	public static PositionPreference askUserWhetherClientIsParentOrChildDuringRenaming(UserInteractor userInteractor,
		String oldFullname, String newFullname, boolean wasChildBefore) {
		String parentOrChildMessage = "You have renamed " + oldFullname + " to " + newFullname
			+ ", which might cause the corresponding member in the families model to change its position inside a family. Which position should this member, who was a\t"
			+ (wasChildBefore ? "child" : "parent") + " before, have after the renaming?";

		List<String> parentOrChildOptions = List.of("Parent", "Child");

		int parentOrChildSelection = userInteractor
			.getSingleSelectionDialogBuilder()
			.message(parentOrChildMessage)
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction();

		return parentOrChildSelection == parentOrChildOptions.indexOf("Child") ? PositionPreference.Child : PositionPreference.Parent;
	}

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

	/**Checks if a insurance clients name is <code>null</code>, empty or contains escape sequences.
	 * @param insuranceClient The insurance client of which the name has to be valid.
	 * @throws IllegalStateException if the insurance clients name in invalid.
	 */
	public static void assertValidName(InsuranceClient insuranceClient) {
		if (insuranceClient.getName() == null) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_NULL);
		} else if (insuranceClient.getName().trim().isEmpty()) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE);
		} else if (insuranceClient.getName().contains("\n") || insuranceClient.getName().contains("\t")
			|| insuranceClient.getName().contains("\r")) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES);
		}
	}

}
