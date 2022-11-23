package tools.vitruv.applications.pcmjava.javaeditor.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public class BlockingProgressMonitor extends NullProgressMonitor {
	private static final int DEFAULT_TIMEOUT_MS = 500;
	
	private final Semaphore semaphore = new Semaphore(0);
	
	public BlockingProgressMonitor() {
		super();
	}
	
	@Override
	public void done() {
		super.done();
		semaphore.release();
	}
	
	@Override
	public void setCanceled(boolean cancelled) {
		super.setCanceled(cancelled);
		if (cancelled) {
			semaphore.release();
		}
	}
	
	public void waitForCompletion() {
		try {
			semaphore.tryAcquire(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void performSynchronous(Consumer<IProgressMonitor> action) {
		BlockingProgressMonitor monitor = new BlockingProgressMonitor();
		action.accept(monitor);
		monitor.waitForCompletion();
	}
	
	public static <E extends Exception> void acceptSynchronousThrowing(ThrowingConsumer<IProgressMonitor, E> action) throws E {
		BlockingProgressMonitor monitor = new BlockingProgressMonitor();
		try {
			action.accept(monitor);
		} catch (Exception exception) {
			throw exception;
		}
		monitor.waitForCompletion();
	}
	
	public static <R> R performSynchronous(Function<IProgressMonitor, R> action) {
		BlockingProgressMonitor monitor = new BlockingProgressMonitor();
		R result = action.apply(monitor);
		monitor.waitForCompletion();
		return result;
	}
	
	public static <R, E extends Exception> R applySynchronousThrowing(ThrowingFunction<IProgressMonitor, R, E> action) throws E {
		BlockingProgressMonitor monitor = new BlockingProgressMonitor();
		R result = null;
		try {
			result = action.apply(monitor);
		} catch (Exception e) {
			throw e;
		}
		monitor.waitForCompletion();
		return result;
	}
	
	public static interface ThrowingConsumer<T, E extends Exception> {
		void accept(T t) throws E;
	}
	
	public static interface ThrowingFunction<T, R, E extends Exception> {
		R apply(T t) throws E;
	}
}
