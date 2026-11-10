package tools.vitruv.applications.cbs.testutils.equivalencetest;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExecutableInvoker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContextException;
import org.junit.jupiter.api.extension.TestInstances;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;
import org.opentest4j.TestAbortedException;

import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;

/**
 * The {@link ExtensionContext} of a single dynamic equivalence test. Delegates everything but the display name, the
 * unique ID, the tags and the stores to the context of the test factory method.
 */
class EquivalenceTestExtensionContext implements ExtensionContext {
    private final String name;
    private final int executionIndex;
    private final ExtensionContext parentContext;
    private final MetamodelDescriptor testMetamodel;
    private Optional<Throwable> executionException = Optional.empty();
    private final Map<Namespace, NamespaceStore> namespaceStores = new HashMap<>();

    EquivalenceTestExtensionContext(String name, int executionIndex, ExtensionContext parentContext) {
        this(name, executionIndex, parentContext, null);
    }

    EquivalenceTestExtensionContext(String name, int executionIndex, ExtensionContext parentContext,
            MetamodelDescriptor testMetamodel) {
        this.name = name;
        this.executionIndex = executionIndex;
        this.parentContext = parentContext;
        this.testMetamodel = testMetamodel;
    }

    @Override
    public Optional<String> getConfigurationParameter(String key) {
        return parentContext.getConfigurationParameter(key);
    }

    @Override
    public <T> Optional<T> getConfigurationParameter(String key, Function<? super String, ? extends T> transformer) {
        return parentContext.getConfigurationParameter(key, transformer);
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public Optional<AnnotatedElement> getElement() {
        return parentContext.getElement();
    }

    @Override
    public ExecutionMode getExecutionMode() {
        return parentContext.getExecutionMode();
    }

    @Override
    public Optional<ExtensionContext> getParent() {
        return Optional.of(parentContext);
    }

    @Override
    public ExtensionContext getRoot() {
        return parentContext.getRoot();
    }

    @Override
    public Store getStore(Namespace namespace) {
        return namespaceStores.computeIfAbsent(namespace, ignored -> new NamespaceStore());
    }

    /**
     * Store scopes did not exist when this context was written. All scopes share the stores of this context.
     */
    @Override
    public Store getStore(StoreScope scope, Namespace namespace) {
        return getStore(namespace);
    }

    @Override
    public Set<String> getTags() {
        if (testMetamodel == null) {
            return parentContext.getTags();
        }
        Set<String> tags = new LinkedHashSet<>(parentContext.getTags());
        tags.add(testMetamodel.getName());
        return tags;
    }

    @Override
    public Optional<Class<?>> getTestClass() {
        return parentContext.getTestClass();
    }

    @Override
    public Optional<Object> getTestInstance() {
        return parentContext.getTestInstance();
    }

    @Override
    public Optional<Lifecycle> getTestInstanceLifecycle() {
        return parentContext.getTestInstanceLifecycle();
    }

    @Override
    public Optional<TestInstances> getTestInstances() {
        return parentContext.getTestInstances();
    }

    @Override
    public Optional<Method> getTestMethod() {
        return parentContext.getTestMethod();
    }

    @Override
    public ExecutableInvoker getExecutableInvoker() {
        return parentContext.getExecutableInvoker();
    }

    @Override
    public String getUniqueId() {
        return parentContext.getUniqueId() + "-" + executionIndex;
    }

    @Override
    public Optional<Throwable> getExecutionException() {
        return executionException;
    }

    void setExecutionException(Optional<Throwable> executionException) {
        this.executionException = executionException;
    }

    @Override
    public void publishReportEntry(Map<String, String> map) {
        throw new UnsupportedOperationException(
                "Reporting is not supported for " + getClass().getSimpleName() + "!");
    }

    @Override
    public void publishFile(String name, MediaType mediaType, ThrowingConsumer<Path> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void publishDirectory(String name, ThrowingConsumer<Path> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Class<?>> getEnclosingTestClasses() {
        throw new UnsupportedOperationException();
    }

    void close() {
        ThrowableCollector throwableCollector = new ThrowableCollector(TestAbortedException.class::isInstance);
        // make a snapshot before iterating in case some concurrent modification happens
        List.copyOf(namespaceStores.values()).forEach(store -> store.close(throwableCollector));
        throwableCollector.assertEmpty();
    }

    private static class NamespaceStore implements Store {
        private final Map<Object, Object> values = new HashMap<>();

        @Override
        public Object get(Object key) {
            return values.get(key);
        }

        @Override
        public <V> V get(Object key, Class<V> requiredType) {
            return checkResultType(key, get(key), requiredType);
        }

        @Override
        public <K, V> Object getOrComputeIfAbsent(K key, Function<? super K, ? extends V> defaultCreator) {
            return computeIfAbsent(key, defaultCreator);
        }

        @Override
        public <K, V> V getOrComputeIfAbsent(K key, Function<? super K, ? extends V> defaultCreator,
                Class<V> requiredType) {
            return computeIfAbsent(key, defaultCreator, requiredType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <K, V> Object computeIfAbsent(K key, Function<? super K, ? extends V> defaultCreator) {
            return values.computeIfAbsent(key, ignored -> defaultCreator.apply((K) key));
        }

        @Override
        public <K, V> V computeIfAbsent(K key, Function<? super K, ? extends V> defaultCreator,
                Class<V> requiredType) {
            return checkResultType(key, computeIfAbsent(key, defaultCreator), requiredType);
        }

        @Override
        public void put(Object key, Object value) {
            values.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return values.remove(key);
        }

        @Override
        public <V> V remove(Object key, Class<V> requiredType) {
            return checkResultType(key, remove(key), requiredType);
        }

        private static <V> V checkResultType(Object key, Object value, Class<V> requiredType) {
            if (value == null) {
                return null;
            }
            if (!requiredType.isInstance(value)) {
                throw new ExtensionContextException(
                        "Object stored under key [" + key + "] is not of required type [" + requiredType + "]");
            }
            return requiredType.cast(value);
        }

        @SuppressWarnings("deprecation") // CloseableResource is what the original closed; AutoCloseable came later
        private void close(ThrowableCollector throwableCollector) {
            // make a snapshot before iterating in case some concurrent modification happens
            values.values().stream()
                    .filter(CloseableResource.class::isInstance)
                    .map(CloseableResource.class::cast)
                    .toList()
                    .forEach(resource -> throwableCollector.execute(resource::close));
        }
    }
}
