package tools.vitruv.applications.pcmjava.tests.util.java2pcm

interface SynchronizationAwaitCallback {
	def void waitForSynchronization(int numberOfExpectedSynchronizationCalls)
}
