/*********************************************
 * OPL 12.8.0.0 Model
 * Author: victorskl
 * Creation Date: 31 Jan 2018 at 1:49:20 pm
 *********************************************/
 
// tute from 00:00 - 19:40 of https://www.youtube.com/watch?v=4VZgBiDOAC4 
// use oplide to run the model

// variables

dvar float+ x;
dvar float+ y;

// expressions

dexpr float cost = 0.12*x + 0.15*y;

// model

minimize cost;
//maximize cost; // get "Primal unbounded due to dual bounds, variable 'x'." in Engine log
subject to {

	// constraints

	cons01:
	60*x + 60*y >= 300;
	
	cons02:
	12*x + 6*y >= 36;
	
	cons03:
	10*x + 30*y >= 90;
	
}

// post-processing - look the writeln output in Scripting log

execute {
	if (cplex.getCplexStatus() == 1) {
		writeln("reduced cost of x: ", x.reducedCost);
		writeln("reduced cost of y: ", y.reducedCost);
		writeln("dual of cons01: ", cons01.dual);
		writeln("dual of cons02: ", cons02.dual);
		writeln("dual of cons03: ", cons03.dual);
	} else {
		writeln("ERROR: solution not found");
	}
}

