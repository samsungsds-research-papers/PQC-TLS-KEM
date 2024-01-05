del SSLServerExample.class
javac -cp ./libs/* SSLServerExample.java
java -Xms1024m -Xmx1024m -Dtest.keystore.name=secp256r1 -Djava.net.preferIPv4Stack=true -Djdk.tls.namedGroups=secp256r1,x25519Kyber768 -cp ./;./libs/* SSLServerExample
