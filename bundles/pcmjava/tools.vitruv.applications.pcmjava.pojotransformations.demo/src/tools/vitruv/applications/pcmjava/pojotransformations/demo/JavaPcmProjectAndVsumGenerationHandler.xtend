package tools.vitruv.applications.pcmjava.pojotransformations.demo

import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException

class JavaPcmProjectAndVsumGenerationHandler extends AbstractHandler {
	
	override execute(ExecutionEvent event) throws ExecutionException {
		new JavaPcmProjectAndVsumGeneration().createProjectAndVsum();
		return null;
	}
	
}