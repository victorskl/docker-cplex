# docker-cplex

[![DOI](https://zenodo.org/badge/121027722.svg)](https://zenodo.org/badge/latestdoi/121027722)

IBM ILOG CPLEX deployment with Docker. The [demo Java code](src/HelloCplex.java) to use the CPLEX through [Concert API Technology](https://www.ibm.com/support/knowledgecenter/en/SSSA5P_12.8.0/ilog.odms.cplex.help/CPLEX/UsrMan/topics/APIs/Java/06_modeling_title_synopsis.html). This will end up the embedding use on CPLEX runtime component.

### On Local Development

- Download and install [IBM ILOG CPLEX Studio](https://www.ibm.com/au-en/marketplace/ibm-ilog-cplex/purchase#product-header-top)
- On macOS: (other platform, adjust the path)

```
cd src
javac -cp .:/Applications/CPLEX_Studio128/cplex/lib/cplex.jar HelloCplex.java
java -Djava.library.path=/Applications/CPLEX_Studio128/cplex/bin/x86-64_osx -cp .:/Applications/CPLEX_Studio128/cplex/lib/cplex.jar HelloCplex

Press CTRL+C to terminate
```

- Example output as follow:
```
Tried aggregator 1 time.
LP Presolve eliminated 1 rows and 3 columns.
All rows and columns eliminated.
Presolve time = 0.00 sec. (0.00 ticks)
Solution is: Optimal
Objective Value: 0.0

Tried aggregator 1 time.
No LP presolve or aggregator reductions.
Presolve time = 0.00 sec. (0.00 ticks)

Iteration log . . .
Iteration:     1   Dual objective     =             0.600000
Solution is: Optimal
Objective Value: 0.6599999999999999

Solutions:
	x: 3.0
	y: 2.0

Reduced cost of x: 0.0
Reduced cost of y: 0.0

Dual of cons01: 0.0017500000000000003
Dual of cons02: -0.0
Dual of cons03: 0.0014999999999999996
```

### On Docker Deployment

#### Build CPLEX JRE Docker Image

- The docker container image will be Debian based [openjdk:8-jre](https://hub.docker.com/_/openjdk/).

- Copy the downloaded CPLEX Linux installer `cplex_studio128.linux-x86-64.bin` to the same location with `cplex/Dockerfile`.

  ```
  tree cplex
  cplex
  ├── Dockerfile
  ├── cplex_studio128.linux-x86-64.bin
  └── response.properties  
  ```

- Build the image:
  
  ```
  cd cplex
  docker image build -t cplex:12.8 -t cplex:12.8.0 .
  docker images|grep cplex
  cplex    12.8       3b473e7d9ec3        About a minute ago   3.43GB
  cplex    12.8.0     3b473e7d9ec3        About a minute ago   3.43GB
  ```

- _(Optional)_ Publish to private docker image registry:

  ```
  docker tag cplex:12.8 docker-image-registry.com/namespace/cplex:12.8
  docker tag cplex:12.8.0 docker-image-registry.com/namespace/cplex:12.8.0
  
  docker push docker-image-registry.com/namespace/cplex:12.8
  docker push docker-image-registry.com/namespace/cplex:12.8.0
  ```

#### Build Sample Java App

- Make sure to compile the `HelloCplex.java`. This is on macOS. Otherwise adjust the classpath.
    ```
    javac -cp .:/Applications/CPLEX_Studio128/cplex/lib/cplex.jar src/HelloCplex.java
    ```

- Once ready, build the docker image.
    ```
    docker build -t dev_tusk .
    ```

- Then run the container using the image.
    ```
    docker run -it --rm --name tusk dev_tusk
    ```

- Open another terminal and observe into the container.
    ```
    docker images dev_tusk
    docker exec -it tusk bash
    ls -l
    ls -l /opt/ibm/ILOG/CPLEX_Studio128/
    ls -l /opt/ibm/ILOG/CPLEX_Studio128/cplex/lib/
    ls -l /opt/ibm/ILOG/CPLEX_Studio128/cplex/bin/x86-64_linux/
    
    java -Djava.library.path=/opt/ibm/ILOG/CPLEX_Studio128/cplex/bin/x86-64_linux -cp .:/opt/ibm/ILOG/CPLEX_Studio128/cplex/lib/cplex.jar HelloCplex
    
    CTRL+C to terminate
    
    exit
    ```

- To stop the running container:
    ```
    CTRL+C in the container running terminal 
    
    Or from the another terminal, enter:
    
        docker stop tusk
    ```

- Clean up if desire
    ```
    docker rm tusk
    docker rmi dev_tusk
    ```

