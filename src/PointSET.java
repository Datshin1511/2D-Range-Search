import java.util.Iterator;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private TreeSet<Point2D> tree;

    public PointSET() {
        this.tree = new TreeSet<>();
    }   

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return tree.size();
    }

    public void insert(Point2D p) {
        if(p == null) throw new IllegalArgumentException("Point cannot be null");
        tree.add(p);
    }

    public boolean contains(Point2D p) {
        if(p == null) throw new IllegalArgumentException("Point cannot be null");
        return tree.contains(p);
    }

    public void draw() {
        // TODO: Incomplete function
    }

    public Iterable<Point2D> range(RectHV rect) {
        // TODO: Incomplete iterable/iterator
        if(rect == null) throw new IllegalArgumentException("Rectangle cannot be null");

        return new Iterable<Point2D>() {

            @Override
            public Iterator<Point2D> iterator() {
                return new Iterator<Point2D>() {
                    Point2D points[] = new Point2D[size()];
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < points.length;
                    }

                    @Override
                    public Point2D next() {
                        if(!hasNext()) throw new UnsupportedOperationException("Unimplemented method 'next'");

                        return points[i++];
                    }
                    
                };
                
            }
            
        };
    }

    public Point2D nearest(Point2D p) {
        // TODO: Incomplete function
        if(p == null) throw new IllegalArgumentException("Point cannot be null");
        if (tree.isEmpty()) return null;

        return null;
    }

    public String stringPoint(Point2D p){
        return "(" + p.x() + ", " + p.y() + ")";
    }

    public static void main(String[] args) {
        // Creates new instance of the class
        PointSET set = new PointSET();

        // Inserts 30 random points inside the unit square
        for(int i=0; i<30; i++){
            set.insert(new Point2D(Math.random(), Math.random()));
        }

        // Prints PointSET size
        StdOut.println("PointSET size: " + set.size());

        // Prints whether the PointSET is empty or not
        StdOut.println("Is the PointSET empty?: " + set.isEmpty());
        
        // Checks 10 random points inside the unit square
        for(int i=0; i<10; i++){
            Point2D point = new Point2D(Math.random(), Math.random());
            StdOut.println("Is " + set.stringPoint(point) + "present among the points?: " + set.contains(point));
        }

        // Prints all points present in the PointSET that are contained within the custom rectangle
        StdOut.println("Following points are present inside a custom rectangle of (0.3, 0.1) to (0.9, 0.8)");
        for(Point2D point: set.range(new RectHV(0.3, 0.1, 0.9, 0.8))){
            StdOut.println(set.stringPoint(point));
        }

        // Prints the nearest point in the PointSET to the argument point
        Point2D p = new Point2D(0.43, 0.45);
        StdOut.println("Point nearest to the point " + set.stringPoint(p) + ": " + set.nearest(p));

    }
}   
