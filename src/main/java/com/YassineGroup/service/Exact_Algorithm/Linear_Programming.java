package com.YassineGroup.service.Exact_Algorithm;

import com.YassineGroup.service.AbstractGraphColoring.Algorithm;
import com.YassineGroup.service.AbstractGraphColoring.GraphColoring;
import com.YassineGroup.service.Graph.Graph;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.cplex.IloCplex;

import java.util.ArrayList;

public class Linear_Programming extends GraphColoring {

    private int[] resultColors;

    public Linear_Programming(Graph g) {
        super(g);
        this.resultColors = new int[V];
    }

    @Override
    public Algorithm executeGraphAlgorithm() {

        try {
            // Instantiate an empty model
            IloCplex model = new IloCplex();

            // Define an array of decision variables
            IloIntVar[][] x = new IloIntVar[V][V];

            for (int i = 0; i < V; i++) {

                // Define each variable's range from 0 to 1
                for (int j = 0; j < V; j++) {
                    x[i][j] = model.intVar(0, 1);
                }
            }

            /*
             * Y is a binary variable (0 or 1) to hold colors. Yi means whether the color i is used or not.
             */
            IloIntVar[] Y = new IloIntVar[V];
            for (int j = 0; j < V; j++) {
                Y[j] = model.intVar(0, 1);
            }

            // Objective: Define the objective function
            IloLinearNumExpr obj = model.linearNumExpr();

            /*
             * Define and add expressions for objective function minimize sum(Yi)
             */
            for (int i = 0; i < V; i++) {
                obj.addTerm(1, Y[i]);
            }

            // Define a minimization problem
            model.addMinimize(obj);

            /*
             * +++++++++++++++++ Define the constraints ++++++++++++++++++++
             * first Constraint: Sum(Xi,k) = 1. Equation ensures that each vertex receives exactly one color.
             */
            for (int i = 0; i < V; i++) { // for each variable
                IloLinearNumExpr constraint = model.linearNumExpr();
                for (int j = 0; j < V; j++) {
                    constraint.addTerm(1, x[i][j]);
                }
                model.add(model.addEq(constraint, 1));
            }

            /*
             * Second Constraint:  Xi,j - Yj <= 1. for all i,k= 1,..,n. The next condition combines the two types of variables,
             * one Vertex can only be colored with color j if Yj = 1.
             */
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    IloLinearNumExpr constraint = model.linearNumExpr();
                    constraint.addTerm(1.0, x[i][j]);
                    constraint.addTerm(-1.0, Y[j]);
                    model.add(model.addLe(constraint, 1));
                }
            }

            /*
             * Third Constraint:  Xic + Xjc <= 1. This restrictions ensures that at most one of the two variables will be true,
             * effectively avoiding color conflicts.
             */
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    for (int j2 = 0; j2 < V; j2++) {
                        IloLinearNumExpr constraint = model.linearNumExpr();
                        if (graph.isEdges(i, j2)) {
                            constraint.addTerm(1.0, x[i][j]);
                            constraint.addTerm(1.0, x[j2][j]);
                            model.add(model.addLe(constraint, 1));
                        }
                    }
                }
            }

            /*
             * Fourth Constraint: 0<= Xi,j , Yk <= 1 for all j,j=1,...,n
             */
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    IloLinearNumExpr constraint = model.linearNumExpr();
                    constraint.addTerm(1.0, x[i][j]);
                    model.add(model.addLe(constraint, 1));
                    model.add(model.addGe(constraint, 0));
                }
            }
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    IloLinearNumExpr constraint = model.linearNumExpr();
                    constraint.addTerm(1.0, Y[i]);
                    model.add(model.addLe(constraint, 1));
                    model.add(model.addGe(constraint, 0));
                }
            }

            /*
             * fifth Constraint: Y[i] -Y[i+1]>=0. This ensures that any k-coloured solution only uses the colours
             * labelled 1 to k.
             */
            for (int i = 0; i < V - 1; i++) {
                IloLinearNumExpr constraint = model.linearNumExpr();
                constraint.addTerm(1.0, Y[i]);
                constraint.addTerm(-1.0, Y[i + 1]);
                model.add(model.addGe(constraint, 0));
            }

            // Suppress the auxiliary output printout
            model.setParam(IloCplex.IntParam.SimDisplay, 0);

            // Solve the model
            boolean isSolved = model.solve();

            if (isSolved) {
                // Print out the objective function
                System.out.println("obj_val = " + model.getObjValue());

                // Fill ArrayList Colors with the used Colors
                ArrayList<Integer> colors = new ArrayList<>();
                for (int i = 0; i < V; i++) { // for each variable
                    for (int j = 0; j < V; j++) {
                        if (model.getValue(x[i][j]) != 0.0) {
                            colors.add(j);
                        }
                    }
                }

                resultColors = colors.stream().mapToInt(i -> i).toArray();

            } else {
                System.out.println("Model not solved :(");
            }

        } catch (Exception e) {
            e.getMessage();
        }

        printSolution();
        return new Algorithm("Linear Programming Algorithm", computeResultsColors(resultColors), usedColor(resultColors), resultColors);
    }

    @Override
    public void description() {
        System.out.println("This is the implementation of the Linear Programming Algorithm ");
    }

    @Override
    public void printSolution() {
        description();
        printTest(resultColors, graph);
    }
}
