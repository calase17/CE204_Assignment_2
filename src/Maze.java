import com.sun.source.tree.WhileLoopTree;

import java.util.*;

public class Maze {
    int width;
    int height;
    int [] array;
    MatrixGraph matrixGraphG;
    MatrixGraph spanningTreeT;

    class Edge implements Comparable<Edge>{
        int x;
        int y;
        double weight;

        Edge(int x,int y,double weight){
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return (int) (this.weight - o.weight);
        }

        @Override
        public String toString() {
            return "x:" + x + " y:" + y + " weight:" + weight;
        }
    }

    public Maze(int width, int height){

        this.height = height;
        this.width = width;
        matrixGraphG = new MatrixGraph(this.height * this.width, false);
        for (int i = 0; i < matrixGraphG.numVertices(); i++){
            double randWeight = Math.random() * 50;
            double randWeight2 = Math.random() * 50;
            if (i+1 < 100 && i+10 < 100){
                matrixGraphG.addEdge(i,i+1, randWeight);
                matrixGraphG.addEdge(i,i+10,randWeight2);
                }
            }
        }

    public void print(){
        int start = (int) (Math.random() * 10);
        int end = (int) (Math.random() * 10);
        int vertexIndex = 0;
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if ((x == 0 && y == start) || (x == 9 && y == end)){
                    System.out.print("*");
                }
                else {
                    System.out.print("+");
                }
                if (x < width-1 && spanningTreeT.isEdge(vertexIndex, vertexIndex+1)){
                    System.out.print("-");
                }
                else{
                    System.out.print(" ");
                }
                vertexIndex++;
            }
            System.out.println();
            if (y < height-1 && spanningTreeT.isEdge(vertexIndex, vertexIndex+10)){
                System.out.println("|");
            }
            else{
                System.out.println(" ");
            }
        }
    }


    public void initializeComponents(){
        array = new int[matrixGraphG.numVertices()];
        for (int component = 0; component < matrixGraphG.numVertices(); component++){
            array[component] = component;
        }
    }




    public void spanningTree(){
        initializeComponents();
        spanningTreeT = new MatrixGraph(matrixGraphG.numVertices(), false);

            for (int component = 0; component < matrixGraphG.numVertices(); component++){
                int [] neighbours = matrixGraphG.neighbours(component);
                TreeSet<Edge> edgeSet = new TreeSet<>();
                for (int neighbour : neighbours){
                    if (array[component] != array[neighbour]){
                        double weight = matrixGraphG.weight(component, neighbour);
                        Edge edge = new Edge(component, neighbour, weight);
                        edgeSet.add(edge);
                    }
                }
                if (edgeSet.isEmpty()) {
                    break;
                }
                Edge cheapestEdge = edgeSet.first();
                if (!spanningTreeT.isEdge(cheapestEdge.x, cheapestEdge.y)){
                    spanningTreeT.addEdge(cheapestEdge.x, cheapestEdge.y, cheapestEdge.weight);
                    mergeComponents(component, cheapestEdge.y);
                }
        }
    }


    public void mergeComponents(int x, int y){
        for (int i = 0; i < array.length; i++){
            if (array[i] == array[y]){
                array[i] = array[x];
            }
        }
    }



    public static void main(String[] args){
        Maze maze = new Maze(10, 10);
        maze.spanningTree();
        maze.print();
    }
}
