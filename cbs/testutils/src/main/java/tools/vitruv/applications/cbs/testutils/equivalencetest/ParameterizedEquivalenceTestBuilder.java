package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.DynamicNode;

/**
 * Builder for parameterized {@linkplain EquivalenceTestBuilder equivalence tests}.
 */
public interface ParameterizedEquivalenceTestBuilder extends ParametersBuilder {
	<T> Iterable<? extends DynamicNode> parameterizedBy(
			List<? extends Parameter1<? extends T>> parameters,
			BiConsumer<EquivalenceTestBuilder, T> testConstructor);

	<T> Iterable<? extends DynamicNode> parameterizedBy(
			List<? extends Parameter1<? extends T>> parameters,
			Function<T, String> nameTemplate,
			BiConsumer<EquivalenceTestBuilder, T> testConstructor);

	<T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
			List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
			TriConsumer<EquivalenceTestBuilder, T1, T2> testConstructor);

	<T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
			List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
			BiFunction<T1, T2, String> nameTemplate,
			TriConsumer<EquivalenceTestBuilder, T1, T2> testConstructor);

	Iterable<? extends DynamicNode> dependsOn(
			Function<ParameterizedEquivalenceTestBuilder, Iterable<? extends DynamicNode>> otherTest,
			Consumer<EquivalenceTestBuilder> testConstructor);

	@FunctionalInterface
	interface TriConsumer<A, B, C> {
		void accept(A a, B b, C c);
	}
}
