package tools.vitruv.applications.pcmjava.tests.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryComponent
import tools.vitruv.framework.views.View

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.OperationInterface

@Utility
class PcmQueryUtil {
	static def Repository claimSinglePcmRepository(View view) {
		view.getRootObjects(Repository).claimOne
	}

	static def Repository claimPcmRepository(View view, String repositoryName) {
		view.getRootObjects(Repository).filter[it.entityName == repositoryName].claimOne
	}
	
	static def RepositoryComponent claimComponent(Repository repository, String componentName) {
		repository.components__Repository.filter[it.entityName == componentName].claimOne
	}
	
	static def PrimitiveDataType claimPrimitiveDataType(Repository repository, PrimitiveTypeEnum primitiveType) {
		repository.dataTypes__Repository.filter(PrimitiveDataType).filter[it.type == primitiveType].claimOne
	}
	
	static def CompositeDataType claimCompositeDataType(Repository repository, String datatypeName) {
		repository.dataTypes__Repository.filter(CompositeDataType).filter[it.entityName == datatypeName].claimOne
	}
	
	static def OperationInterface claimOperationInterface(Repository repository, String interfaceName) {
		repository.interfaces__Repository.filter(OperationInterface).filter[it.entityName == interfaceName].claimOne
	}
}
