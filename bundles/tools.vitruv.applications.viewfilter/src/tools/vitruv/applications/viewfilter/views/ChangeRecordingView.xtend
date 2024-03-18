package tools.vitruv.applications.viewfilter.views;

import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.change.composite.description.VitruviusChangeResolver
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.framework.views.CommittableView
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.applications.viewfilter.views.AbstractBasicView
import tools.vitruv.applications.viewfilter.views.ChangesetDeterminableView
import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView

/**
 * A {@link View} that records changes to its resources and allows to propagate them 
 * back to the underlying models using the {@link #commitChanges} method.
 */
class ChangeRecordingView implements ModifiableView, CommittableView {
    @Delegate
    protected ChangesetDeterminableView view
    protected ChangeRecorder changeRecorder

    protected new(ChangesetDeterminableView view) {
        checkArgument(view !== null, "view must not be null")
        checkState(!view.isModified, "view must not be modified")
        this.view = view
        setupChangeRecorder
    }

    override update() {
        changeRecorder.endRecordingAndClose()
        view.update()
        setupChangeRecorder
    }

    protected def setupChangeRecorder() {
        changeRecorder = new ChangeRecorder(view.viewResourceSet)
        changeRecorder.addToRecording(view.viewResourceSet)
        changeRecorder.beginRecording()
    }

    override commitChanges() {
        view.checkNotClosed()
        val recordedChange = changeRecorder.endRecording()
        val changeResolver = VitruviusChangeResolver.forHierarchicalIds(view.viewResourceSet)
        val unresolvedChanges = changeResolver.assignIds(recordedChange)
        view.viewType.commitViewChanges(this, unresolvedChanges)
        view.viewChanged = false
        changeRecorder.beginRecording()
    }

    override close() throws Exception {
        if (!isClosed) {
            changeRecorder.close()
        }
        view.close()
    }

    protected def void endRecordingAndClose(ChangeRecorder recorder) {
        if (recorder.isRecording) {
            recorder.endRecording()
        }
        recorder.close()
    }

    override withChangeRecordingTrait() {
        val newView = view.withChangeRecordingTrait
        changeRecorder.close
        return newView
    }

    override withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        val newView = view.withChangeDerivingTrait(changeResolutionStrategy)
        changeRecorder.close
        return newView
    }
}
