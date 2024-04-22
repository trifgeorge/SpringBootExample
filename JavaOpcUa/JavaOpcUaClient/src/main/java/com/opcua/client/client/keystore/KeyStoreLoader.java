package com.opcua.client.client.keystore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.milo.opcua.sdk.server.util.HostnameUtil;
import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateBuilder;
import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateGenerator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.regex.Pattern;

public class KeyStoreLoader {
    private static final Logger log = LogManager.getLogger(KeyStoreLoader.class);
    private static final Pattern IP_ADDR_PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final String CLIENT_ALIAS = "client-ai";
    private static final char[] PASSWORD = "password".toCharArray();
    private X509Certificate[] clientCertificateChain;
    private X509Certificate clientCertificate;
    private KeyPair clientKeyPair;

    public KeyStoreLoader load(Path baseDir) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        Path serverKeyStore = baseDir.resolve("example-client.pfx");

        log.info(" Loading KeyStore at {}", serverKeyStore);

        if (!Files.exists(serverKeyStore)) {
            keyStore.load(null, PASSWORD);

            KeyPair keyPair = SelfSignedCertificateGenerator.generateRsaKeyPair(2048);
            SelfSignedCertificateBuilder builder = new SelfSignedCertificateBuilder(keyPair).setCommonName("Eclipse Milo Example Client").setOrganization("AC").setOrganizationalUnit("dev").setLocalityName("Cluj").setStateName("Cluj Napoca").setCountryCode("RO").setApplicationUri("urn:eclipse:milo:examples:client").addDnsName("localhost").addIpAddress("127.0.0.1");

            // Get as many hostnames and IP addresses as we can list in the certificate.
            for (String hostname : HostnameUtil.getHostnames("0.0.0.0")) {
                if (IP_ADDR_PATTERN.matcher(hostname).matches()) {
                    builder.addIpAddress(hostname);
                } else {
                    builder.addDnsName(hostname);
                }
            }
            X509Certificate certificate = builder.build();
            keyStore.setKeyEntry(CLIENT_ALIAS, keyPair.getPrivate(), PASSWORD, new X509Certificate[]{certificate});
            try (OutputStream out = Files.newOutputStream(serverKeyStore)) {
                keyStore.store(out, PASSWORD);
            }
        } else {
            try (InputStream in = Files.newInputStream(serverKeyStore)) {
                keyStore.load(in, PASSWORD);
            }
        }
        Key clientPrivateKey = keyStore.getKey(CLIENT_ALIAS, PASSWORD);
        if (clientPrivateKey instanceof PrivateKey) {
            clientCertificate = (X509Certificate) keyStore.getCertificate(CLIENT_ALIAS);

            clientCertificateChain = Arrays.stream(keyStore.getCertificateChain(CLIENT_ALIAS)).map(X509Certificate.class::cast).toArray(X509Certificate[]::new);

            PublicKey serverPublicKey = clientCertificate.getPublicKey();
            clientKeyPair = new KeyPair(serverPublicKey, (PrivateKey) clientPrivateKey);
        }
        return this;
    }

    public X509Certificate getClientCertificate() {
        return clientCertificate;
    }

    public X509Certificate[] getClientCertificateChain() {
        return clientCertificateChain;
    }

    public KeyPair getClientKeyPair() {
        return clientKeyPair;
    }
}
