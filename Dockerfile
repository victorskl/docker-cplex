FROM openjdk:8-jre

COPY cplex_studio128.linux-x86-64.bin /app/cplex_studio128.linux-x86-64.bin
COPY response.properties /app/response.properties
COPY src/ /app/

WORKDIR /app

RUN chmod u+x cplex_studio128.linux-x86-64.bin
RUN ./cplex_studio128.linux-x86-64.bin -f response.properties

ENTRYPOINT ["java","-Djava.library.path=/opt/ibm/ILOG/CPLEX_Studio128/cplex/bin/x86-64_linux","-cp",".:/opt/ibm/ILOG/CPLEX_Studio128/cplex/lib/cplex.jar","HelloCplex"]
