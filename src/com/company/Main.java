package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int range_min = 100;
        int range_max = 850;

        ArrayList<Segment> data = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            Point p1 = new Point(rand(range_min, range_max), rand(range_min, range_max));
            Point p2 = new Point(rand(range_min, range_max), rand(range_min, range_max));
            data.add(new Segment(p1, p2));
        }

        BentleyOttmann test = new BentleyOttmann(data);

        long t1 = System.currentTimeMillis();
        test.find_intersections();
        long t2 = System.currentTimeMillis();

        //test.print_intersections();
        ArrayList<Point> intersections = test.get_intersections();

        write_to_file(intersections);
        new GUI(data, intersections);

        System.out.println("number of intersections: " + intersections.size());
        System.out.println("runtime: " + (t2 - t1) + " ms");
    }

    private static double rand(double range_min, double range_max) {
        Random r = new Random();
        return range_min + (r.nextDouble() * (range_max - range_min));
    }

    private static void write_to_file(ArrayList<Point> intersections){
        try(FileWriter writer = new FileWriter("res.txt", false))
        {
            for(Point p: intersections){
                String text = "(" + p.getX() + ", " + p.getY() + ")\n";
                writer.write(text);
            }

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}