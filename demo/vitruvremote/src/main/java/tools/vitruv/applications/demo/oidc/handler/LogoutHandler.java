package tools.vitruv.applications.demo.oidc.handler;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

import tools.vitruv.applications.demo.oidc.OIDCMockProvider;

public class LogoutHandler extends Handler.Abstract.NonBlocking {
    private OIDCMockProvider provider;

    public LogoutHandler(OIDCMockProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        response.setStatus(HttpStatus.FOUND_302);
        response.getHeaders()
            .add(HttpHeader.LOCATION, this.provider.getRedirectUriForLogout(request.getHttpURI().getQuery()));
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
