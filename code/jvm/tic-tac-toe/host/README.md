
# Command line reference

## Build images

* `./gradlew buildImageJvm` - builds the JVM image.
* `./gradlew buildImageNginx` - builds the Nginx image.
* `./gradlew buildImagePostgresTest` - builds the PostgreSQL image for testing.
* `./gradlew buildImageUbuntu` - builds an image with Ubuntu and the DIG DNS tool.
* `./gradlew buildImageAll` - builds all images.

## Start, scale, and stop services

* `./gradlew allUp` - starts all services.
* `./gradlew allDown` - stops all services.
* On the `host` folder, `docker compose up -d --scale ttt-jvm-dynamic=3` - scales the dynamic JVM service.

## Nginx

* `docker exec -ti ttt-nginx bash` - open shell on contained running Nginx.
* `nginx -s reload` - reloads Nginx configuration.

## Ubuntu

* `docker exec -ti ubuntu bash` - open shell on contained running Ubuntu.
* `dig ttt-jvm-dynamic` - uses `dig` to resolve the addresses for the `ttt-jvm-dynamic` hostname.


