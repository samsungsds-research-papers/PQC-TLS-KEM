import javax.net.ssl.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.security.*;
import java.util.logging.LogManager;
import java.security.cert.CertificateException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;
import org.bouncycastle.tls.TlsFatalAlertReceived;

import java.lang.management.*;
import com.sun.management.OperatingSystemMXBean;

public class SSLClientExample {

    private static OperatingSystemMXBean oBean = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean());
    private static ThreadMXBean tBean = ManagementFactory.getThreadMXBean();

    private static String TEST_MESSAGE = "";
    private static String TEST_SERVER_HOST = "127.0.0.1";
    private static int TEST_SERVER_PORT = 8001;
    private static int TEST_COUNT = 100;

    public static void main(String[] args) throws Exception {

        // disable bouncycastle log (java.util.logging)
        LogManager.getLogManager().reset();

        boolean isPQCEnabled = Boolean.parseBoolean(System.getProperty("test.pqc.enable", "true"));

        if (isPQCEnabled == true) {
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            Security.insertProviderAt(new BouncyCastleJsseProvider(), 2);		
            Security.setProperty("ssl.KeyManagerFactory.algorithm", "PKIX");
            Security.setProperty("ssl.TrustManagerFactory.algorithm", "PKIX");
        }
        // Create SSL context
        SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                // Trust all client certificates
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                // Trust all server certificates
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, new SecureRandom());
        
        // Create SSL socket factory
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        
        long startRealTime = System.currentTimeMillis();
        long startProcessCpuTime = oBean.getProcessCpuTime();
        long startThreadCpuTime = tBean.getCurrentThreadCpuTime();
    
        for (int i = 0; i < SSLClientExample.TEST_COUNT; i++) {
            try {
                // open connection
                SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(SSLClientExample.TEST_SERVER_HOST, SSLClientExample.TEST_SERVER_PORT);			
                sslSocket.setUseClientMode(true);
                sslSocket.setTcpNoDelay(true);

                // TLS Handshake            
                sslSocket.startHandshake();
    
                // print progress (console output: '.')
                processSocket(sslSocket);

                // close connection
                sslSocket.setSoLinger(true, 0);
                sslSocket.close();
            } catch(TlsFatalAlertReceived e) {
                e.printStackTrace();    
                throw new RuntimeException(e);            
            } catch(Exception e) {
                // do nothing
            }
        }

        long endRealTime = System.currentTimeMillis();
        long endProcessCpuTime = oBean.getProcessCpuTime();
        long endThreadCpuTime = tBean.getCurrentThreadCpuTime();

        System.out.println("");        
        System.out.println("==========================================================");
        System.out.println("Test Count:" + SSLClientExample.TEST_COUNT );
        System.out.println("Cpu-Time(Process):" + (endProcessCpuTime - startProcessCpuTime) / 1000000L + "ms");
        System.out.println("Cpu-Time(Thread) :" + (endThreadCpuTime - startThreadCpuTime) / 1000000L + "ms");
        System.out.println("Real-Time:" + (endRealTime - startRealTime) + "ms");
        System.out.println("==========================================================");
        
        
    }

    private static void processSocket(SSLSocket sslSocket) throws Exception {
        String message = SSLClientExample.TEST_MESSAGE;
        if (!message.equals("")) {
            PrintWriter writer = new PrintWriter(sslSocket.getOutputStream(), true);
            writer.println(message);

            BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));                
            String response = reader.readLine();

            if (!message.equals(response)) {
                throw new RuntimeException("Message Error");
            }            

            writer.close();
            reader.close();                    
        }

        System.out.print(".");
    }
}
