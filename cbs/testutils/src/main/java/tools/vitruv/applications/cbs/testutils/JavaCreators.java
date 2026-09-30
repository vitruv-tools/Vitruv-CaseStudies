package tools.vitruv.applications.cbs.testutils;

import java.nio.file.Path;
import java.util.Set;
import org.emftext.language.java.JavaFactory;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.commons.CommonsFactory;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.types.TypesFactory;

public class JavaCreators extends FactoryCreators {
	public static final JavaCreators java = new JavaCreators();
	public final JavaTypesCreators types = new JavaTypesCreators();
	public final JavaClassifiersCreators classifiers = new JavaClassifiersCreators();
	public final JavaMembersCreators members = new JavaMembersCreators();
	public final JavaCommonsCreators commons = new JavaCommonsCreators();
	public final JavaContainersCreators containers = new JavaContainersCreators();
	private final MetamodelDescriptor metamodel = new MetamodelDescriptor("java", Set.of("java"));

	public JavaCreators() {
		super(JavaFactory.eINSTANCE);
	}

	public MetamodelDescriptor getMetamodel() {
		return metamodel;
	}

	public static Path java(Path path) {
		return path.resolveSibling(path.getFileName() + ".java");
	}

	public static Path java(String path) {
		return java(Path.of(path));
	}

	public static class JavaClassifiersCreators extends FactoryCreators {
		public JavaClassifiersCreators() {
			super(ClassifiersFactory.eINSTANCE);
		}
	}

	public static class JavaMembersCreators extends FactoryCreators {
		public JavaMembersCreators() {
			super(MembersFactory.eINSTANCE);
		}
	}

	public static class JavaTypesCreators extends FactoryCreators {
		public JavaTypesCreators() {
			super(TypesFactory.eINSTANCE);
		}
	}

	public static class JavaCommonsCreators extends FactoryCreators {
		public JavaCommonsCreators() {
			super(CommonsFactory.eINSTANCE);
		}
	}

	public static class JavaContainersCreators extends FactoryCreators {
		public JavaContainersCreators() {
			super(ContainersFactory.eINSTANCE);
		}
	}
}
