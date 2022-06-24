package tools.vitruv.domains.java.ui.monitorededitor.astchangelistener;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

/**
 * Implements and registers an {@link IExecutionListener} which listens to
 * Eclipse edit commands. If the edit command is a CUT, a withhold is propagated
 * to ensure that the callback listener waits for the insert event. Can be
 * paused.
 */
class EditCommandListener implements IExecutionListener {
	private static final Logger LOGGER = Logger.getLogger(EditCommandListener.class);
	private static final String EDIT_CUT_ID = "org.eclipse.ui.edit.cut";
	private final WithholdCallback withholdCallback;

	public EditCommandListener(WithholdCallback withholdCallback) {
		this.withholdCallback = withholdCallback;
	}

	public void startListening() {
		LOGGER.trace("Start listening with edit command listener");
		ICommandService service = PlatformUI.getWorkbench().getService(ICommandService.class);
		service.addExecutionListener(this);
	}

	public void stopListening() {
		LOGGER.trace("Stop listening with edit command listener");
		ICommandService service = PlatformUI.getWorkbench().getService(ICommandService.class);
		service.removeExecutionListener(this);
	}

	@Override
	public void notHandled(String commandId, NotHandledException exception) {
		// Do nothing.
	}

	@Override
	public void postExecuteFailure(String commandId, ExecutionException exception) {
		// Do nothing.
	}

	@Override
	public void postExecuteSuccess(String commandId, Object returnValue) {
		// Do nothing.
	}

	@Override
	public synchronized void preExecute(String commandId, ExecutionEvent event) {
		if (isEditCutCommand(event)) {
			withholdCallback.withholdEvent();
		}
	}

	private boolean isEditCutCommand(ExecutionEvent event) {
		if (event.getCommand().getId().equals(EDIT_CUT_ID)) {
			return true;
		}
		return false;
	}

}
