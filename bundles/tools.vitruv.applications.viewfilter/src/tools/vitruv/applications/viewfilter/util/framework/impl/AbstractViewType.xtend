package tools.vitruv.applications.viewfilter.util.framework.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.views.ViewSelector

import static com.google.common.base.Preconditions.checkArgument

abstract package class AbstractViewType<S extends ViewSelector, Id> implements ViewCreatingViewType<S, Id> {
	@Accessors(PUBLIC_GETTER)
	val String name

	new(String name) {
		checkArgument(name !== null, "view type name must not be null")
		this.name = name
	}
}
