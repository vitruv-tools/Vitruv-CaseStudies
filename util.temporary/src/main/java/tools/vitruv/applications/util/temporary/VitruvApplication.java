package tools.vitruv.applications.util.temporary;

import java.util.Set;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

/**
 * Interface for Vitruv applications. Taken over from Vitruv's
 * {@code tools.vitruv.framework.applications} module, which Vitruv removed.
 */
public interface VitruvApplication {

  /**
   * Returns the change propagation specifications of this Vitruv application.
   *
   * @return the change propagation specifications
   */
  public Set<ChangePropagationSpecification> getChangePropagationSpecifications();

  /**
   * Returns the name of this Vitruv application.
   *
   * @return the name
   */
  public String getName();
}
