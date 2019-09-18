mvnw clean & mvnw.cmd & docker-compose -f ./docker-compose.yaml down -v & docker-compose -f ./docker-compose.yaml up -V --build --force-recreate --always-recreate-deps --remove-orphans
