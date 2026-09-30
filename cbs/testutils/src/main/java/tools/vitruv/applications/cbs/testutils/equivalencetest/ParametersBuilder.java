package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.util.function.Supplier;

public interface ParametersBuilder {
	<T> Parameter1<? extends T> parameter(T p);

	<T> Parameter1<? extends T> parameter(T p, Supplier<String> name);

	<T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2);

	<T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2, Supplier<String> name);

	interface NamedParameter {
		String getName();

		boolean hasExplicitName();
	}

	interface Parameter1<T> extends NamedParameter {
		T getP();
	}

	interface Parameter2<T1, T2> extends NamedParameter {
		T1 getP1();

		T2 getP2();
	}
}
