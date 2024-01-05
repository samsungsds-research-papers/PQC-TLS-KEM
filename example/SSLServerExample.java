import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.util.logging.LogManager;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;

public class SSLServerExample {

    public static void main(String[] args) throws Exception {

        // disable bouncycastle log (java.util.logging)
        LogManager.getLogManager().reset();

        int port = 8001;

        boolean isPQCEnabled = Boolean.parseBoolean(System.getProperty("test.pqc.enable", "true"));

        if (isPQCEnabled == true) {
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            Security.insertProviderAt(new BouncyCastleJsseProvider(), 2);
            Security.setProperty("ssl.KeyManagerFactory.algorithm", "PKIX");
            Security.setProperty("ssl.TrustManagerFactory.algorithm", "PKIX");        
        }

        // Load the server keystore
        String keystoreName = System.getProperty("test.keystore.name","");
        char[] keystorePassword = "serverpass".toCharArray();
        KeyStore serverKeyStore = KeyStore.getInstance("PKCS12");
        serverKeyStore.load(new FileInputStream("keystore/" + keystoreName + ".keystore"), keystorePassword);
        
        // Create SSL context
        SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(serverKeyStore, keystorePassword);
        sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
        
        // Create SSL socket factory
        SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
        
        // Create server socket
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
        sslServerSocket.setReuseAddress(true);        
        System.out.println("SSL Server started on port " + port);
        
        while (true) {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            sslSocket.setTcpNoDelay(true);

            try {                
                sslSocket.startHandshake();  
                
                processSocket(sslSocket);

            } catch (Exception e) {
                // do nothing
            } finally {
                try {
                    sslSocket.setSoLinger(true, 0);
                    sslSocket.close();                    
                } catch (Exception e) {
                    // do nothing
                } 
            }            
        }
    }

    private static void processSocket(SSLSocket sslSocket) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        String response = reader.readLine();

        PrintWriter writer = new PrintWriter(sslSocket.getOutputStream(), true);  
        writer.print(response);         
        
        writer.close();
        reader.close();
    }
}
