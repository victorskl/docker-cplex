import ilog.concert.*;
import ilog.cplex.IloCplex;

public class HelloCplex {

    public static void main(String[] args) throws InterruptedException {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Shutting down...")));

        while (true) {
            example1();
            Thread.sleep(1000);
            System.out.println();
            example2();
            Thread.sleep(5000);
            System.out.println();
        }
    }

    private static void example1() {
        try {

            IloCplex cplex = new IloCplex();

            // create model and solve it

            // variables
            IloNumVar[] x = cplex.numVarArray(3, 0.0, 100.0);

            // create expression x[0] + 2*x[1] + 3*x[2]
            IloNumExpr expr = cplex.sum(x[0], cplex.prod(2.0, x[1]), cplex.prod(3.0, x[2]));

            // model
            cplex.addMinimize(expr);

            // add constraints -x[0] + x[1] + x[2] <= 20.0
            cplex.addLe(cplex.sum(cplex.negative(x[0]), x[1], x[2]), 20);

            // solve and print
            boolean found = cplex.solve();

            System.out.println("Solution is: " + cplex.getCplexStatus().toString());

            if (found) {

                double objectiveValue = cplex.getObjValue();
                System.out.println("Objective Value: " + objectiveValue);

            } else {
                System.out.println("Solution not found!");
            }

        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }

    // CPLEX Java version of OPL tute1.mod
    private static void example2() {
        try {

            IloCplex cplex = new IloCplex();

            IloNumVar x = cplex.numVar(0.0d, Double.MAX_VALUE, IloNumVarType.Float);
            IloNumVar y = cplex.numVar(0.0d, Double.MAX_VALUE, IloNumVarType.Float);

            IloNumExpr cost = cplex.sum(cplex.prod(0.12, x), cplex.prod(0.15, y));

            cplex.addMinimize(cost);

            IloRange cons01 = cplex.addGe(cplex.sum(cplex.prod(60,x), cplex.prod(60,y)), 300);
            IloRange cons02 = cplex.addGe(cplex.sum(cplex.prod(12, x), cplex.prod(6,y)), 36);
            IloRange cons03 = cplex.addGe(cplex.sum(cplex.prod(10, x), cplex.prod(30, y)), 90);

            boolean found = cplex.solve();

            System.out.println("Solution is: " + cplex.getCplexStatus().toString());

            if (found) {

                double objectiveValue = cplex.getObjValue();
                System.out.println("Objective Value: " + objectiveValue);

                System.out.println();
                System.out.println("Solutions: ");
                System.out.println("\tx: " + cplex.getValue(x));
                System.out.println("\ty: " + cplex.getValue(y));

                System.out.println();
                System.out.println("Reduced cost of x: " + cplex.getReducedCost(x));
                System.out.println("Reduced cost of y: " + cplex.getReducedCost(y));

                System.out.println();
                System.out.println("Dual of cons01: " + cplex.getDual(cons01));
                System.out.println("Dual of cons02: " + cplex.getDual(cons02));
                System.out.println("Dual of cons03: " + cplex.getDual(cons03));

            } else {
                System.out.println("Solution not found!");
            }

        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }
}
