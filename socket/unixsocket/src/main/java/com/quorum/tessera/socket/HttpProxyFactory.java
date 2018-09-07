package com.quorum.tessera.socket;

import com.quorum.tessera.config.ServerConfig;
import com.quorum.tessera.config.SslConfig;
import com.quorum.tessera.ssl.context.model.SSLContextProperties;
import com.quorum.tessera.ssl.strategy.TrustMode;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import java.net.URI;

/**
 * Creates proxies to the local HTTP server, setting up connection
 * configuration such as the target address and SSL settings.
 */
public class HttpProxyFactory {

    private final URI serverUri;

    private final SocketFactory socketFactory;

    private static final String LOCALHOST = "127.0.0.1:";

    public HttpProxyFactory(final ServerConfig serverConfig) throws Exception {
        this.serverUri = new URI(serverConfig.getServerUri().getScheme() + "://" + LOCALHOST + serverConfig.getPort());

        if (serverConfig.isSsl()) {

            final SslConfig sslConfig = serverConfig.getSslConfig();

            final SSLContext sslContext = TrustMode.NONE.createSSLContext(
                new SSLContextProperties(
                    LOCALHOST + serverConfig.getPort(),
                    sslConfig.getClientKeyStore(),
                    sslConfig.getClientKeyStorePassword(),
                    sslConfig.getClientTlsKeyPath(),
                    sslConfig.getClientTlsCertificatePath(),
                    sslConfig.getClientTrustStore(),
                    sslConfig.getClientTrustStorePassword(),
                    sslConfig.getClientTrustCertificates(),
                    sslConfig.getKnownServersFile()
                )
            );
            
            this.socketFactory = sslContext.getSocketFactory();
        } else {
            this.socketFactory = SocketFactory.getDefault();
        }

    }

    /**
     * Creates a new proxy to the server, whose connection hasn't yet been opened
     *
     * @return
     */
    public HttpProxy create() {
        return new HttpProxy(serverUri, socketFactory);
    }

}
