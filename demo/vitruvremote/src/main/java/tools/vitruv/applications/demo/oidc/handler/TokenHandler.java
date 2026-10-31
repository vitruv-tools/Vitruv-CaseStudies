package tools.vitruv.applications.demo.oidc.handler;

import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.oidc.OIDCMockProvider;

public class TokenHandler extends Handler.Abstract.NonBlocking {
    private OIDCMockProvider provider;

    public TokenHandler(OIDCMockProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        Content.Sink.write(response, true, this.provider.getTokens(), callback);
        return true;
    }
}
