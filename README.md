# Keycloak SPI CustomUserProvider & CustomAuthenticator
This Keycloak Service Provider Interface (SPI) is designed to fetch user information from Oracle Database and provide a flexible way to integrate user data into your keycloak. It is also to expand keycloak to provide login functions such as log in with other input parameters in addition to username and password. 

## Features
- Retrieve user details from oracle database using the Keycloak SPI framework.
- Display user information like Roles, Groups and other attributes fetched from database in Keycloak and applying them in authorization process.
- Develop a custom authenticator which login with three parameters.
- Configure the Clients in keycloak to use the custom authenticator.
- Flexible and customizable to meet your specific needs.
- Easy integration with your existing Keycloak configuration version 12.0.4.

## Installation
To use this Keycloak SPI User Fetcher, follow these steps:
- Clone the repository: git clone https://github.com/your-username/keycloak-spi-user-fetcher.git
- Build the project: `mvn clean install`
recomendation: keycloak needs all dependencies used in the SPI. To deploy all those packed in one jar file it is convenient to use this configuration in the POM.XML:

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

## Configuration
To configure the Keycloak SPI User Fetcher, follow these steps:
- In your Keycloak admin console, go to the your Realm and in left panel select `User Federation`.
- Click on the `Add Provider` button and select the custom user provider name.
- Save the configuration and enable the SPI.
- Test the integration by fetching user data using the provided API. It is required to go to the Users section in left panel. Then click on `view all users`. you can search a fullname or partial name to acheive the specific user.



Clicking on a user, new form comes up to give other user details. in Groups, RoleMappings, Attributes and Credentials, all details can be seen. also if you need this information to apply a policy against a resource in Authorization section of a client, it is also accessable:


Authenticator
to install custom Authenticator it is time to go to the Authenticatin section. here you can see different Authentication flows. Click on New then name it and save. Click on add execution to create a following flow:


after then, this Authentication flow can be set for any Client settings. go to the specific client. in settings tab. under Authenticatin flow overrides set your fresh Authenticator.
Now you can get token through password grant type with new Authenticator that accept three parameters to login.  here we have username, password and banch :

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
