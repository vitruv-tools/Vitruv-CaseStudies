package tools.vitruv.applications.demo.performance.worker.handler;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

public class GetOidcHandler extends Handler.Abstract.NonBlocking {
    private String oidcUri;

    public GetOidcHandler(String oidcuri) {
        this.oidcUri = oidcuri;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        Content.Sink.write(response, true, this.oidcUri, callback);
        return true;
    }
}
