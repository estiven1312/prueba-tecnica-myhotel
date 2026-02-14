# Development

## Configuracion requerida

Antes de ejecutar el proyecto, asegurate de tener una configuracion basada en el archivo:

- `src/main/resources/application-example.yaml`

Crea tu archivo de configuracion local a partir de ese ejemplo (por ejemplo, `application.yaml`) y ajusta los valores segun tu entorno:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `server.servlet.context-path`
- `server.port`

## Base de datos local (Docker)

El archivo `development/docker-compose.yml` levanta un MySQL local en el puerto `3308`.

Para iniciar la base:

```bash
cd development
docker compose up -d
```

## Ejecucion

```bash
./gradlew bootRun
```

## Docker (build/run)

Para construir la imagen usando el Dockerfile con capas:

```bash
./gradlew bootJar

docker build -t reports-hr-project .
```

Para ejecutar el contenedor:

```bash
docker run --rm -p 8081:8081 \
  -e JAVA_OPTS="-Xms256m -Xmx512m" \
  reports-hr-project
```
