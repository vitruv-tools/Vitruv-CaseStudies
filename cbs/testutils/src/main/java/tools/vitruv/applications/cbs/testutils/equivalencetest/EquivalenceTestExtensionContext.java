package tools.vitruv.applications.cbs.testutils.equivalencetest;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExecutableInvoker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContextException;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;
import org.junit.jupiter.api.extension.MediaType;
import org.junit.jupiter.api.extension.TestInstances;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;
import org.opentest4j.TestAbortedException;
import tools.vitruv.applications.cbs.testutils.MetamodelDescriptor;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class EquivalenceTestExtensionContext implements ExtensionContext {
    private String name;
    private int executionIndex;
    private ExtensionContext parentContext;
    private MetamodelDescriptor testMetamodel;
    private Optional<Throwable> executionException = Optional.empty();
    private Map<Namespace, NamespaceStore> namespaceStores = new HashMap<>();

    public EquivalenceTestExtensionContext(String name, int executionIndex, ExtensionContext parentContext) {
        this(name, executionIndex, parentContext, null);
    }

    public EquivalenceTestExtensionContext(String name, int executionIndex, ExtensionContext parentContext,
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
    public Optional<java.lang.reflect.AnnotatedElement> getElement() {
        return parentContext.getElement();
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
        return namespaceStores.computeIfAbsent(namespace, ns -> new NamespaceStore());
    }

    @Override
    public Store getStore(StoreScope scope, Namespace namespace) {
        // For simplicity, ignore scope and delegate to the existing getStore(Namespace)
        return getStore(namespace);
    }

    @Override
    public Set<String> getTags() {
        if (testMetamodel == null) {
            return parentContext.getTags();
        } else {
            Set<String> tags = parentContext.getTags();
            tags.add(testMetamodel.name);
            return tags;
        }
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
    public Optional<java.lang.reflect.Method> getTestMethod() {
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
    public void publishReportEntry(Map<String, String> map) {
        throw new UnsupportedOperationException(
                "Reporting is not supported for " + this.getClass().getSimpleName() + "!");
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
    public Optional<Throwable> getExecutionException() {
        return executionException;
    }

    @Override
    public ExecutionMode getExecutionMode() {
        // Delegate to parent or return a default
        return parentContext.getExecutionMode();
    }

    void close() {
        ThrowableCollector throwableCollector = new ThrowableCollector(t -> t instanceof TestAbortedException);
        // make a snapshot before iterating in case some concurrent modification happens
        namespaceStores.values().stream().toList().forEach(store -> store.close(throwableCollector));
        throwableCollector.assertEmpty();
    }

    private static class NamespaceStore implements ExtensionContext.Store {
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
        @SuppressWarnings("unchecked")
        public <K, V> V getOrComputeIfAbsent(K key,
                java.util.function.Function<? super K, ? extends V> defaultCreator) {
            return (V) ((Map<K, Object>) values).computeIfAbsent(key, defaultCreator);
        }

        @Override
        public <K, V> V getOrComputeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> defaultCreator,
                Class<V> requiredType) {
            return checkResultType(key, getOrComputeIfAbsent(key, defaultCreator), requiredType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <K, V> V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
            return (V) ((Map<K, Object>) values).computeIfAbsent(key, mappingFunction);
        }

        @Override
        public <K, V> V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction,
                Class<V> requiredType) {
            return checkResultType(key, computeIfAbsent(key, mappingFunction), requiredType);
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

        private <V> V checkResultType(Object key, Object value, Class<V> requiredType) {
            if (value == null)
                return null;
            if (!requiredType.isInstance(value)) {
                throw new ExtensionContextException(
                        "Object stored under key [" + key + "] is not of required type [" + requiredType + "]");
            }
            return requiredType.cast(value);
        }

        private void close(ThrowableCollector throwableCollector) {
            // make a snapshot before iterating in case some concurrent modification happens
            values.values().stream()
                    .filter(CloseableResource.class::isInstance)
                    .map(CloseableResource.class::cast)
                    .toList()
                    .forEach(resource -> throwableCollector.execute(resource::close));
        }
    }

    @Override
    public List<Class<?>> getEnclosingTestClasses() {
        throw new UnsupportedOperationException("Unimplemented method 'getEnclosingTestClasses'");
    }

    @Override
    public void publishFile(String arg0, org.junit.jupiter.api.MediaType arg1, ThrowingConsumer<Path> arg2) {
        throw new UnsupportedOperationException("Unimplemented method 'publishFile'");
    }
}