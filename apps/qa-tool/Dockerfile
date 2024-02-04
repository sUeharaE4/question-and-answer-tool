# create dependencies to create custom jre
FROM amazoncorretto:17 as deps

WORKDIR /app
COPY ./target/*.jar /app/app.jar

RUN set -eux; \
    "$JAVA_HOME"/bin/jar -xf app.jar && \
    "$JAVA_HOME"/bin/jdeps \
    --ignore-missing-deps \
    -q \
    --multi-release 17 \
    --recursive \
    --print-module-deps \
    --class-path="./BOOT-INF/lib/*" \
    --module-path="./BOOT-INF/lib/*" \
    ./app.jar > jre-deps.info

# create custom jre
FROM amazoncorretto:17 as jre-builder

WORKDIR /app
COPY --from=deps /app/jre-deps.info jre-deps.info
RUN set -eux; \
    "$JAVA_HOME"/bin/jlink --verbose \
    --compress 2 \
    --strip-java-debug-attributes \
    --no-header-files \
    --no-man-pages \
    --output jre \
    --add-modules "$(cat jre-deps.info)"

FROM debian:buster-20240130-slim
ENV JAVA_HOME /opt/java/openjdk
ENV PATH $JAVA_HOME/bin:$PATH
WORKDIR /app
COPY --from=jre-builder /app/jre $JAVA_HOME
COPY --from=deps /app/app.jar app.jar

ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
