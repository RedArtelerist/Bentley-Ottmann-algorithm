package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Event implements Comparable<Event>{
    private Point point;
    private final ArrayList<Segment> segments;
    private double value;
    private int type;

    Event(Point p, Segment s, int type) {
        this.point = p;
        this.segments = new ArrayList<>(Arrays.asList(s));
        this.value = p.getX();
        this.type = type;
    }

    Event(Point p, ArrayList<Segment> s, int type) {
        this.point = p;
        this.segments = s;
        this.value = p.getX();
        this.type = type;
    }

    public void add_point(Point p) {
        this.point = p;
    }

    public Point get_point() {
        return this.point;
    }

    public void add_segment(Segment s) {
        this.segments.add(s);
    }

    public ArrayList<Segment> get_segments() {
        return this.segments;
    }

    public void set_type(int type) {
        this.type = type;
    }

    public int get_type() {
        return this.type;
    }

    public void set_value(double value) {
        this.value = value;
    }

    public double get_value() {
        return this.value;
    }

    @Override
    public int compareTo(Event e) {
        if (this.get_value() < e.get_value())
            return -1;
        if (this.get_value() > e.get_value())
            return 1;
        return 0;
    }
}