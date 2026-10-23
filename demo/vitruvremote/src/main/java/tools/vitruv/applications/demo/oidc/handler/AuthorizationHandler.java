package tools.vitruv.applications.demo.oidc.handler;

import tools.vitruv.applications.demo.oidc.OIDCMockProvider;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

public class AuthorizationHandler extends Handler.Abstract.NonBlocking {
    private OIDCMockProvider provider;

    public AuthorizationHandler(OIDCMockProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        var query = request.getHttpURI().getQuery();
        var redirectUri = this.provider.getRedirectUriForAuthRequest(query);
        response.setStatus(HttpStatus.FOUND_302);
        response.getHeaders().add(HttpHeader.LOCATION, redirectUri);
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
