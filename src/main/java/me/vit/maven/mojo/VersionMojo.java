package me.vit.maven.mojo;

import me.vit.maven.exception.MongoPluginExecutionException;
import me.vit.maven.exception.MongoPluginInitializationException;
import me.vit.maven.shell.Shell;
import me.vit.maven.shell.ShellBuilder;
import me.vit.maven.util.StringUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Obtains MongoBB shell version
 */
@Mojo(
        name = "version",
        requiresProject = false
)
public class VersionMojo extends AbstractMojo {
    /**
     * MongoDB shell home: a directory with 'bin' directory having 'mongo' inside it
     */
    @Parameter(
            property = "maven.mongo.shellHome"
    )
    private String shellHome;

    public void execute() throws MojoExecutionException, MojoFailureException {
        String shellVersion;
        try {
            Shell shell = new ShellBuilder().home(shellHome).build();
            String versionOutput = shell.version();
            shellVersion = getFirstLine(versionOutput);

        } catch (MongoPluginInitializationException e) {
            throw new MojoFailureException("'version' mojo initialization error", e);

        } catch (MongoPluginExecutionException e) {
            throw new MojoExecutionException("'version' mojo execution error", e);
        }
        getLog().info(shellVersion);
    }

    private static String getFirstLine(String multilineString) {
        return StringUtil.isBlank(multilineString) ?
                "" : multilineString.substring(0, multilineString.indexOf("\n"));
    }
}