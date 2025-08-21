package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.performance.configs.VitruvServerController;

public class StartServerHandler extends Handler.Abstract.NonBlocking {
    private VitruvServerController controller;

    public StartServerHandler(VitruvServerController controller) {
        this.controller = controller;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        var configName = Request.getPathInContext(request).substring(1);
        if (!this.controller.isServerRunning(configName)) {
            if (this.controller.isServerRunning()) {
                response.setStatus(HttpStatus.CONFLICT_409);
                Content.Sink.write(response, true, "A server is already running.", callback);
                return true;
            }
            this.controller.startServer(configName);
            Content.Sink.write(response, true, "", callback);
            return true;
        }
        response.setStatus(HttpStatus.CREATED_201);
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
