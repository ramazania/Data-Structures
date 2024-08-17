/**
 * Name: Ali & Elise
 * Email: ramazania@carleton.edu, knutsene@carleton.edu
 * Description: Wrting a program that would schedule student exams.
 */

import java.util.ArrayList;
import java.util.Collection;
import edu.princeton.cs.algs4.In;

public class ExamScheduler {
    private UndirectedGraph graph; // graph of courses and exam conflicts
    // construct an exam scheduler for the schedule information in filename
    ExamScheduler(String filename) {
        graph = new UndirectedGraph();
    
        In file = new In(filename);
        String nextLine = file.readLine();

        while (file.hasNextLine()) { // read through file
            nextLine = file.readLine();
            String numbers = "234";
            // if the first character is a number contained in numbers,
            // then we store in num as an int
            if (numbers.contains(Character.toString(nextLine.charAt(0)))) {
                int num = Integer.parseInt(Character.toString(nextLine.charAt(0)));
                ArrayList<String> list = new ArrayList<>(); // creates arraylist
                for (int i = 0; i < num; i++) {
                    String temp = file.readLine();
                    list.add(temp);
                    if (!graph.containsVertex(temp)) {
                        graph.addVertex(temp); // adds the course as the graph vertex
                    }
                }
                // x represents the index in the list the xth course in list
                for (int x = 0; x < list.size(); x++) { 
                    // i refers to the the in list that refers to ith course
                    for (int j = 0; j < list.size(); j++) { 
                        if (list.get(x) != list.get(j)) {
                            graph.addEdge(list.get(x), list.get(j));
                        }
                    }
                }
            }
        }
    }
// Gets graph 
    public UndirectedGraph getGraph() {
        return graph;
    }


    // generate and return a valid schedule
    public Iterable<? extends Collection<String>> makeSchedule() {
        ArrayList<ArrayList<String>> array = new ArrayList<>(); // an array of arraylists
         // converts graph into array
            while (graph.numVertices() != 0) {
                // creating new arraylist for each exam slot   
                ArrayList<String> littlearray = new ArrayList<>();
                // looping over graph
                    for (String vertex2 : graph.V()) { // looping over vertex
                        boolean foundedge = false;
                        for (int i = 0; i < littlearray.size(); i++) { 
                            if (graph.containsEdge(littlearray.get(i), vertex2)) {
                                foundedge = true;
                            }
                        }
                        if (!foundedge) {
                            littlearray.add(vertex2); 
                            } 
                        } 
                    array.add(littlearray);
                    for (String vertex : littlearray) {
                        graph.removeVertex(vertex);
                    }
                          
            }    
               
    return array;
        }

    
    public static void main(String[] args) {
        
        // create a new ExamScheduler from the small.txt input file
        ExamScheduler scheduler = new ExamScheduler("medium.txt");

        // make a schedule
        System.out.println(scheduler.getGraph().toString());
        UndirectedGraph graph = scheduler.getGraph().copy();
        var s = scheduler.makeSchedule();

        // check that the schedule is valid
        // NOTE: ScheduleChecker.check expects the graph to be the original full graph
        // so use copies to preserve the original (or change what's being passed in
        // here)
        ScheduleChecker.check(graph, s);

    }
}