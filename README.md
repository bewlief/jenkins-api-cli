[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.xtech/jenkins.client.java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.xtech/jenkins.client.java)

# jenkins-client-java

Java binding for the Jenkins client.

[sonar](https://sonarcloud.io/dashboard?id=com.xtech.ci%3Ajenkins.client.java)

# How to use it

Add the following dependency to the pom.xml file of your project:

```xml

<dependency>
    <groupId>com.xtech</groupId>
    <artifactId>jenkins.client.java</artifactId>
    <version>1.0.0-20171217</version>
</dependency>
```

# Example of get all jobs from jenkins

```java
import com.xtech.jenkins.client.Jenkins;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author xtech
 */
public class Demo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URI serverURI = new URI("http://localhost:8080/jenkins");
        Jenkins jenkins = new Jenkins(serverURI, "admin", "admin");

        Jobs jobMgr = jenkins.getJobs();
        List<Job> allJobs = jobMgr.getAllJobs();

        for (Job job : allJobs) {
            System.out.println(job.getName());
        }
    }
}
```

# Example of get all installed plugins from jenkins

```java
import com.xtech.jenkins.client.Jenkins;
import com.xtech.jenkins.client.model.plugin.Plugin;
import com.xtech.jenkins.client.helper.Plugins;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author xtech
 */
public class Demo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URI serverURI = new URI("http://localhost:8080/jenkins");
        Jenkins jenkins = new Jenkins(serverURI, "admin", "admin");

        Plugins pluginMgr = jenkins.getPlugins();
        List<Plugin> allInstalledPlugins = pluginMgr.getPluginManager().getPlugins();
        for (Plugin plugin : allInstalledPlugins) {
            System.out.println(plugin.getShortName());
        }
    }
}
```

# Example of get all credentials from jenkins

```java
import com.xtech.jenkins.client.Jenkins;
import com.xtech.jenkins.client.model.credential.Credential;
import com.xtech.jenkins.client.helper.Credentials;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author xtech
 */
public class Demo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URI serverURI = new URI("http://localhost:8080/jenkins");
        Jenkins jenkins = new Jenkins(serverURI, "admin", "admin");

        Credentials credentialMgr = jenkins.getCredentials();
        Map<String, Credential> credentialMap = credentialMgr.list();
        for (String key : credentialMap.keySet()) {
            System.out.println(credentialMap.get(key).getDescription());
        }
    }
}
```

# Compile & Package

If you want to compile project, you can via `mvn clean compile`

If you want to package project and skip the junit test, you can via `mvn clean package -DskipTest`
