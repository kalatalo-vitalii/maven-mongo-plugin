package me.vit.maven.mojo;

import me.vit.maven.exception.MongoPluginExecutionException;
import me.vit.maven.exception.MongoPluginInitializationException;
import me.vit.maven.migrate.Migrator;
import me.vit.maven.migrate.options.DestinationOptions;
import me.vit.maven.migrate.options.SourceOptions;
import me.vit.maven.migrate.version.apply.ScriptApplier;
import me.vit.maven.migrate.version.apply.VersionApplier;
import me.vit.maven.migrate.version.provider.VersionScriptProvider;
import me.vit.maven.migrate.version.provider.impl.AllAfterLastAppliedVersionScriptProvider;
import me.vit.maven.migrate.version.provider.impl.AllNotAppliedVersionScriptProvider;
import me.vit.maven.migrate.version.reader.VersionDocumentReader;
import me.vit.maven.migrate.version.reader.VersionScriptReader;
import me.vit.maven.migrate.version.reader.impl.FileVersionScriptReader;
import me.vit.maven.migrate.version.reader.impl.ShellVersionDocumentReader;
import me.vit.maven.shell.Shell;
import me.vit.maven.shell.ShellBuilder;
import me.vit.maven.shell.options.ShellOptions;
import me.vit.maven.util.StringUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;

import java.io.File;

/**
 * Migrate MongoDB using the specified migration scripts directory and the target database
 */
@Mojo(
        name = "migrate",
        requiresProject = false
)
public class MigrateMojo extends AbstractMojo {
    /**
     * MongoDB shell home: a directory with 'bin' directory having 'mongo' inside it
     */
    @Parameter(
            property = "maven.mongo.shellHome"
    )
    private String shellHome;

    @Parameter(
            property = "maven.mongo.host",
            defaultValue = "localhost"
    )
    private String host;

    @Parameter(
            property = "maven.mongo.port",
            defaultValue = "27017"
    )
    private String port;

    @Parameter(
            required = true,
            property = "maven.mongo.database"
    )
    private String database;

    /**
     * Server id located in the Maven settings.xml
     */
    @Parameter(
            property = "maven.mongo.serverId"
    )
    private String serverId;

    @Parameter(
            property = "maven.mongo.user"
    )
    private String user;

    @Parameter(
            property = "maven.mongo.password"
    )
    private String password;

    /**
     * defaults to 'database' parameter
     */
    @Parameter(
            property = "maven.mongo.authenticationDatabase"
    )
    private String authenticationDatabase;
    /**
     * a collection name where to store the applied versions
     */
    @Parameter(
            required = true,
            property = "maven.mongo.versionsCollection"
    )
    private String versionsCollection;

    /**
     * a document field name where to store applied version number
     */
    @Parameter(
            defaultValue = "number",
            property = "maven.mongo.versionNumberField"
    )
    private String versionNumberField;

    /**
     * a document field name where to store applied version number parts - all versions comparison is doing on that field
     */
    @Parameter(
            defaultValue = "numberParts",
            property = "maven.mongo.versionNumberPartsField"
    )
    private String versionNumberPartsField;

    /**
     * a document field name where to store applied version name
     */
    @Parameter(
            defaultValue = "name",
            property = "maven.mongo.versionNameField"
    )
    private String versionNameField;

    /**
     * a directory containing the .js scripts; a searching in this directory is not recursive
     */
    @Parameter(
            required = true,
            property = "maven.mongo.scriptsDir"
    )
    private File scriptsDir;

    /**
     * a separator to distinguish version number and name
     */
    @Parameter(
            defaultValue = "__",
            property = "maven.mongo.versionAndNameSeparator"
    )
    private String versionAndNameSeparator;

    /**
     * a script apply strategy; one of:
     * newest - apply all after last applied
     * all    - apply all not applied yet
     */
    @Parameter(
            defaultValue = "newest",
            property = "maven.mongo.applyStrategy"
    )
    private String applyStrategy;

    @Parameter(
            defaultValue = "${settings}",
            readonly = true
    )
    private Settings settings;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Migrator migrator = buildMigrator();
            getLog().debug("migrator build successfully");
            migrator.migrate();

        } catch (MongoPluginInitializationException e) {
            throw new MojoFailureException("'migrate' mojo initialization error", e);

        } catch (MongoPluginExecutionException e) {
            throw new MojoExecutionException("'migrate' mojo execution error", e);
        }
    }

    private Migrator buildMigrator() {
        ShellOptions shellOptions = buildShellOptions();
        if (shellOptions.getAuthentication() == null) {
            getLog().info("shell options without authentication build:");
        } else {
            getLog().info("shell options with authentication build:");
        }
        getLog().info("  host    : " + shellOptions.getHost());
        getLog().info("  port    : " + shellOptions.getPort());
        getLog().info("  database: " + shellOptions.getDb());

        SourceOptions srcOptions = buildSourceOptions();
        DestinationOptions dstOptions = buildDestinationOptions();

        Shell shell = new ShellBuilder().home(shellHome).options(shellOptions).build();
        VersionScriptReader scriptReader = new FileVersionScriptReader(srcOptions);
        VersionDocumentReader documentReader = new ShellVersionDocumentReader(shell, dstOptions);
        VersionApplier versionApplier = new VersionApplier(shell, dstOptions);
        ScriptApplier scriptApplier = new ScriptApplier(shell);
        VersionScriptProvider scriptProvider = instantiateVersionScriptProvider(scriptReader, documentReader);

        return new Migrator(scriptProvider, scriptApplier, versionApplier);
    }

    private ShellOptions buildShellOptions() {
        Server server = settings.getServer(serverId);
        if (server != null) {
            user = server.getUsername();
            password = server.getPassword();
        }
        return new ShellOptions.Builder()
                .host(host)
                .port(port)
                .database(database)
                .user(user)
                .password(password)
                .authenticationDatabase(StringUtil.isNotBlank(authenticationDatabase) ? authenticationDatabase : database)
                .build();
    }

    private SourceOptions buildSourceOptions() {
        return new SourceOptions.Builder()
                .scriptsDir(scriptsDir)
                .versionAndNameSeparator(versionAndNameSeparator)
                .build();
    }

    private DestinationOptions buildDestinationOptions() {
        return new DestinationOptions.Builder()
                .collection(versionsCollection)
                .numberField(versionNumberField)
                .numberPartsField(versionNumberPartsField)
                .nameField(versionNameField)
                .build();
    }

    private VersionScriptProvider instantiateVersionScriptProvider(
            VersionScriptReader scriptReader, VersionDocumentReader documentReader) {
        if ("newest".equals(applyStrategy)) {
            return new AllAfterLastAppliedVersionScriptProvider(scriptReader, documentReader);

        } else if ("all".equals(applyStrategy)) {
            return new AllNotAppliedVersionScriptProvider(scriptReader, documentReader);

        } else {
            return new AllAfterLastAppliedVersionScriptProvider(scriptReader, documentReader);
        }
    }
}
