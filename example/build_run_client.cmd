del SSLClientExample.class
javac -cp ./libs/* SSLClientExample.java
REM java -Xms1024m -Xmx1024m -Djava.net.preferIPv4Stack=true -Djdk.tls.namedGroups=kyber1024 -cp ./;./libs/* SSLClientExample
java -Xms1024m -Xmx1024m -Djava.security.properties=test.security -Djava.net.preferIPv4Stack=true -Djdk.tls.namedGroups=x25519Kyber768 -cp ./;./libs/* SSLClientExample