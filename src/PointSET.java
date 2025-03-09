import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
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
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);

        Iterator<Point2D> it = tree.iterator();
        while(it.hasNext()){
            it.next().draw();
        }

        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) throw new IllegalArgumentException("Rectangle cannot be null");

        ArrayList<Point2D> points = new ArrayList<>();
        Iterator<Point2D> it = tree.iterator();

        Point2D minPoint = new Point2D(rect.xmin(), rect.ymin());
        Point2D maxPoint = new Point2D(rect.xmax(), rect.ymax());

        while(it.hasNext()){
            Point2D daPoint = it.next();
            boolean case1 = false, case2 = false;

            if(daPoint.x() >= minPoint.x() && daPoint.y() >= minPoint.y()) case1 = true;
            if(daPoint.x() <= maxPoint.x() && daPoint.y() <= maxPoint.y()) case1 = true;

            if(case1 && case2) points.add(daPoint);
        }

        return new Iterable<Point2D>() {
            
            @Override
            public Iterator<Point2D> iterator() {                
                return new Iterator<Point2D>() {                   
                    int i=0, size = points.size();

                    @Override
                    public boolean hasNext() {
                        return i < size;
                    }

                    @Override
                    public Point2D next() {
                        if(!hasNext()) throw new UnsupportedOperationException("Unimplemented method 'next'");

                        return points.get(i++);
                    }
                    
                };
                
            }
            
        };
    }

    public Point2D nearest(Point2D p) {
        if(p == null) throw new IllegalArgumentException("Point cannot be null");
        if (tree.isEmpty()) return null;

        Point2D p1 = tree.ceiling(p);
        Point2D p2 = tree.floor(p);

        return (computeLength(p1, p) < computeLength(p2, p)) ? p1 : p2;
    }

    private double computeLength(Point2D p, Point2D q) {
        if(p == null || q == null) throw new IllegalArgumentException("Points cannot be null");

        return Math.sqrt((Math.pow(p.x() - q.x(), 2) + Math.pow( p.y() - q.y(), 2)));
    }

    public String stringPoint(Point2D p){
        if (p == null) throw new IllegalArgumentException("Point cannot be a null value");

        return " (" + p.x() + ", " + p.y() + ") ";
    }

    // MAIN - UNIT TESTING
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
        StdOut.println("\nIs the PointSET empty? :" + set.isEmpty());
        
        // Checks 10 random points inside the unit square
        for(int i=0; i<10; i++){
            Point2D point = new Point2D(Math.random(), Math.random());
            StdOut.println("\nIs" + set.stringPoint(point) + "present among the points? :" + set.contains(point));
        }

        // Prints all points present in the PointSET that are contained within the custom rectangle
        StdOut.println("\nFollowing points are present inside a custom rectangle of (0.3, 0.1) to (0.9, 0.8)");
        StdOut.println("-------------------------------------------------------------------------------------");
        int count = 0;
        for(Point2D point: set.range(new RectHV(0.3, 0.1, 0.9, 0.8))){
            StdOut.println(set.stringPoint(point));
            count++;
        }
        StdOut.println("\nPoints inside the rectangle: " + count);

        // Prints the nearest point in the PointSET to the argument point
        Point2D p = new Point2D(0.43, 0.45);
        StdOut.println("\nPoint nearest to the point" + set.stringPoint(p) + ": " + set.nearest(p));

        // Pictorial representation of the PointSET
        set.draw();
    }
}   
