package tools.vitruv.domains.java.ui.monitorededitor.changeclassification

import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.Accessors
import static com.google.common.base.Preconditions.checkState

class ResourceChange {
	@Accessors(PUBLIC_GETTER)
	val URI oldResourceURI
	@Accessors(PUBLIC_GETTER)
	val URI newResourceURI
	
	new(URI oldResourceURI, URI newResourceURI) {
		checkState(oldResourceURI !== null || newResourceURI !== null, "either old or new resource URI must not be null")
		this.oldResourceURI = oldResourceURI
		this.newResourceURI = newResourceURI
	}
	
}