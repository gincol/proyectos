FROM tomcat:8.5.34

COPY apivigiafrontal/apivigiafrontalWeb/target/apivigiafrontal.war /usr/local/tomcat/webapps/apivigiafrontal.war
COPY apivigiafrontal/cloud/docker/server.xml /usr/local/tomcat/conf/
COPY apivigiafrontal/cloud/docker/context.xml /usr/local/tomcat/conf/

EXPOSE 8080
