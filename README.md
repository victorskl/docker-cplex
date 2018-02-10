# docker-cplex

IBM ILOG CPLEX deployment with Docker. The [demo Java code](src/HelloCplex.java) to use the CPLEX through Concert API Technology. This will end up the embedding use on CPLEX runtime component.

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

- In [`Dockerfile`](Dockerfile), we will be using [JRE-8](https://hub.docker.com/_/openjdk/) __ONLY__.

- The container image will be Debian base; therefore copy the downloaded CPLEX Linux installer `cplex_studio128.linux-x86-64.bin` to the same location with `Dockerfile`.

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

- EoF