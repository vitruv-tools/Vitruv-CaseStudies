package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.oidc.OIDCMockServer;

public class StopWorkerHandler extends Handler.Abstract.NonBlocking {
    private OIDCMockServer oidcServer;

    public StopWorkerHandler(OIDCMockServer oidcServer) {
        this.oidcServer = oidcServer;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        this.oidcServer.stop();
        this.getServer().stop();
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
