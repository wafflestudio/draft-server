# draft-server [![Build Status](https://travis-ci.com/wafflestudio/draft-server.svg?token=siV9jNpC1p2FuLSyqUnp&branch=master)](https://travis-ci.com/wafflestudio/draft-server)

- How to set up?
```
mysql.server start
mysql -u root -e "CREATE DATABASE draft;"
mysql -u root -e "CREATE USER 'draft-admin'@'localhost' IDENTIFIED BY 'draft-pw';"
mysql -u root -e "GRANT ALL PRIVILEGES ON *.* to 'draft-admin'@'localhost';"
./gradlew assemble
```

- How to test?
```
./gradlew test
```

- How to run?
```
./gradlew bootRun
```

- How to build?
```
./gradlew clean build
```

## Endpoint
- devel: http://ec2-15-165-158-156.ap-northeast-2.compute.amazonaws.com
- prod: not yet

### API documentation
- http://ec2-15-165-158-156.ap-northeast-2.compute.amazonaws.com/swagger-ui.html
- https://github.com/wafflestudio/draft-server/wiki/Api-Specification

### Wiki
- https://github.com/wafflestudio/draft-server/wiki
