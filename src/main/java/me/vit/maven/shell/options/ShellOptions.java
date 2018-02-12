package me.vit.maven.shell.options;

import me.vit.maven.exception.build.ShellOptionsBuildException;
import me.vit.maven.util.StringUtil;

public class ShellOptions {
    private String host;
    private String port;
    private String db;
    private Authentication authentication;

    private ShellOptions(String host, String port, String db, Authentication authentication) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.authentication = authentication;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDb() {
        return db;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public static class Builder {
        private String host;
        private String port;
        private String database;
        private String user;
        private String password;
        private String authenticationDatabase;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(String port) {
            this.port = port;
            return this;
        }

        public Builder database(String database) {
            this.database = database;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder authenticationDatabase(String authenticationDatabase) {
            this.authenticationDatabase = authenticationDatabase;
            return this;
        }

        public ShellOptions build() {
            if (StringUtil.isBlank(host)) {
                throw new ShellOptionsBuildException("no 'host' specified");
            }
            if (StringUtil.isBlank(port)) {
                throw new ShellOptionsBuildException("no 'port' specified");
            }
            if (StringUtil.isBlank(database)) {
                throw new ShellOptionsBuildException("no 'database' specified");
            }
            return new ShellOptions(
                    host,
                    port,
                    database,
                    new Authentication.Builder()
                            .user(user)
                            .password(password)
                            .db(authenticationDatabase)
                            .build()
            );
        }
    }
}
