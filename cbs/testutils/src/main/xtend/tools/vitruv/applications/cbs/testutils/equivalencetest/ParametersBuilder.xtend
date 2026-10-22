package tools.vitruv.applications.cbs.testutils.equivalencetest

interface ParametersBuilder {
	def <T> Parameter1<? extends T> parameter(T p)

	def <T> Parameter1<? extends T> parameter(T p, ()=>String name)

	def <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2)

	def <T1, T2> Parameter2<T1, T2> parameters(T1 p1, T2 p2, ()=>String name)

	interface NamedParameter {
		def String getName()
		def boolean hasExplicitName()
	}

	interface Parameter1<T> extends NamedParameter {
		def T getP()
	}

	interface Parameter2<T1, T2> extends NamedParameter {
		def T1 getP1()

		def T2 getP2()
	}
}
