package tools.vitruv.applications.cbs.testutils.equivalencetest

import java.util.List
import org.junit.jupiter.api.DynamicNode

/**
 * Builder for parameterized {@linkplain EquivalenceTestBuilder equivalence tests}. 
 */
interface ParameterizedEquivalenceTestBuilder extends ParametersBuilder {
	// these horrible declarations are one of the many reasons why you want to use Kotlin.
	def <T> Iterable<? extends DynamicNode> parameterizedBy(
		List<? extends Parameter1<? extends T>> parameters,
		(EquivalenceTestBuilder, T)=>void testConstructor
	)

	def <T> Iterable<? extends DynamicNode> parameterizedBy(
		List<? extends Parameter1<? extends T>> parameters,
		(T)=>String nameTemplate,
		(EquivalenceTestBuilder, T)=>void testConstructor
	)

	def <T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
		List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
		(EquivalenceTestBuilder, T1, T2)=>void testConstructor
	)

	def <T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
		List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
		(T1, T2)=>String nameTemplate,
		(EquivalenceTestBuilder, T1, T2)=>void testConstructor
	)

	def Iterable<? extends DynamicNode> dependsOn(
		(ParameterizedEquivalenceTestBuilder)=>Iterable<? extends DynamicNode> otherTest,
		(EquivalenceTestBuilder)=>void testConstructor
	)
}
