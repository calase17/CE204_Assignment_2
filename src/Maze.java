public class Maze {
    int width;
    int height;

    public Maze(int width, int height){
        this.height = height;
        this.width = width;
        MatrixGraph matrixGraph = new MatrixGraph(this.height, false);
        print(matrixGraph);
    }

    public void print(MatrixGraph matrixGraph){
        int start =(int) (Math.random() * 10);
        int end = (int) (Math.random() * 10);

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int y1 = y + 1;
                if (y1 < width){
                    matrixGraph.addEdge(y,y1,1.0);
                }
                if ((x == 0 && start == y) || (x == 9 && y == end)){
                    System.out.print("*");
                }
                else{
                    System.out.print("+");
                }

                if (x < 9){
                    System.out.print("-");
                }
            }
            System.out.println();
            if (y < 9){
                System.out.println("| | | | | | | | | |");
            }
        }
    }




    public static void main(String[] args){
        new Maze(10, 10);
    }
}
