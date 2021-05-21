package com.company;

public class Segment implements Comparable<Segment> {
    private Point p1;
    private Point p2;
    double value;

    Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.calculate_value(this.first().getX());
    }

    public Point first() {
        if(p1.getX() <= p2.getX()) {
            return p1;
        } else {
            return p2;
        }
    }

    public Point second() {
        if(p1.getX() <= p2.getX()) {
            return p2;
        } else {
            return p1;
        }
    }

    public void calculate_value(double value) {
        double x1 = this.first().getX();
        double x2 = this.second().getX();
        double y1 = this.first().getY();
        double y2 = this.second().getY();
        this.value = y1 + (((y2 - y1) / (x2 - x1)) * (value - x1));
    }

    public void set_value(double value) {
        this.value = value;
    }

    public double get_value() {
        return this.value;
    }

    @Override
    public int compareTo(Segment s) {
        if (this.get_value() < s.get_value()) {
            return 1;
        }
        if (this.get_value() > s.get_value()) {
            return -1;
        }
        return 0;
    }
}