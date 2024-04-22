package com.opcua.client.client;

import com.opcua.client.client.keystore.KeyStoreLoader;
import com.opcua.server.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.client.security.DefaultClientCertificateValidator;
import org.eclipse.milo.opcua.stack.core.Stack;
import org.eclipse.milo.opcua.stack.core.security.DefaultTrustListManager;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class ClientRunner {

    private static final Logger log = LogManager.getLogger(ClientRunner.class);
    private final CompletableFuture<OpcUaClient> future = new CompletableFuture<>();
    private final boolean serverRequired;
    private Server server;
    private DefaultTrustListManager trustListManager;
    private final Client client;

    public ClientRunner(Client client) throws Exception {
        this(client, true);
    }

    public ClientRunner(Client clientExample, boolean serverRequired) throws Exception {
        this.client = clientExample;
        this.serverRequired = serverRequired;

        if (serverRequired) {
            server = new Server();
            server.startup().get();
        }
    }

    private OpcUaClient createClient() throws Exception {
        Path securityTempDir = Paths.get(System.getProperty("java.io.tmpdir"), "client", "security");
        Files.createDirectories(securityTempDir);
        if (!Files.exists(securityTempDir)) {
            throw new Exception("unable to create security dir: " + securityTempDir);
        }
        File pkiDir = securityTempDir.resolve("pki").toFile();
        log.info(" Security dir: {}", securityTempDir.toAbsolutePath());
        log.info(" security pki dir: {}", pkiDir.getAbsolutePath());

        KeyStoreLoader loader = new KeyStoreLoader().load(securityTempDir);

        trustListManager = new DefaultTrustListManager(pkiDir);
        DefaultClientCertificateValidator certificateValidator = new DefaultClientCertificateValidator(trustListManager);

        return OpcUaClient.create(client.getEndpointUrl(), endpoints -> endpoints.stream().filter(client.endpointFilter()).findFirst(), configBuilder -> configBuilder.setApplicationName(LocalizedText.english("eclipse milo opc-ua client"))
                .setApplicationUri("urn:eclipse:milo:examples:client")
                .setKeyPair(loader.getClientKeyPair())
                .setCertificate(loader.getClientCertificate())
                .setCertificateChain(loader.getClientCertificateChain())
                .setCertificateValidator(certificateValidator)
                .setIdentityProvider(client.getIdentityProvider())
                .setRequestTimeout(uint(5000))
                .build());
    }

    public void run() {
        try {
            OpcUaClient opcUaClient = createClient();

            if (serverRequired) {
                // For the sake of the examples we will create mutual trust between the client and
                // server, so we can run them with security enabled by default.
                // If the client example is pointed at another server then the rejected certificate
                // will need to be moved from the security "pki/rejected" directory to the
                // "pki/trusted/certs" directory.

                // Make the example server trust the example client certificate by default.
                opcUaClient.getConfig().getCertificate().ifPresent(
                        certificate ->
                                server.getServer().getConfig().getTrustListManager().addTrustedCertificate(certificate)
                );

                // Make the example client trust the example server certificate by default.
                server.getServer().getConfig().getCertificateManager().getCertificates().forEach(
                        certificate ->
                                trustListManager.addTrustedCertificate(certificate)
                );
            }
            future.whenCompleteAsync((c, ex) -> {
                if (ex != null) {
                    log.error("Error running example: {}", ex.getMessage(), ex);
                }

                try {
                    opcUaClient.disconnect().get();
                    log.info( "CLIENT DISCONNECTED ");
                    if (serverRequired && server != null) {
                        server.shutdown().get();
                    }
                    Stack.releaseSharedResources();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error disconnecting: {}", e.getMessage(), e);
                }

                try {
                    Thread.sleep(1000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            try {
                client.run(opcUaClient, future);
                future.get(15, TimeUnit.SECONDS);
            } catch (Throwable t) {
                log.error("Error running client example: {}", t.getMessage(), t);
                future.completeExceptionally(t);
            }
        } catch (Throwable t) {
            log.error("Error getting client: {}", t.getMessage(), t);

            future.completeExceptionally(t);

            try {
                Thread.sleep(1000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(999_999_999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
