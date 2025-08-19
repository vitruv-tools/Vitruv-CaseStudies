package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.performance.common.VitruvClientController;

public class ClientStateHandler extends Handler.Abstract.NonBlocking {
    private VitruvClientController controller;

    public ClientStateHandler(VitruvClientController controller) {
        this.controller = controller;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        var configName = Request.getPathInContext(request).substring(1);
        var running = this.controller.isClientRunning(configName);
        Content.Sink.write(response, true, "" + running, callback);
        return true;
    }
}
