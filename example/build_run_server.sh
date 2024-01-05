classpath=./libs/bcpkix-jdk18on-177b05.jar:./libs/bcprov-jdk18on-177b05.jar:./libs/bctls-jdk18on-177b05.jar:./libs/bcutil-jdk18on-177b05.jar

rm -v ./SSLServerExample.class
javac -cp ${classpath} SSLServerExample.java

java -Xms1024m -Xmx1024m -Dtest.keystore.name=secp256r1 -Djdk.tls.namedGroups=secp256r1,kyber512,kyber768,kyber1024,secp256Kyber512,secp384Kyber768,secp521Kyber1024,x25519Kyber512,x25519Kyber768,x448Kyber768 -cp ./:./libs/* SSLServerExample

