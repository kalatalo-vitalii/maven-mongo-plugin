package me.vit.maven.shell.options;

import me.vit.maven.util.StringUtil;

public class Authentication {
    private String user;
    private String password;
    private String db;

    private Authentication(String user, String password, String db) {
        this.user = user;
        this.password = password;
        this.db = db;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDb() {
        return db;
    }

    public static class Builder {
        private String user;
        private String password;
        private String db;

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder db(String db) {
            this.db = db;
            return this;
        }

        public Authentication build() {
            if (StringUtil.isNotBlank(user) &&
                    (StringUtil.isNotBlank(password) &&
                            (StringUtil.isNotBlank(db)))) {
                return new Authentication(user, password, db);

            } else {
                return null;
            }
        }
    }
}
