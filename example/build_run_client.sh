classpath=./libs/bcpkix-jdk18on-177b05.jar:./libs/bcprov-jdk18on-177b05.jar:./libs/bctls-jdk18on-177b05.jar:./libs/bcutil-jdk18on-177b05.jar

rm -v ./SSLClientExample*.class
javac -cp ${classpath} SSLClientExample.java

java -Xms1024m -Xmx1024m -Djava.net.preferIPv4Stack=true -Djdk.tls.namedGroups=x25519Kyber768 -cp ./:./libs/* SSLClientExample

