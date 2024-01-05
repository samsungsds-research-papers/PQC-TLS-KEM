del SSLClientExample.class
javac -cp ./libs/* SSLClientExample.java
java -Xms1024m -Xmx1024m -Djava.net.preferIPv4Stack=true -Djdk.tls.namedGroups=x25519Kyber768 -cp ./;./libs/* SSLClientExample
