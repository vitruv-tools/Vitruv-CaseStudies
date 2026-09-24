package tools.vitruv.applications.demo.performance.worker.handler;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

public class KeyStoreHandler extends Handler.Abstract.NonBlocking {
    private Path keyStorePath;
    private String localHost;
    
    public KeyStoreHandler(Path keyStorePath, String localHost) {
        this.keyStorePath = keyStorePath;
        this.localHost = localHost;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        if (HttpMethod.GET.is(request.getMethod()) && Request.getRemoteAddr(request).equals(this.localHost)) {
            var keyStoreContent = Files.readAllBytes(this.keyStorePath);
            response.write(true, ByteBuffer.wrap(keyStoreContent), callback);
            return true;
        }
        response.setStatus(HttpStatus.NOT_FOUND_404);
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
