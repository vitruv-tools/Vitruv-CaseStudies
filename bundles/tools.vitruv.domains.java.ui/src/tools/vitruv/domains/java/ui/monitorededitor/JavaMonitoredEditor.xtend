package tools.vitruv.domains.java.ui.monitorededitor

import java.util.ArrayList
import java.util.List
import java.util.Set
import java.util.function.Supplier
import org.apache.log4j.Logger
import org.eclipse.core.resources.WorkspaceJob
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.ui.IStartup
import org.eclipse.ui.PlatformUI
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.ASTChangeListener
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.ChangeOperationListener
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeClassifyingEventToResourceChangeConverter
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ResourceChange
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeClassifyingEvent
import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.framework.domains.ui.monitorededitor.AbstractMonitoredEditor
import tools.vitruv.framework.domains.ui.monitorededitor.MonitoredEditor
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewTypeFactory
import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy
import tools.vitruv.framework.vsum.VirtualModel

import static com.google.common.base.Preconditions.checkState

/** 
 * Extends {@link AbstractMonitoredEditor} and implements {@link UserInteractor} by delegation to a dialog {@link UserInteractor}. 
 * The {@link MonitoredEditor} uses the {@link ASTChangeListener} to monitor changes in Java source code. The listener generates 
 * {@link ChangeClassifyingEvent}s which are converted to {@link ResourceChange}s by the {@link ChangeClassifyingEventToResourceChangeConverter}
 * and then propagated as state-based changes to the {@link VirtualModel}.
 */
class JavaMonitoredEditor extends AbstractMonitoredEditor implements ChangeOperationListener, IStartup {
    static val Logger log = Logger.getLogger(JavaMonitoredEditor)

    @Accessors(PUBLIC_GETTER)
    val Set<String> monitoredProjectNames
    @Accessors(PUBLIC_SETTER)
    volatile boolean reportChanges
    val ChangeClassifyingEventToResourceChangeConverter changeConverter
    val RecordingState recordingState
    val ASTChangeListener astListener
    val Supplier<Boolean> shouldBeActive

    new(VirtualModel virtualModel, Supplier<Boolean> shouldBeActive, String... monitoredProjectNames) {
        super(virtualModel)
        checkState(PlatformUI.getWorkbench() !== null,
            "Platform is not running but necessary for monitored Java editor")
        log.debug('''Start initializing monitored Java editor for projects «monitoredProjectNames»''')
        this.monitoredProjectNames = Set.of(monitoredProjectNames)
        this.changeConverter = new ChangeClassifyingEventToResourceChangeConverter()
        this.reportChanges = true
        this.shouldBeActive = shouldBeActive
        this.recordingState = new RecordingState([submitPropagationCheckJobIfNecessary])
        astListener = new ASTChangeListener(this.monitoredProjectNames)
        astListener.addListener(this)
        log.trace('''Finished initializing monitored Java editor for projects «monitoredProjectNames»''')
    }

    new(VirtualModel virtualModel, String... monitoredProjectNames) {
        this(virtualModel, [true], monitoredProjectNames)
    }

    def void startMonitoring() {
        astListener.startListening
    }

    def void stopMonitoring() {
        astListener.stopListening
    }

    def private void submitPropagationCheckJobIfNecessary() {
        if (automaticPropagationAfterMilliseconds == -1) {
            return
        }
        log.trace('''Submitting propagation job for projects «monitoredProjectNames»''')
        val changePropagationJob = new WorkspaceJob("Change propagation check job") {
            override runInWorkspace(IProgressMonitor monitor) {
                propagateRecordedChangesIfNecessary
                return Status.OK_STATUS
            }
        }
        changePropagationJob.setPriority(Job.SHORT)
        // Defer change propagation while waiting for further changes to occur
        changePropagationJob.schedule(automaticPropagationAfterMilliseconds)
    }

    private def void propagateRecordedChangesIfNecessary() {
        log.trace('''Checking for necessary change propagation in projects «monitoredProjectNames»''')
        if (!recordingState.hasRecentlyBeenChanged()) {
            submitChangePropagationJob
        } else {
            recordingState.touch()
            submitPropagationCheckJobIfNecessary
        }
    }

    override void propagateRecordedChanges() {
        log.debug('''Explicitly triggered change propagation for projects «monitoredProjectNames»''')
        submitChangePropagationJob
    }

    private def void submitChangePropagationJob() {
        log.trace('''Submitting change propagation job for projects «monitoredProjectNames»''')
        val changePropagationJob = new WorkspaceJob("Change propagation job") {
            override runInWorkspace(IProgressMonitor monitor) {
                internalPropagateRecordedChanges
                return Status.OK_STATUS
            }
        }
        changePropagationJob.setPriority(Job.BUILD)
        changePropagationJob.schedule()
    }

    private def void internalPropagateRecordedChanges() {
        log.debug('''Propagating «recordingState.changeCount» change(s) in projects «monitoredProjectNames»''')
        val changes = recordingState.reset()
        for (ResourceChange change : changes) {
            val changedResource = if (change.newResourceURI !== null) {
                    try {
                        val changedResource = new ResourceSetImpl().getResource(change.newResourceURI, true)
                        // Resolve proxies to ensure that all elements can be found instead of treating proxies as new elements
                        EcoreUtil.resolveAll(changedResource)
                        changedResource
                    } catch (RuntimeException e) {
                        throw new IllegalStateException("Resource " + change.newResourceURI +
                            " was not found although it should exist")
                    }
                }
            // TODO This is just a workaround to access the previous resource state.
            // The correct behavior would be to attach the Java monitor to a state-deriving view and reuse its change deriving functionality.
            try (val view = getViewForURI(change.oldResourceURI ?: change.newResourceURI)) {
                val oldResource = view.rootObjects.head?.eResource
                val changeSequence = generateChange(changedResource, oldResource)
                virtualModel.propagateChange(changeSequence)
            } catch (Exception e) {
                log.error('''Some error occurred during propagating changes in projects «monitoredProjectNames»''', e)
                throw new IllegalStateException(e)
            }
        }
    }

    private def View getViewForURI(URI resourceURI) {
        val selector = virtualModel.createSelector(ViewTypeFactory.createIdentityMappingViewType("Java"))

        for (rootElement : selector.selectableElements.filter[it.eResource?.URI == resourceURI]) {
            selector.setSelected(rootElement, true)
        }
        return selector.createView()
    }

    private def VitruviusChange generateChange(Resource newState, Resource referenceState) {
        val changeResolutionStrategy = new DefaultStateBasedChangeResolutionStrategy
        if (referenceState === null) {
            return changeResolutionStrategy.getChangeSequenceForCreated(newState)
        } else if (newState === null) {
            return changeResolutionStrategy.getChangeSequenceForDeleted(referenceState)
        } else {
            return changeResolutionStrategy.getChangeSequenceBetween(newState, referenceState)
        }
    }

    override void notifyEventOccured() {
        if (!shouldBeActive.get) {
            stopMonitoring
        }
    }

    override void notifyEventClassified(ChangeClassifyingEvent event) {
        log.debug('''Monitored editor for projects «monitoredProjectNames» reacting to change «event»''')
        val convertedChange = changeConverter.convert(event)
        if (!this.reportChanges) {
            log.
                warn('''Do not report change «convertedChange» because reporting is disabled for projects «monitoredProjectNames»''')
            return
        }
        log.trace('''Submit change in projects «monitoredProjectNames»''')
        recordingState.submitChange(convertedChange)
    }

    override void earlyStartup() {
    }

    private static class RecordingState {
        val List<ResourceChange> changes = new ArrayList()
        int lastChangeCount = 0
        val ()=>void submitPropagation

        new(()=>void submitPropagation) {
            this.submitPropagation = submitPropagation
        }

        def synchronized Iterable<ResourceChange> reset() {
            val returnedChanges = new ArrayList(changes)
            changes.clear()
            lastChangeCount = 0
            return returnedChanges
        }

        def synchronized void submitChange(ResourceChange change) {
            changes.add(change)
            // If this is the first change, submit a propagation job
            if (changes.size() === 1) {
                submitPropagation.apply
            }
        }

        def synchronized boolean hasRecentlyBeenChanged() {
            return changes.size() !== lastChangeCount
        }

        def synchronized void touch() {
            lastChangeCount = changes.size()
        }

        def synchronized int getChangeCount() {
            return changes.size()
        }

        def synchronized Iterable<ResourceChange> getChanges() {
            return changes
        }
    }
}
