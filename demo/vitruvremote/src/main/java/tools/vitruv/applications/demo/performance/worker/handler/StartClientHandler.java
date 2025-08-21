package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.performance.common.PathConstants;
import tools.vitruv.applications.demo.performance.configs.VitruvClientController;

public class StartClientHandler extends Handler.Abstract.NonBlocking {
    private VitruvClientController controller;

    public StartClientHandler(VitruvClientController controller) {
        this.controller = controller;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        var configName = Request.getPathInContext(request).substring(1);
        var serverConfig = Request.extractQueryParameters(request).get(PathConstants.QUERY_PARAMETER_SERVER_CONFIG);
        if (!this.controller.isClientRunning() && configName != null && serverConfig != null) {
            var uri = Content.Source.asString(request);
            this.controller.startClient(configName, serverConfig.getValue(), uri);
        } else if (this.controller.isClientRunning()) {
            response.setStatus(HttpStatus.CREATED_201);
        } else if (configName == null || serverConfig == null) {
            response.setStatus(HttpStatus.BAD_REQUEST_400);
        }
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
