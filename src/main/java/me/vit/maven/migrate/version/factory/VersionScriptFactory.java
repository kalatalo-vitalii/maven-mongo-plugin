package me.vit.maven.migrate.version.factory;

import me.vit.maven.exception.execute.VersionScriptBuildException;
import me.vit.maven.migrate.version.VersionScript;
import me.vit.maven.util.StringUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionScriptFactory extends AbstractVersionFactory {
    private static final Pattern VERSION_NUMBER_FIND_PATTERN = Pattern.compile("^[0-9.]+");
    private Pattern versionNameFindPattern;

    public VersionScriptFactory(String numberAndNameSeparator) {
        this.versionNameFindPattern = Pattern.compile(String.format("^[0-9.]+%s", numberAndNameSeparator));
    }

    public VersionScript versionFile(File file) {
        if (file == null) {
            throw new VersionScriptBuildException("no script file specified");
        }
        String fileName = file.getName().replace(".js", "");

        String number = getVersionNumber(fileName);
        int[] parts = getVersionNumberParts(number);
        String name = getVersionName(fileName);

        return new VersionScript(number, parts, name, file);
    }

    String getVersionNumber(String scriptFileName) {
        if (StringUtil.isBlank(scriptFileName)) {
            throw new VersionScriptBuildException("script filename is empty");
        }
        Matcher matcher = VERSION_NUMBER_FIND_PATTERN.matcher(scriptFileName);
        if (matcher.find()) {
            return scriptFileName.substring(matcher.start(), matcher.end());
        } else {
            throw new VersionScriptBuildException("no version number found in the script filename: " + scriptFileName);
        }
    }

    String getVersionName(String scriptFileName) {
        Matcher matcher = versionNameFindPattern.matcher(scriptFileName);
        String versionNameWithFileExtension = matcher.find() ?
                scriptFileName.substring(matcher.end(), scriptFileName.length()) : "";
        return versionNameWithFileExtension.split("\\.")[0].replace("_", " ");
    }
}
