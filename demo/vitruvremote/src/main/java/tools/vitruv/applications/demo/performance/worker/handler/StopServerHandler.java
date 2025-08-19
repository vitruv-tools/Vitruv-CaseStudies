package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.performance.common.VitruvServerController;

public class StopServerHandler extends Handler.Abstract.NonBlocking {
    private VitruvServerController controller;

    public StopServerHandler(VitruvServerController controller) {
        this.controller = controller;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        var configName = Request.getPathInContext(request).substring(1);
        if (this.controller.isServerRunning(configName)) {
            this.controller.stopServer(configName);
        }
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
