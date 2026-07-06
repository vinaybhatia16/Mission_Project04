FROM tomcat:11.0

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ORSProject04.war /usr/local/tomcat/webapps/ORSProject04.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
