package tools.vitruv.applications.remote.tests;

public class ChangeDerivingPropagationTest extends PropagationBase {
    @Override
    public void initView() {
        view = remote.getView("test").withChangeDerivingTrait();
    }
}
