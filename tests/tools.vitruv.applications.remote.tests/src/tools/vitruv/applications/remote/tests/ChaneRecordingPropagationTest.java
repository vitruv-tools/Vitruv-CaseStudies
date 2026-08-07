package tools.vitruv.applications.remote.tests;

public class ChaneRecordingPropagationTest extends PropagationBase {
    @Override
    public void initView() {
        view = remote.getView("test").withChangeRecordingTrait();
    }
}