# S-PQC-TLS
PQC-KEM to BouncyCastle java (v1.76) TLS v1.3 module


- [Status](#status)
  * [Supported NamedGroups](#supported-namedgroups)
- [Quickstart](#quickstart)
  * [Building](#building)
  * [Running](#running)

## Status
#### Supported NamedGroups
- kyber512
- kyber768
- kyber1024
- secp256Kyber512
- secp384Kyber768
- secp521Kyber1024
- x25519Kyber512
- x25519Kyber768
- x448Kyber768

## Quickstart
### Building
#### build bc-java with ant (or gradle)

### Running
#### Step 0 : Insert security provider
- case1 : into the code
```
Security.insertProviderAt(new BouncyCastleProvider(), 1);
Security.insertProviderAt(new BouncyCastleJsseProvider(), 2);		
Security.setProperty("ssl.KeyManagerFactory.algorithm", "PKIX");
Security.setProperty("ssl.TrustManagerFactory.algorithm", "PKIX");
```

- case2 : into the java.security file
```
#
# List of providers and their preference orders (see above):
#
#security.provider.1=SUN
#security.provider.2=SunRsaSign
#security.provider.3=SunEC
#security.provider.4=SunJSSE
#security.provider.5=SunJCE
#security.provider.6=SunJGSS
#security.provider.7=SunSASL
#security.provider.8=XMLDSig
#security.provider.9=SunPCSC
#security.provider.10=JdkLDAP
#security.provider.11=JdkSASL
#security.provider.12=SunMSCAPI
#security.provider.13=SunPKCS11

security.provider.1=org.bouncycastle.jce.provider.BouncyCastleProvider
security.provider.2=org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
security.provider.3=SUN
security.provider.4=SunRsaSign
security.provider.5=SunEC
security.provider.6=SunJSSE
security.provider.7=SunJCE
security.provider.8=SunJGSS
security.provider.9=SunSASL
security.provider.10=XMLDSig
security.provider.11=SunPCSC
security.provider.12=JdkLDAP
security.provider.13=JdkSASL
security.provider.14=SunMSCAPI
security.provider.15=SunPKCS11
```

#### Step 1 : Set the classpath / build java application
```
classpath=./libs/bcpkix-jdk18on-177b05.jar:./libs/bctls-jdk18on-177b05.jar:./libs/bcprov-jdk18on-177b05.jar:./libs/bcutil-jdk18on-177b05.jar
javac -cp ${classpath} SSLServerExample.java
javac -cp ${classpath} SSLClientExample.java
```

#### Step 2 : Run server application with jdk.tls.namedGroups option
```
java -jar -Djdk.tls.namedGroups=secp256r1,kyber512,secp256Kyber512,x25519Kyber768,x448Kyber768 -cp ./libs/* SSLServerExample.jar
```

#### Step 3 : Run client application with jdk.tls.namedGroups option
```
java -jar -Djdk.tls.namedGroups=x25519Kyber768 -cp ./libs/* SSLClientExample.jar
```
