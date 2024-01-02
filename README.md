# S-PQC-TLS
PQC-KEM to BouncyCastle java (v1.76) TLSv1.3 module


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
#### build bc-java with gradle (or ant)

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
classpath=${bc-java}/pkix/build/libs/bcpkix-jdk18on-175.jar:${bc-java}/tls/build/libs/bctls-jdk18on-175.jar:${bc-java}/core/build/libs/core-175.jar:${bc-java}/prov/build/libs/bcprov-jdk18on-175.jar:${bc-java}/util/build/libs/bcutil-jdk18on-175.jar

javac -cp ${classpath} TestServer.java
```

#### Step 2 : Run java application with jdk.tls.namedGroups option
```
java -jar -Djdk.tls.namedGroups=secp384Kyber768 TestServer.jar
```
