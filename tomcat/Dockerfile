FROM openjdk:17 as builder

RUN --mount=type=bind,src=../,dst=/usr/src/performance,rw \
    --mount=type=cache,target=/root/.m2 \
    cd /usr/src/performance/ && \
    ./mvnw package && \
    cp target/performance-*.war /tmp/performance.war

FROM tomcat:latest

COPY --from=builder /tmp/performance.war /usr/local/tomcat/webapps/

CMD /usr/local/tomcat/bin/catalina.sh run