package tools.vitruv.applications.pcmjava.javaeditor.util;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import java.util.stream.Collectors;

import org.emftext.language.java.containers.Package;

import tools.vitruv.framework.views.View;

public class JavaQueryUtil {
	private JavaQueryUtil() {
	}
	
	public static Package claimPackage(View view, String name) {
		return claimOne(view.getRootObjects(Package.class).stream().filter(it -> name.equals(it.getName())).collect(Collectors.toList()));
	}
}
