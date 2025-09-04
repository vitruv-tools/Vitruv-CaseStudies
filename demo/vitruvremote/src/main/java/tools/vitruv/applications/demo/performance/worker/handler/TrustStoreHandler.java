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

import tools.vitruv.applications.demo.performance.common.PathConstants;
import tools.vitruv.remote.seccommon.cert.CertificateGenerator;

public class TrustStoreHandler extends Handler.Abstract.NonBlocking {
    private Path localTrustStorePath;
    private Path remoteTrustStorePath;
    private String tlsPwd;

    public TrustStoreHandler(Path localTrustStorePath, Path remoteTrustStorePath, String tlsPwd) {
        this.localTrustStorePath = localTrustStorePath;
        this.remoteTrustStorePath = remoteTrustStorePath;
        this.tlsPwd = tlsPwd;
    }

    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception {
        if (HttpMethod.GET.is(request.getMethod())) {
            response.getHeaders().add(PathConstants.HEADER_VITRUV_SECRET, this.tlsPwd);
            var trustStoreContent = Files.readAllBytes(this.localTrustStorePath);
            response.write(true, ByteBuffer.wrap(trustStoreContent), callback);
            return true;
        } else if (HttpMethod.POST.is(request.getMethod())) {
            var content = Content.Source.asByteBuffer(request);
            Files.write(this.remoteTrustStorePath, content.array());
            CertificateGenerator.mergeKeyStores(this.remoteTrustStorePath, this.tlsPwd, this.localTrustStorePath, this.tlsPwd, false);
            Content.Sink.write(response, true, "", callback);
            return true;
        }
        response.setStatus(HttpStatus.NOT_FOUND_404);
        Content.Sink.write(response, true, "", callback);
        return true;
    }
}
