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
        StdDraw.setPenColor(StdDraw.BLACK);
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
        for (Point2D p : tree) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;

        // ArrayList<Point2D> points = new ArrayList<>();
        // Iterator<Point2D> it = tree.iterator();

        // Point2D minPoint = new Point2D(rect.xmin(), rect.ymin());
        // Point2D maxPoint = new Point2D(rect.xmax(), rect.ymax());

        // while(it.hasNext()){
        //     Point2D daPoint = it.next();
        //     boolean case1 = false, case2 = false;

        //     if(daPoint.x() >= minPoint.x() && daPoint.y() >= minPoint.y()) 
        //         case1 = true;
        //     if(daPoint.x() <= maxPoint.x() && daPoint.y() <= maxPoint.y()) 
        //         case2 = true;

        //     if(case1 && case2) points.add(daPoint);
        // }

        // return new Iterable<Point2D>() {
            
        //     @Override
        //     public Iterator<Point2D> iterator() {                
        //         return new Iterator<Point2D>() {                   
        //             int i=0, size = points.size();

        //             @Override
        //             public boolean hasNext() {
        //                 return i < size;
        //             }

        //             @Override
        //             public Point2D next() {
        //                 if(!hasNext()) throw new UnsupportedOperationException("Unimplemented method 'next'");

        //                 return points.get(i++);
        //             }
                    
        //         };
                
        //     }
            
        // };
    }

    public Point2D nearest(Point2D p) {
        if(p == null) throw new IllegalArgumentException("Point cannot be null");
        if (tree.isEmpty()) return null;

        Point2D nearest = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Point2D point : tree) {
            double distance = p.distanceSquaredTo(point);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = point;
            }
        }
        return nearest;
    }

    private String stringPoint(Point2D p){
        if (p == null) throw new IllegalArgumentException("Point cannot be a null value");

        return " (" + p.x() + ", " + p.y() + ") ";
    }

    // MAIN - UNIT TESTING
    public static void main(String[] args) {

        // New instance of the class
        PointSET set = new PointSET();

        int entryPoints = 100;
        int checkPoints = (3*entryPoints) / 100;

        // 50 random points inside the unit square
        for(int i=0; i<entryPoints; i++){
            set.insert(new Point2D(Math.random(), Math.random()));
        }

        // PointSET size
        StdOut.println("PointSET size: " + set.size());

        // Whether the PointSET is empty or not
        StdOut.println("Is the PointSET empty? :" + set.isEmpty() + "\n");
        
        // Checking 20 random points inside the unit square
        for(int i=0; i<checkPoints; i++){
            Point2D point = new Point2D(Math.random(), Math.random());
            StdOut.println("Is" + set.stringPoint(point) + "present among the points? :" + set.contains(point));
        }

        // All points present in the PointSET that are contained within the custom rectangle
        StdOut.println("\nFollowing points are present inside a custom rectangle of (0.2, 0.3) to (0.5, 0.6)");
        StdOut.println("-------------------------------------------------------------------------------------");
        int count = 0;
        for(Point2D point: set.range(new RectHV(0.3, 0.25, 0.5, 0.7))){
            StdOut.println(set.stringPoint(point));
            count++;
        }
        StdOut.println("\nPoints inside the rectangle: " + count);

        // Nearest point in the PointSET to the argument point
        Point2D p = new Point2D(0.805, 0.827);
        Point2D nearestPoint = set.nearest(p);
        StdOut.println("\nPoint nearest to the point" + set.stringPoint(p) + ": " + set.stringPoint(nearestPoint));

        // Pictorial representation of the PointSET and nearest point
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.01);
        p.draw();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        nearestPoint.draw();

        set.draw();
    }
}   
