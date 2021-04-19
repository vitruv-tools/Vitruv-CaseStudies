package tools.vitruv.applications.cbs.testutils.equivalencetest

import java.util.List
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.^extension.ExtensionContext
import tools.vitruv.testutils.UriMode
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder.NamedParameter
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder.Parameter1
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder.Parameter2
import org.eclipse.xtend.lib.annotations.Delegate
import org.junit.jupiter.api.DynamicNode
import java.util.Collection
import tools.vitruv.framework.propagation.ChangePropagationSpecification

@FinalFieldsConstructor
package class DefaultParameterizedEquivalenceTestBuilder implements ParameterizedEquivalenceTestBuilder {
	@Delegate
	extension val DefaultParametersBuilder parametersBuilder = new DefaultParametersBuilder
	val ExtensionContext templateMethodContext
	val Collection<ChangePropagationSpecification> changePropagationSpecifications
	val UriMode uriMode
	val ModelComparisonSettings modelComparisonSettings

	override <T> parameterizedBy(
		List<? extends Parameter1<? extends T>> parameters,
		(EquivalenceTestBuilder, T)=>void testConstructor
	) {
		parameters.indexed.map [
			val index = key
			extension val parameter = value
			val builder = newBuilder(index, name)
			testConstructor.apply(builder, p)
			dynamicContainer(name, builder.testsThatStepsAreEquivalent)
		]
	}

	override <T> parameterizedBy(
		List<? extends Parameter1<? extends T>> parameters,
		(T)=>String nameTemplate,
		(EquivalenceTestBuilder, T)=>void testConstructor
	) {
		parameterizedBy(parameters.map[if (!hasExplicitName) withName(nameTemplate.apply(p)) else it], testConstructor)
	}

	override <T1, T2> parameterizedBy(
		List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
		(EquivalenceTestBuilder, T1, T2)=>void testConstructor
	) {
		parameters.indexed.map [
			val index = key
			extension val parameter = value
			val builder = newBuilder(index, name)
			testConstructor.apply(builder, p1, p2)
			dynamicContainer(name, builder.testsThatStepsAreEquivalent)
		]
	}

	override <T1, T2> parameterizedBy(
		List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
		(T1, T2)=>String nameTemplate,
		(EquivalenceTestBuilder, T1, T2)=>void testConstructor
	) {
		parameterizedBy(parameters.map[if (!hasExplicitName) withName(nameTemplate.apply(p1, p2)) else it],
			testConstructor)
	}

	override dependsOn((ParameterizedEquivalenceTestBuilder)=>Iterable<? extends DynamicNode> otherTest,
		(EquivalenceTestBuilder)=>void testConstructor) {
		otherTest.apply(new DependencyBuilder(this, testConstructor))
	}

	private def newBuilder(int index, String name) {
		val context = new EquivalenceTestExtensionContext(name, index, templateMethodContext)
		new DefaultEquivalenceTestBuilder(context, changePropagationSpecifications, uriMode, modelComparisonSettings)
	}

	private static class DefaultParametersBuilder implements ParametersBuilder {
		override <T> parameter(T p, ()=>String name) {
			new P(name.apply(), p)
		}

		override <T> parameter(T p) {
			new P(null, p)
		}

		override <T1, T2> parameters(T1 p1, T2 p2, ()=>String name) {
			new P2(name.apply(), p1, p2)
		}

		override <T1, T2> parameters(T1 p1, T2 p2) {
			new P2(null, p1, p2)
		}

		def private <T> Parameter1<T> withName(Parameter1<T> parameter, String name) {
			new P(name, parameter.p)
		}

		def private <T1, T2> Parameter2<T1, T2> withName(Parameter2<T1, T2> parameter, String name) {
			new P2(name, parameter.p1, parameter.p2)
		}

		@FinalFieldsConstructor
		private static class ParameterWithDefaultName implements NamedParameter {
			val String name

			override String getName() { name ?: this.toString() }

			override hasExplicitName() { name !== null }
		}

		@FinalFieldsConstructor
		@Accessors
		private static class P<T> extends ParameterWithDefaultName implements Parameter1<T> {
			val T p

			override toString() { '''(«p»)''' }
		}

		@FinalFieldsConstructor
		@Accessors
		private static class P2<T1, T2> extends ParameterWithDefaultName implements Parameter2<T1, T2> {
			val T1 p1
			val T2 p2

			override toString() { '''(«p1», «p2»)''' }
		}

	}

	@FinalFieldsConstructor
	private static class DependencyBuilder implements ParameterizedEquivalenceTestBuilder {
		@Delegate
		val ParameterizedEquivalenceTestBuilder targetBuilder
		val (EquivalenceTestBuilder)=>void testConstructor

		override <T> parameterizedBy(
			List<? extends Parameter1<? extends T>> parameters,
			(EquivalenceTestBuilder, T)=>void dependencyConstructor
		) {
			targetBuilder.parameterizedBy(parameters) [ builder, p |
				builder.dependsOn[sub|dependencyConstructor.apply(sub, p)]
				testConstructor.apply(builder)
			]
		}

		override <T> parameterizedBy(
			List<? extends Parameter1<? extends T>> parameters,
			(T)=>String nameTemplate,
			(EquivalenceTestBuilder, T)=>void dependencyConstructor
		) {
			targetBuilder.parameterizedBy(parameters, nameTemplate) [ builder, p |
				builder.dependsOn[sub|dependencyConstructor.apply(sub, p)]
				testConstructor.apply(builder)
			]
		}

		override <T1, T2> parameterizedBy(List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
			(EquivalenceTestBuilder, T1, T2)=>void dependencyConstructor) {
			targetBuilder.parameterizedBy(parameters) [ builder, p1, p2 |
				builder.dependsOn[sub|dependencyConstructor.apply(sub, p1, p2)]
				testConstructor.apply(builder)
			]
		}

		override <T1, T2> parameterizedBy(
			List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
			(T1, T2)=>String nameTemplate,
			(EquivalenceTestBuilder, T1, T2)=>void dependencyConstructor
		) {
			targetBuilder.parameterizedBy(parameters, nameTemplate) [ builder, p1, p2 |
				builder.dependsOn[sub|dependencyConstructor.apply(sub, p1, p2)]
				testConstructor.apply(builder)
			]
		}

		override dependsOn((ParameterizedEquivalenceTestBuilder)=>Iterable<? extends DynamicNode> otherTest,
			(EquivalenceTestBuilder)=>void testConstructor) {
			otherTest.apply(new DependencyBuilder(this, testConstructor))
		}
	}
}
