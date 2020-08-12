package tools.vitruv.applications.cbs.commonalities.tests.util

/**
 * Provides methods to create and check the domain model for a specific test
 * case.
 */
interface DomainModel {

	/**
	 * Creates and synchronizes the (source) model.
	 */
	def void createAndSynchronize()

	/**
	 * Checks if the (target) model exists and matches the expected model.
	 */
	def void check()
}
