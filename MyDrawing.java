import edu.princeton.cs.algs4.StdDraw;

/**
 * MyDrawing
 * Ali Ramazani
 * My drawing potrays a picture of a snowman with a blue background.
 */
public class MyDrawing {

    public static void main(String[] args) {
        // Blue Background using a big blue filled circle.
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(0.5, 0.5, 0.8);

        // Body 
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledCircle(0.5, 0.2, 0.2);
        StdDraw.filledCircle(0.5, 0.45, 0.15);
        StdDraw.filledCircle(0.5, 0.68, 0.10);

        // Hands
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.line(0.35, 0.5, 0.2, 0.6);
        StdDraw.line(0.65, 0.5, 0.8, 0.6);

        // Buttons
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.03);
        StdDraw.point(0.5, 0.5);
        StdDraw.point(0.5, 0.45);

        // Hat
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(0.4, 0.75, 0.6, 0.75);
        StdDraw.filledSquare(0.5, 0.84, 0.095);

        // Mouth
        StdDraw.line(0.45, 0.65, 0.55, 0.65);

        // Eyes
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.03);
        StdDraw.point(0.45, 0.7);
        StdDraw.point(0.55, 0.7);

    }
    

    


}