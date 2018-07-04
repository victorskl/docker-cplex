FROM openjdk:8-jre

COPY cplex_studio128.linux-x86-64.bin /cplex/cplex_studio128.linux-x86-64.bin
COPY response.properties /cplex/response.properties

RUN chmod u+x /cplex/cplex_studio128.linux-x86-64.bin
RUN /cplex/cplex_studio128.linux-x86-64.bin -f /cplex/response.properties
RUN rm -rf /cplex
