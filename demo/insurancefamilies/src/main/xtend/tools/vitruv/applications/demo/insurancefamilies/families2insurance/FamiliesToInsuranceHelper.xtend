package tools.vitruv.applications.demo.insurancefamilies.families2insurance

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.insurance.Gender
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceClient

import static extension edu.kit.ipd.sdq.metamodels.families.FamiliesUtil.getFamily

@Utility
class FamiliesToInsuranceHelper {
	
	public final static String EXCEPTION_MESSAGE_FIRSTNAME_NULL = "A member's firstname is not allowed to be null."
	public final static String EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE = "A member's firstname has to contain at least one non-whitespace character."
	public final static String EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES = "A member's firstname cannot contain any whitespace escape sequences."

	/** Returns the name of an InsuranceClient corresponding to a Member.
	 *
	 *  @param member Member from which a corresponding Insurance Client should be given a correct name.
	 *  @return Name that the corresponding Insurance Client from the insurance model should have.
	 */
	def static String getInsuranceClientName(Member member) {
		val name = new StringBuilder()
		name.append(member.firstName)
		if(member.family.lastName !== null && !member.family.lastName.empty){
			name.append(" " + member.family.lastName)
		}
		
		return name.toString()
	}
	
	/**Checks if a members firstname is <code>null</code>, empty or contains escape sequences.
	 * @param member The member whose firstname is checked
	 * @throws IllegalArgumentException if firstname is not valid
	 */
	def static void assertValidFirstname(Member member) {
		if (member.firstName === null) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_NULL)
		} else if (member.firstName.trim.empty) {
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE)
		} else if (member.firstName.contains("\n") || member.firstName.contains("\t") || member.firstName.contains("\r")){
			throw new IllegalStateException(EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		}
	}
	
	/**Checks if a InsuranceClient has the expected gender and throws an exception if not.
	 * @param insuranceClient The Insurance Client which is supposed to be of the expected gender.
	 * @param expectedGender The expected Gender of the insuranceClient
	 * @throws UnsupportedOperationException if the insuranceClient is not of the expected gender.
	 */
	def static void assertGender(InsuranceClient insuranceClient, Gender expectedGender) {
		if (!(insuranceClient.gender === expectedGender)) {
			val expectedGenderString = expectedGender === Gender.MALE ? "male" : "female"
			
			throw new UnsupportedOperationException(
				'''The position of a «expectedGenderString» family member can only be assigned to members with no or a «expectedGenderString» corresponding insurance client.'''
			)
		}
	}
	
	def static Gender isMaleToGender(Boolean isMale) {
		 isMale ? Gender.MALE : Gender.FEMALE
	}
}
