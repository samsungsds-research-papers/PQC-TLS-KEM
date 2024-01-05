del SSLServerExample.class
javac -cp ./libs/* SSLServerExample.java
java -Xms1024m -Xmx1024m -Dtest.keystore.name=secp256r1 -Djava.net.preferIPv4Stack=true -Djdk.tls.namedGroups=secp256r1,kyber512,kyber768,kyber1024,secp256Kyber512,secp384Kyber768,secp521Kyber1024,x25519Kyber512,x25519Kyber768,x448Kyber768 -cp ./;./libs/* SSLServerExample
