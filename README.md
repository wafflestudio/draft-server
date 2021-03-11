# draft-server [![Build Status](https://travis-ci.com/wafflestudio/draft-server.svg?token=siV9jNpC1p2FuLSyqUnp&branch=master)](https://travis-ci.com/wafflestudio/draft-server)

### Temporarily Discontinued

- How to set up?
```
psql -c 'CREATE DATABASE draft;' -U postgres
psql -c "CREATE USER draft PASSWORD 'draft-pw';"
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
- devel: https://draft.wafflestudio.com
- prod: not yet

### API documentation
- **https://draft-waffle.atlassian.net/wiki/spaces/DRAFTWAFFL/pages/33108/API+Documentation**
- https://draft.wafflestudio.com/swagger-ui.html
- https://github.com/wafflestudio/draft-server/wiki/Api-Specification

### Wiki
- https://github.com/wafflestudio/draft-server/wiki
