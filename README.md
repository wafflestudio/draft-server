# draft-server
[![Build Status](https://travis-ci.com/wafflestudio/draft-server.svg?token=siV9jNpC1p2FuLSyqUnp&branch=master)](https://travis-ci.com/wafflestudio/draft-server)
- How to run?
```shell script
mysql.server start
mysql -e "CREATE DATABASE draft;"
mysql -e "CREATE USER 'draft-admin'@'localhost' IDENTIFIED BY 'draft-pw';"
mysql -e "GRANT ALL PRIVILEGES ON *.* to 'draft-admin'@'localhost';"
./gradlew clean build
./gradlew bootRun
```

