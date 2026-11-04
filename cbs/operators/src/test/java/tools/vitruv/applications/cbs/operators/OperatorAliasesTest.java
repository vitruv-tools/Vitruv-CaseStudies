package tools.vitruv.applications.cbs.operators;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tools.vitruv.applications.cbs.operators.domaincommon.operators.FirstUpperOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.attribute.AttributeMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.mapping.reference.ReferenceMappingOperator;
import tools.vitruv.dsls.commonalities.runtime.operators.participation.condition.ParticipationConditionOperator;

/**
 * Checks that every operator has a consistent {@code cl_operators__} counterpart, see the
 * package documentation.
 */
class OperatorAliasesTest {
    private static final String ALIAS_PACKAGE = "cl_operators__";
    private static final List<Class<? extends Annotation>> OPERATOR_ANNOTATIONS = List.of(
            AttributeMappingOperator.class,
            ReferenceMappingOperator.class,
            ParticipationConditionOperator.class);

    @Test
    void findsOperators() {
        assertFalse(operatorClasses().toList().isEmpty(), "no operator classes found");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("operatorClasses")
    void operatorHasConsistentAlias(Class<?> operator) throws ReflectiveOperationException {
        Annotation annotation = operatorAnnotation(operator);
        String operatorName = (String) annotation.annotationType().getMethod("name").invoke(annotation);
        String aliasName = operator.getPackageName() + "." + ALIAS_PACKAGE + "." + operatorName;

        Class<?> alias = Class.forName(aliasName);

        assertSame(operator, alias.getSuperclass(), aliasName + " must extend " + operator.getSimpleName());
        assertEquals(annotation, operatorAnnotation(alias),
                aliasName + " must repeat the annotation of " + operator.getSimpleName());
        Constructor<?>[] aliasConstructors = alias.getConstructors();
        assertEquals(1, aliasConstructors.length, aliasName + " must have exactly one public constructor");
        assertArrayEquals(operator.getConstructors()[0].getParameterTypes(),
                aliasConstructors[0].getParameterTypes(),
                aliasName + " must take the same constructor parameters as " + operator.getSimpleName());
    }

    static Stream<Class<?>> operatorClasses() {
        Path root = classesRoot();
        try (Stream<Path> files = Files.walk(root)) {
            return files
                    .filter(file -> file.toString().endsWith(".class"))
                    .filter(file -> !file.toString().contains(ALIAS_PACKAGE))
                    .<Class<?>>map(file -> loadClass(root, file))
                    .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                    .filter(clazz -> OPERATOR_ANNOTATIONS.stream().anyMatch(clazz::isAnnotationPresent))
                    .toList()
                    .stream();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot list the operator classes", e);
        }
    }

    private static Annotation operatorAnnotation(Class<?> clazz) {
        Annotation result = OPERATOR_ANNOTATIONS.stream()
                .map(clazz::getAnnotation)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        assertNotNull(result, clazz.getName() + " has no operator annotation");
        return result;
    }

    private static Path classesRoot() {
        String packagePath = OperatorAliasesTest.class.getPackageName().replace('.', '/');
        try {
            // The test classes are in their own directory, so locate the main classes through an operator.
            Path mainClasses = Path.of(
                    FirstUpperOperator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return mainClasses.resolve(packagePath);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Class<?> loadClass(Path root, Path classFile) {
        String relativeName = root.relativize(classFile).toString()
                .replace(classFile.getFileSystem().getSeparator(), ".");
        String className = OperatorAliasesTest.class.getPackageName() + "."
                + relativeName.substring(0, relativeName.length() - ".class".length());
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot load " + className, e);
        }
    }
}
