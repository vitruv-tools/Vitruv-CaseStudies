package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import com.nimbusds.jose.shaded.gson.Gson;

import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting;
import tools.vitruv.applications.demo.performance.worker.ConfigExecutionController;

public class StartConfigHandler extends Handler.Abstract.NonBlocking {
    private ConfigExecutionController controller;
    private Gson jsonParser = new Gson();

    public StartConfigHandler(ConfigExecutionController controller) {
        this.controller = controller;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        var body = Content.Source.asString(request);
        var configSetting = this.jsonParser.fromJson(body, StartConfigurationSetting.class);
        var responseBody = "";
        if (!this.controller.isRunning()) {
            responseBody = this.controller.executeConfig(configSetting);
        }
        Content.Sink.write(response, true, responseBody, callback);
        return true;
    }
}
