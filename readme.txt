A maven plugin for MongoDB which applies the specified .js files to migrate DB.
Features:
    1) uses MongoDB shell allowing to work with any mongo instance;
    2) allows optional authentication

goals:

    version - shows MongoDB shell version for the available shell
    migrate - applies .js files to MongoDB to migrate the database


configuration:

    see "plugin.xml" inside the built .jar

     example:

        <plugin>
            <groupId>com.itictory.maven</groupId>
            <artifactId>mongo-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <configuration>
                <database>test</database>
                <shellHome>d:\dev\mongo\3.4\</shellHome>
                <port>27018</port>
                <database>calce-test</database>
                <user>calce-test</user>
                <password>calce-test</password>
                <authenticationDatabase>calce-test</authenticationDatabase>
                <versionsCollection>version</versionsCollection>
                <scriptsDir>d:\code\calce\calce-backend\scripts\mongodb.migration\</scriptsDir>
            </configuration>
        </plugin>

build:
        mvn mongo -package -DskipTests

        * a source POM contains some test data, so omitting the tests is to avoid test environment set up.


history:

        1.0 - initial version

        1.1 - bug fixes, logging readability improvements, versions apply strategies

        1.2 - general improvements

            features:
                1) update mojos to allow running without a project
                2) expose mojos configuration to the command line

            bug fixes:
                1) fix run on db having no versions applied yet