package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.performance.worker.ConfigExecutionController;

public class StateHandler extends Handler.Abstract.NonBlocking {
    private ConfigExecutionController controller;

    public StateHandler(ConfigExecutionController controller) {
        this.controller = controller;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        Content.Sink.write(response, true, "" + this.controller.getProgress(), callback);
        return true;
    }
}
