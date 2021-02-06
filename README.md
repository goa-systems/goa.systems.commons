# GOA systems commons

A library containing common code snippets to reduce duplicate implementations of often used code.

## Compatibility

JDK >= 1.8

## Continous integration

The task ```localPublish``` exports the generated jar files into the folder "build/remote". Continous integration pipelines can grab them from there and deploy them to a [Maven](https://maven.apache.org) repository for example.

Such a task could look like this

```bash
scp -r build/remote user@remote-server:/home/user/remote
ssh user@remote-server 'mvn deploy:deploy-file \
-Dfile=remote/commons-0.0.1.jar \
-Dsources=remote/commons-0.0.1-sources.jar \
-Djavadoc=remote/commons-0.0.1-javadoc.jar \
-DgroupId=goa.systems \
-DartifactId=commons \
-Dversion=0.0.1 \
-Dpackaging=jar \
-DgeneratePom=true \
-DcreateChecksum=true \
-Durl=file://m2repo'
ssh user@remote-server 'rm -rf /home/user/remote'
```

This task copys the generated jar files to the remote server, imports them using Maven and deletes the temporary directory on the server.