FROM cplex:12.8

COPY src/ /app/

WORKDIR /app

ENTRYPOINT ["java","-Djava.library.path=/opt/ibm/ILOG/CPLEX_Studio128/cplex/bin/x86-64_linux","-cp",".:/opt/ibm/ILOG/CPLEX_Studio128/cplex/lib/cplex.jar","HelloCplex"]
