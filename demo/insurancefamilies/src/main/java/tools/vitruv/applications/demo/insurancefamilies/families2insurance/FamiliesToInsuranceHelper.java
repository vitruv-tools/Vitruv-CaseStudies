package tools.vitruv.applications.demo.insurancefamilies.families2insurance;

import static edu.kit.ipd.sdq.metamodels.families.FamiliesUtil.getFamily;

import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.insurance.Gender;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient;

public final class FamiliesToInsuranceHelper {

	public static final String EXCEPTION_MESSAGE_FIRSTNAME_NULL = "A member's firstname is not allowed to be null.";
	public static final String EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE = "A member's firstname has to contain at least one non-whitespace character.";
	public static final String EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES = "A member's firstname cannot contain any whitespace escape sequences.";

	private FamiliesToInsuranceHelper() {
	}

	/** Returns the name of an InsuranceClient corresponding to a Member.
	 *
	 *  @param member Member from which a corresponding Insurance Client should be given a correct name.
	 *  @return Name that the corresponding Insurance Client from the insurance model should have.
	 */
	public static String getInsuranceClientName(Member member) {
		StringBuilder name = new StringBuilder();
		name.append(member.getFirstName());
		if (getFamily(member).getLastName() != null && !getFamily(member).getLastName().isEmpty()) {
			name.append(" " + getFamily(member).getLastName());
		}

		return name.toString();
	}

	/**Checks if a members firstname is <code>null</code>, empty or contains escape sequences.
	 * @param member The member whose firstname is checked
	 * @throws IllegalArgumentException if firstname is not valid
	 */
	public static void assertValidFirstname(Member member) {
		if (member.getFirstName() == null) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_NULL);
		} else if (member.getFirstName().trim().isEmpty()) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE);
		} else if (member.getFirstName().contains("\n") || member.getFirstName().contains("\t")
			|| member.getFirstName().contains("\r")) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES);
		}
	}

	/**Checks if a InsuranceClient has the expected gender and throws an exception if not.
	 * @param insuranceClient The Insurance Client which is supposed to be of the expected gender.
	 * @param expectedGender The expected Gender of the insuranceClient
	 * @throws UnsupportedOperationException if the insuranceClient is not of the expected gender.
	 */
	public static void assertGender(InsuranceClient insuranceClient, Gender expectedGender) {
		if (insuranceClient.getGender() != expectedGender) {
			String expectedGenderString = expectedGender == Gender.MALE ? "male" : "female";

			throw new UnsupportedOperationException("The position of a " + expectedGenderString
				+ " family member can only be assigned to members with no or a " + expectedGenderString
				+ " corresponding insurance client.");
		}
	}

	public static Gender isMaleToGender(Boolean isMale) {
		return isMale ? Gender.MALE : Gender.FEMALE;
	}
}
