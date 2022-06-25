package tools.vitruv.domains.java.ui.monitorededitor.astchangelistener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IStartup;

import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.ConcreteChangeClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postchange.CreatePackageClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postchange.DeletePackageClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postchange.RemoveCompilationUnitClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postchange.RenamePackageClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.AddFieldClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.AddImportClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.AddMethodClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeAnnotationClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeFieldModifiersClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeFieldTypeClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeMethodModifiersClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeMethodParameterClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeMethodReturnTypeClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangePackageDeclarationClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeSupertypeClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.ChangeTypeModifiersClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.CreateTypeClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.HigherOperationEventsFilter;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.JavaDocClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.MethodBodyChangedClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.MethodParameterNameChangedClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.RemoveFieldClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.RemoveImportClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.RemoveMethodClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.RemoveTypeClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.RenameFieldClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.RenameMethodClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.astchangelistener.classification.postreconcile.RenameTypeClassifier;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruv.domains.java.ui.monitorededitor.javamodel2ast.CompilationUnitUtil;

import static com.google.common.base.Preconditions.checkState;

/**
 * The {@link ASTChangeListener} reacts to changes in the Eclipse JDT AST and
 * generates {@link ChangeClassifyingEvent}s from the AST
 * {@link IJavaElementDelta}. Registered {@link ChangeOperationListener}s are
 * notified when new code changes occur. The ASTChangeListener holds an array of
 * {@link ConcreteChangeClassifier}s who are responsible for classifying changes
 * given the AST delta and the current and previous AST state.
 */
public class ASTChangeListener implements IStartup, IElementChangedListener, WithholdCallback {

	private static final Logger LOG = Logger.getLogger(ASTChangeListener.class);
	private static final int CHANGE_HISTORY_MINUTES = 4;

	private final List<ConcreteChangeClassifier> postReconcileClassifiers;
	private final List<ConcreteChangeClassifier> postChangeClassifiers;
	private final PreviousASTState previousState;
	private final ChangeHistory withholdChangeHistory;
	private final List<ChangeOperationListener> listeners;

	private volatile boolean listening = false;
	private volatile boolean withholding = false;
	private final EditCommandListener editCommandListener;
	private final Set<String> monitoredProjectNames;

	public ASTChangeListener(final Set<String> monitoredProjectNames) {
		LOG.debug("Start initializing AST change listener for projects " + monitoredProjectNames);
		this.monitoredProjectNames = monitoredProjectNames;
		this.postReconcileClassifiers = getPostReconcileClassifiers();
		this.postChangeClassifiers = getPostChangeClassifiers();
		this.listeners = new ArrayList<ChangeOperationListener>();

		this.previousState = new PreviousASTState(monitoredProjectNames);
		this.withholdChangeHistory = new ChangeHistory(CHANGE_HISTORY_MINUTES);
		this.editCommandListener = new EditCommandListener(this);
	}

	public void startListening() {
		LOG.debug("Start AST listening for projects " + monitoredProjectNames);
		editCommandListener.startListening();
		JavaCore.addElementChangedListener(this);
		listening = true;
	}

	public void stopListening() {
		LOG.debug("Stop AST listening for projects " + monitoredProjectNames);
		listening = false;
		editCommandListener.stopListening();
		JavaCore.removeElementChangedListener(this);
	}

	private static List<ConcreteChangeClassifier> getPostReconcileClassifiers() {
		final List<ConcreteChangeClassifier> classifiers = new ArrayList<ConcreteChangeClassifier>();
		classifiers.add(new AddFieldClassifier());
		classifiers.add(new RemoveFieldClassifier());
		classifiers.add(new RenameFieldClassifier());
		classifiers.add(new ChangeFieldTypeClassifier());
		classifiers.add(new ChangeFieldModifiersClassifier());
		classifiers.add(new RenameMethodClassifier());
		classifiers.add(new AddMethodClassifier());
		classifiers.add(new RemoveMethodClassifier());
		classifiers.add(new ChangeMethodParameterClassifier());
		classifiers.add(new ChangeMethodModifiersClassifier());
		classifiers.add(new MethodBodyChangedClassifier());
		classifiers.add(new MethodParameterNameChangedClassifier());
		classifiers.add(new ChangeMethodReturnTypeClassifier());
		classifiers.add(new CreateTypeClassifier());
		classifiers.add(new RemoveTypeClassifier());
		classifiers.add(new RenameTypeClassifier());
		classifiers.add(new ChangeTypeModifiersClassifier());
		classifiers.add(new ChangePackageDeclarationClassifier());
		classifiers.add(new AddImportClassifier());
		classifiers.add(new RemoveImportClassifier());
		classifiers.add(new ChangeSupertypeClassifier());
		classifiers.add(new ChangeAnnotationClassifier());
		classifiers.add(new JavaDocClassifier());
		
		return classifiers;
	}

	private static List<ConcreteChangeClassifier> getPostChangeClassifiers() {
		final List<ConcreteChangeClassifier> classifiers = new ArrayList<ConcreteChangeClassifier>();
		classifiers.add(new RemoveCompilationUnitClassifier());
		classifiers.add(new RenamePackageClassifier());
		classifiers.add(new CreatePackageClassifier());
		classifiers.add(new DeletePackageClassifier());
		return classifiers;
	}

	@Override
	public synchronized void elementChanged(final ElementChangedEvent event) {
		for (ChangeOperationListener listener : new ArrayList<>(listeners)) {
			listener.notifyEventOccured();
		}

		if (!listening) {
			return;
		}

		final IJavaElementDelta delta = event.getDelta();
		if (!isMonitoredProject(delta)) {
			return;
		}

		LOG.trace("Recognized relevant AST change in one of the projects " + monitoredProjectNames + ": " + delta);

		final CompilationUnit currentCompilationUnit = CompilationUnitUtil.parseCompilationUnit(delta);
		List<ChangeClassifyingEvent> events = null;
		if (event.getType() == ElementChangedEvent.POST_CHANGE) {
			events = this.classifyPostChange(delta, currentCompilationUnit);
		} else if (event.getType() == ElementChangedEvent.POST_RECONCILE) {
			events = this.classifyPostReconcile(delta, currentCompilationUnit);
		}

		for (final ChangeClassifyingEvent e : events) {
			LOG.trace("AST event classified in one of the projects " + monitoredProjectNames + ": " + e);
		}

		final List<ChangeClassifyingEvent> filteredEvents = HigherOperationEventsFilter.filter(events,
				withholdChangeHistory);
		for (final ChangeClassifyingEvent e : filteredEvents) {
			if (withholding) {
				LOG.trace("[WITHHOLD] ");
			}
			LOG.trace("AST event classified and filtered in one of the projects " + monitoredProjectNames + ": " + e);
		}

		previousState.update(currentCompilationUnit);
		if (!withholding) {
			notifyEventClassified(filteredEvents);
		} else {
			withholdChangeHistory.update(filteredEvents);
			withholding = false;
		}
	}

	private boolean isMonitoredProject(final IJavaElementDelta delta) {
		final IJavaElement element = delta.getElement();
		if (element == null) {
			return false;
		}
		boolean isMonitored = false;
		IJavaProject project = element.getJavaProject();
		if (project != null) {
			isMonitored |= this.monitoredProjectNames.contains(project.getElementName());
		}
		for (IJavaElementDelta elementDelta : delta.getAffectedChildren()) {
			project = elementDelta.getElement().getJavaProject();
			if (project != null) {
				isMonitored |= this.monitoredProjectNames.contains(project.getElementName());
			}
		}
		return isMonitored;
	}

	private List<ChangeClassifyingEvent> classifyPostReconcile(final IJavaElementDelta delta,
			final CompilationUnit currentCompilationUnit) {
		return classifyChange(delta, this.postReconcileClassifiers, currentCompilationUnit);
	}

	private List<ChangeClassifyingEvent> classifyPostChange(final IJavaElementDelta delta,
			final CompilationUnit currentCompilationUnit) {
		return classifyChange(delta, this.postChangeClassifiers, currentCompilationUnit);
	}

	private List<ChangeClassifyingEvent> classifyChange(final IJavaElementDelta delta,
			final Iterable<ConcreteChangeClassifier> classifiers, final CompilationUnit currentCompilationUnit) {
		final List<ChangeClassifyingEvent> events = new ArrayList<ChangeClassifyingEvent>();
		for (final ConcreteChangeClassifier classifier : classifiers) {
			events.addAll(classifier.match(delta, currentCompilationUnit, this.previousState));
		}
		return events;
	}

	private void notifyEventClassified(final List<ChangeClassifyingEvent> events) {
		for (final ChangeClassifyingEvent event : events) {
			for (final ChangeOperationListener listener : this.listeners) {
				listener.notifyEventClassified(event);
			}
		}
	}

	public synchronized void addListener(final ChangeOperationListener listener) {
		checkState(listener != null, "Listener must not be null");
		listeners.add(listener);
	}

	public synchronized void removeListener(final ChangeOperationListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void withholdEvent() {
		withholding = true;
	}

	@Override
	public void earlyStartup() {
	}

}
