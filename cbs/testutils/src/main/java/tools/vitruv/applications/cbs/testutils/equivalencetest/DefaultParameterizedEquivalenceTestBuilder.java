package tools.vitruv.applications.cbs.testutils.equivalencetest;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.extension.ExtensionContext;
import tools.vitruv.applications.cbs.testutils.ModelComparisonSettings;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.views.UriMode;

class DefaultParameterizedEquivalenceTestBuilder implements ParameterizedEquivalenceTestBuilder {
	private final DefaultParametersBuilder parametersBuilder = new DefaultParametersBuilder();
	private final ExtensionContext templateMethodContext;
	private final Collection<ChangePropagationSpecification> changePropagationSpecifications;
	private final UriMode uriMode;
	private final ModelComparisonSettings modelComparisonSettings;

	DefaultParameterizedEquivalenceTestBuilder(ExtensionContext templateMethodContext,
			Collection<ChangePropagationSpecification> changePropagationSpecifications, UriMode uriMode,
			ModelComparisonSettings modelComparisonSettings) {
		this.templateMethodContext = templateMethodContext;
		this.changePropagationSpecifications = changePropagationSpecifications;
		this.uriMode = uriMode;
		this.modelComparisonSettings = modelComparisonSettings;
	}

	@Override
	public <T> Parameter1<? extends T> parameter(T p) {
		return parametersBuilder.parameter(p);
	}

	@Override
	public <T> Parameter1<? extends T> parameter(T p, Supplier<String> name) {
		return parametersBuilder.parameter(p, name);
	}

	@Override
	public <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2) {
		return parametersBuilder.parameters(p1, p2);
	}

	@Override
	public <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2, Supplier<String> name) {
		return parametersBuilder.parameters(p1, p2, name);
	}

	@Override
	public <T> Iterable<? extends DynamicNode> parameterizedBy(List<? extends Parameter1<? extends T>> parameters,
			BiConsumer<EquivalenceTestBuilder, T> testConstructor) {
		return IntStream.range(0, parameters.size())
				.<DynamicNode>mapToObj(index -> {
					Parameter1<? extends T> parameter = parameters.get(index);
					DefaultEquivalenceTestBuilder builder = newBuilder(index, parameter.getName());
					testConstructor.accept(builder, parameter.getP());
					return dynamicContainer(parameter.getName(), builder.testsThatStepsAreEquivalent());
				})
				.toList();
	}

	@Override
	public <T> Iterable<? extends DynamicNode> parameterizedBy(List<? extends Parameter1<? extends T>> parameters,
			Function<T, String> nameTemplate, BiConsumer<EquivalenceTestBuilder, T> testConstructor) {
		List<Parameter1<? extends T>> namedParameters = parameters.stream()
				.<Parameter1<? extends T>>map(parameter -> parameter.hasExplicitName() ? parameter
						: parametersBuilder.withName(parameter, nameTemplate.apply(parameter.getP())))
				.toList();
		return parameterizedBy(namedParameters, testConstructor);
	}

	@Override
	public <T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
			List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
			TriConsumer<EquivalenceTestBuilder, T1, T2> testConstructor) {
		return IntStream.range(0, parameters.size())
				.<DynamicNode>mapToObj(index -> {
					Parameter2<? extends T1, ? extends T2> parameter = parameters.get(index);
					DefaultEquivalenceTestBuilder builder = newBuilder(index, parameter.getName());
					testConstructor.accept(builder, parameter.getP1(), parameter.getP2());
					return dynamicContainer(parameter.getName(), builder.testsThatStepsAreEquivalent());
				})
				.toList();
	}

	@Override
	public <T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
			List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
			BiFunction<T1, T2, String> nameTemplate,
			TriConsumer<EquivalenceTestBuilder, T1, T2> testConstructor) {
		List<Parameter2<? extends T1, ? extends T2>> namedParameters = parameters.stream()
				.<Parameter2<? extends T1, ? extends T2>>map(parameter -> parameter.hasExplicitName() ? parameter
						: parametersBuilder.withName(parameter,
								nameTemplate.apply(parameter.getP1(), parameter.getP2())))
				.toList();
		return parameterizedBy(namedParameters, testConstructor);
	}

	@Override
	public Iterable<? extends DynamicNode> dependsOn(
			Function<ParameterizedEquivalenceTestBuilder, Iterable<? extends DynamicNode>> otherTest,
			Consumer<EquivalenceTestBuilder> testConstructor) {
		return otherTest.apply(new DependencyBuilder(this, testConstructor));
	}

	private DefaultEquivalenceTestBuilder newBuilder(int index, String name) {
		EquivalenceTestExtensionContext context = new EquivalenceTestExtensionContext(name, index,
				templateMethodContext);
		return new DefaultEquivalenceTestBuilder(context, changePropagationSpecifications, uriMode,
				modelComparisonSettings);
	}

	private static class DefaultParametersBuilder implements ParametersBuilder {
		@Override
		public <T> Parameter1<? extends T> parameter(T p, Supplier<String> name) {
			return new P<>(name.get(), p);
		}

		@Override
		public <T> Parameter1<? extends T> parameter(T p) {
			return new P<>(null, p);
		}

		@Override
		public <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2, Supplier<String> name) {
			return new P2<>(name.get(), p1, p2);
		}

		@Override
		public <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2) {
			return new P2<>(null, p1, p2);
		}

		private <T> Parameter1<T> withName(Parameter1<T> parameter, String name) {
			return new P<>(name, parameter.getP());
		}

		private <T1, T2> Parameter2<T1, T2> withName(Parameter2<T1, T2> parameter, String name) {
			return new P2<>(name, parameter.getP1(), parameter.getP2());
		}

		private static class ParameterWithDefaultName implements NamedParameter {
			private final String name;

			ParameterWithDefaultName(String name) {
				this.name = name;
			}

			@Override
			public String getName() {
				return name != null ? name : this.toString();
			}

			@Override
			public boolean hasExplicitName() {
				return name != null;
			}
		}

		private static class P<T> extends ParameterWithDefaultName implements Parameter1<T> {
			private final T p;

			P(String name, T p) {
				super(name);
				this.p = p;
			}

			@Override
			public T getP() {
				return p;
			}

			@Override
			public String toString() {
				return "(" + p + ")";
			}
		}

		private static class P2<T1, T2> extends ParameterWithDefaultName implements Parameter2<T1, T2> {
			private final T1 p1;
			private final T2 p2;

			P2(String name, T1 p1, T2 p2) {
				super(name);
				this.p1 = p1;
				this.p2 = p2;
			}

			@Override
			public T1 getP1() {
				return p1;
			}

			@Override
			public T2 getP2() {
				return p2;
			}

			@Override
			public String toString() {
				return "(" + p1 + ", " + p2 + ")";
			}
		}
	}

	private static class DependencyBuilder implements ParameterizedEquivalenceTestBuilder {
		private final ParameterizedEquivalenceTestBuilder targetBuilder;
		private final Consumer<EquivalenceTestBuilder> testConstructor;

		DependencyBuilder(ParameterizedEquivalenceTestBuilder targetBuilder,
				Consumer<EquivalenceTestBuilder> testConstructor) {
			this.targetBuilder = targetBuilder;
			this.testConstructor = testConstructor;
		}

		@Override
		public <T> Parameter1<? extends T> parameter(T p) {
			return targetBuilder.parameter(p);
		}

		@Override
		public <T> Parameter1<? extends T> parameter(T p, Supplier<String> name) {
			return targetBuilder.parameter(p, name);
		}

		@Override
		public <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2) {
			return targetBuilder.parameters(p1, p2);
		}

		@Override
		public <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2, Supplier<String> name) {
			return targetBuilder.parameters(p1, p2, name);
		}

		@Override
		public <T> Iterable<? extends DynamicNode> parameterizedBy(
				List<? extends Parameter1<? extends T>> parameters,
				BiConsumer<EquivalenceTestBuilder, T> dependencyConstructor) {
			return targetBuilder.<T>parameterizedBy(parameters, (builder, p) -> {
				builder.dependsOn(sub -> dependencyConstructor.accept(sub, p));
				testConstructor.accept(builder);
			});
		}

		@Override
		public <T> Iterable<? extends DynamicNode> parameterizedBy(
				List<? extends Parameter1<? extends T>> parameters,
				Function<T, String> nameTemplate,
				BiConsumer<EquivalenceTestBuilder, T> dependencyConstructor) {
			return targetBuilder.<T>parameterizedBy(parameters, nameTemplate, (builder, p) -> {
				builder.dependsOn(sub -> dependencyConstructor.accept(sub, p));
				testConstructor.accept(builder);
			});
		}

		@Override
		public <T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
				List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
				TriConsumer<EquivalenceTestBuilder, T1, T2> dependencyConstructor) {
			return targetBuilder.<T1, T2>parameterizedBy(parameters, (builder, p1, p2) -> {
				builder.dependsOn(sub -> dependencyConstructor.accept(sub, p1, p2));
				testConstructor.accept(builder);
			});
		}

		@Override
		public <T1, T2> Iterable<? extends DynamicNode> parameterizedBy(
				List<? extends Parameter2<? extends T1, ? extends T2>> parameters,
				BiFunction<T1, T2, String> nameTemplate,
				TriConsumer<EquivalenceTestBuilder, T1, T2> dependencyConstructor) {
			return targetBuilder.<T1, T2>parameterizedBy(parameters, nameTemplate, (builder, p1, p2) -> {
				builder.dependsOn(sub -> dependencyConstructor.accept(sub, p1, p2));
				testConstructor.accept(builder);
			});
		}

		@Override
		public Iterable<? extends DynamicNode> dependsOn(
				Function<ParameterizedEquivalenceTestBuilder, Iterable<? extends DynamicNode>> otherTest,
				Consumer<EquivalenceTestBuilder> testConstructor) {
			return otherTest.apply(new DependencyBuilder(this, testConstructor));
		}
	}
}
