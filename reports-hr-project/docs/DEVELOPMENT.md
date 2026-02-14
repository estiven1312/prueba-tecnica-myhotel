# Development

## Configuracion requerida

Antes de ejecutar el proyecto, asegurate de tener una configuracion basada en el archivo:

- `src/main/resources/application.yaml`

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

Nota:

- Debe asegurarse de configurar con los valores correctos en `application.yaml` para conectar con la base de datos
  local (puerto `3308`).

## Docker Compose (app + mysql - MODO RECOMENDADO)

Para levantar toda la solucion con Docker Compose, primero genera el JAR y luego inicia los servicios desde la raiz:

```bash
# Generar el JAR (se usa en el Dockerfile) y en docker compose con build.args.JAR_FILE
./gradlew bootJar

# Levantar app + mysql
docker compose up --build

# Para detener los servicios
docker compose down -v
```

Notas:

- `docker-compose.yml` en la raiz levanta `app` + `mysql` y usa `development/init`.
- El `Dockerfile` consume `JAVA_OPTS` (puedes exportarlo o pasarlo en el compose) y `JAR_FILE` que es el compilado.
