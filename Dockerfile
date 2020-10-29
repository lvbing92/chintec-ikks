FROM alpine:3.11

ARG TZONE
ENV TZONE="${TZONE:-Asia/Shanghai}"

# Timezone
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/${TZONE} /etc/localtime && \
    echo "${TZONE}" > /etc/timezone && \
    apk del tzdata && \
    date

RUN apk add --no-cache \
      openjdk11

COPY docker/entrypoint.sh /usr/local/bin/entrypoint
RUN chmod +x /usr/local/bin/entrypoint

WORKDIR /app

ARG JAR_PATH
ENV JAR_PATH="${JAR_PATH}"

COPY ${JAR_PATH} /app/app.jar

ENTRYPOINT [ "entrypoint" ]