import java.util.Arrays;

public class Maze {
    int width;
    int height;
    int [][] array;
    MatrixGraph matrixGraphG;
    MatrixGraph spanningTreeT;

    public Maze(int width, int height){
        this.height = height;
        this.width = width;
        matrixGraphG = new MatrixGraph(this.height * this.width, false);
        for (int i = 0; i < matrixGraphG.numVertices(); i++){
            double randWeight = Math.random() * 20.0;
            double randWeight2 = Math.random() * 20.0;
            if (i+1 < 100 && i+10 < 100){
                matrixGraphG.addEdge(i,i+1, randWeight);
                matrixGraphG.addEdge(i,i+10,randWeight2);
                }
            }
        }

    public void print(){
        int start = (int) (Math.random() * 10);
        int end = (int) (Math.random() * 10);
        int countX = 0;
        int countY = 0;
        for (int i = 0; i < matrixGraphG.numVertices(); i++ ){
            if ((countX == 0 && countY == start) || (countX == 9 && countY == end)){
                System.out.print("*");
            }
            else{
                System.out.print("+");
            }
            if (countX < 9 && spanningTreeT.isEdge(i, i+1)){
                System.out.print("-");
            }
            if (countX == 0 && spanningTreeT.isEdge(i,  i+10)){
                System.out.print("|");
            }
            if (countX == 9){
                countX = 0;
                countY++;
            }
            countX++;
        }
    }



    public void initializeComponents(){
        array = new int[matrixGraphG.numVertices()][matrixGraphG.numVertices()];
    }

    public void spanningTree(){
        initializeComponents();
        spanningTreeT = new MatrixGraph(width * height, false);
        for (int component = 0; component < matrixGraphG.numVertices(); component++){
            int [] neighbours = matrixGraphG.neighbours(component);
            int smallestNeighbour = 0;
            double smallestWeight = 0;
            for (int i = 0; i < neighbours.length; i++ ){
                if (i == 0){
                    smallestNeighbour = neighbours[i];
                }
                double weight = matrixGraphG.weight(component, neighbours[i]);
                if (weight < smallestWeight){
                    smallestWeight = weight;
                    smallestNeighbour = neighbours[i];
                }
            }
            spanningTreeT.addEdge(component, smallestNeighbour, smallestWeight);
            mergeComponents(component, smallestNeighbour);
        }
    }

    public void mergeComponents(int x, int y){
        array = Arrays.copyOf(array, array[x].length + 1);
        array[x][array[x].length - 1] = y;
    }





    public static void main(String[] args){
        Maze maze = new Maze(10, 10);
        maze.spanningTree();
        maze.print();
    }
}
