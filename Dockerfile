#Команда для билда:
#docker build -t warehouse-front .
FROM tomcat:latest
ADD target/warehouse-front.war /usr/local/tomcat/webapps/
RUN sed -i 's/port="8080"/port="4447"/' ${CATALINA_HOME}/conf/server.xml
CMD ["catalina.sh", "run"]
