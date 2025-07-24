package tools.vitruv.applications.cbs.testutils.equivalencetest

import org.junit.jupiter.api.^extension.ExtensionContext
import org.junit.jupiter.api.^extension.ExtensionContext.Namespace
import java.util.Map
import java.util.Optional
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.HashMap
import java.util.function.Function
import org.junit.jupiter.api.^extension.ExtensionContextException
import org.junit.jupiter.api.^extension.ExtensionContext.Store.CloseableResource
import org.junit.jupiter.api.^extension.MediaType
import org.junit.jupiter.api.^function.ThrowingConsumer
import java.nio.file.Path
import org.junit.platform.engine.support.hierarchical.ThrowableCollector
import org.opentest4j.TestAbortedException
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor

@FinalFieldsConstructor
package class EquivalenceTestExtensionContext implements ExtensionContext {
	val String name
	val int executionIndex
	val ExtensionContext parentContext
	val MetamodelDescriptor testMetamodel
	@Accessors
	var Optional<Throwable> executionException = Optional.empty
	val namespaceStores = new HashMap<ExtensionContext.Namespace, NamespaceStore>

	package new(String name, int executionIndex, ExtensionContext parentContext) {
		this(name, executionIndex, parentContext, null)
	}

	override getConfigurationParameter(String key) {
		parentContext.getConfigurationParameter(key)
	}

	override <T> getConfigurationParameter(String key, Function<String, T> transformer) {
		parentContext.getConfigurationParameter(key, transformer)
	}

	override getDisplayName() {
		name
	}

	override getElement() {
		parentContext.element
	}
	
	override getExecutionMode() {
		parentContext.executionMode
	}
	

	override getParent() {
		Optional.of(parentContext)
	}

	override getRoot() {
		parentContext.root
	}

	override getStore(Namespace namespace) {
		namespaceStores.computeIfAbsent(namespace)[new NamespaceStore]
	}

	override getTags() {
		if (testMetamodel === null) parentContext.tags else (parentContext.tags + #[testMetamodel.name]).toSet
	}

	override getTestClass() {
		parentContext.testClass
	}

	override getTestInstance() {
		parentContext.testInstance
	}

	override getTestInstanceLifecycle() {
		parentContext.testInstanceLifecycle
	}

	override getTestInstances() {
		parentContext.testInstances
	}

	override getTestMethod() {
		parentContext.testMethod
	}

	override getExecutableInvoker() {
		parentContext.executableInvoker
	}

	override getUniqueId() {
		'''«parentContext.uniqueId»-«executionIndex»'''
	}

	override publishReportEntry(Map<String, String> map) {
		throw new UnsupportedOperationException('''Reporting is not supported for «this.class.simpleName»!''')
	}

	override publishFile(String name, MediaType mediaType, ThrowingConsumer<Path> action) {
		throw new UnsupportedOperationException()
	}

	override publishDirectory(String name, ThrowingConsumer<Path> action) {
		throw new UnsupportedOperationException()
	}

	override getEnclosingTestClasses() {
		throw new UnsupportedOperationException()
	}

	def package void close() {
		val throwableCollector = new ThrowableCollector[it instanceof TestAbortedException]
		// make a snapshot before iterating in case some concurrent modification happens
		namespaceStores.values.toList.forEach[it.close(throwableCollector)]
		throwableCollector.assertEmpty()
	}

	private static class NamespaceStore implements Store {
		val values = new HashMap<Object, Object>()

		override get(Object key) {
			values.get(key)
		}

		override <V> get(Object key, Class<V> requiredType) {
			checkResultType(key, get(key), requiredType)
		}

		override <K, V> getOrComputeIfAbsent(K key, Function<K, V> defaultCreator) {
			(values as Map<K, Object>).computeIfAbsent(key, defaultCreator)
		}

		override <K, V> getOrComputeIfAbsent(K key, Function<K, V> defaultCreator, Class<V> requiredType) {
			checkResultType(key, getOrComputeIfAbsent(key, defaultCreator), requiredType)
		}

		override put(Object key, Object value) {
			values.put(key, value)
		}

		override remove(Object key) {
			values.remove(key)
		}

		override <V> remove(Object key, Class<V> requiredType) {
			checkResultType(key, remove(key), requiredType)
		}

		def private <V> checkResultType(Object key, Object value, Class<V> requiredType) {
			if (value === null) return null
			if (!requiredType.isInstance(value)) {
				throw new ExtensionContextException('''Object stored under key [«key»] is not of required type [«requiredType»]''')
			}
			return value as V
		}

		def private close(ThrowableCollector throwableCollector) {
			// make a snapshot before iterating in case some concurrent modification happens
			values.values.filter(CloseableResource).toList.forEach [ resource |
				throwableCollector.execute[resource.close()]
			]
		}
	}
}
