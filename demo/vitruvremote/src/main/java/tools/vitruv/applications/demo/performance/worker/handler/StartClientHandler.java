package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.performance.common.VitruvClientController;

public class StartClientHandler extends Handler.Abstract.NonBlocking {
    private VitruvClientController controller;

    public StartClientHandler(VitruvClientController controller) {
        this.controller = controller;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        var configName = Request.getPathInContext(request).substring(1);
        if (!this.controller.isClientRunning(configName)) {
            var uri = Content.Source.asString(request);
            this.controller.startClient(configName, uri);
            Content.Sink.write(response, true, "", callback);
            return true;
        }
        response.setStatus(HttpStatus.CREATED_201);
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
