# Keycloak SPI CustomUserProvider & CustomAuthenticator
This Keycloak Service Provider Interface (SPI) is designed to fetch user information from Oracle Database and provide a flexible way to integrate user data into your Keycloak. It is also to expand Keycloak to provide login functions such as login with other input parameters in addition to username and password. 

## Features
- Retrieve user details from an Oracle database using the Keycloak SPI framework.
- Display user information like Roles, Groups, and other attributes fetched from the database in Keycloak and apply them in the authorization process.
- Develop a custom authenticator that login with three parameters.
- Configure the Clients in Keycloak to use the custom authenticator.
- Flexible and customizable to meet your specific needs.
- Easy integration with your existing Keycloak configuration version 12.0.4.

## Installation
To use this Keycloak SPI User Fetcher, follow these steps:
- Clone the repository: git clone https://github.com/JavadMalekzadeh/Keycloak_SPI_CustomUserProviderWithCustomAuthenticator.git
- Build the project: `mvn clean install`
- ✨recomendation: keycloak needs all dependencies used in the SPI. To deploy all those packed in one jar file it is convenient to use this configuration in the POM.XML:

```sh
<plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.3.0</version>
        <executions>
          <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
          </descriptorRefs>
        </configuration>
      </plugin>
</plugins>
```
- Then you must run the following maven goal: `mvn clean install assembly:single`

- Deploy the resulting JAR file with name: ` *-jar-with-dependecies.jar` into your Keycloak server. In keycloak `12.0.4` you should route to `/keycloak12.0.4/standalone/deployments` for deployment and past the jar file there.

# Configuration
## UserStorageProvider
To configure the Keycloak SPI User Fetcher, follow these steps:
- In your Keycloak admin console, go to your Realm, and in the left panel select `User Federation`.
- Click on the `Add Provider` button and select the custom user provider name.
- Save the configuration and enable the SPI.
- Test the integration by fetching user data using the provided API. It is required to go to the Users section in the left panel. Then click on `view all users`. you can search for a full name or partial name to achieve the specific user.



Clicking on a user, a new form comes up to give further user details. in Groups, RoleMappings, Attributes, and Credentials, all details can be seen. also if you need this information to apply a policy against a resource in the Authorization section of a specific Client, it is accessible:


## Authenticator
To install the custom Authenticator it is time to go to the Authentication section. here you can see different Authentication flows. Click on New button then name it and save. Click on `Add execution` to create the following flow:


After then, this Authentication flow can be set for any Client settings. go to the specific client. in `Settings` tab. under `Authentication flow overrides` set your new fresh Authenticator.
Now you can get a token through password grant type with the Authenticator that accepts three parameters to login.  here we have username, password, and banch :

```sh
curl --location 'http://localhost:8080/auth/realms/Banco/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=username' \
--data-urlencode 'password=pass' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=MyClient_Id' \
--data-urlencode 'client_secret=MyClient_Secret' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=openid profile' \
--data-urlencode 'branch=0100'
```
