package com.company;

import java.util.*;

public class BentleyOttmann {
    private final Queue<Event> Q;
    private final NavigableSet<Segment> T;
    private final ArrayList<Point> X;

    BentleyOttmann(ArrayList<Segment> input_data) {
        this.Q = new PriorityQueue<>();
        this.T = new TreeSet<>();
        this.X = new ArrayList<>();
        for(Segment s : input_data) {
            this.Q.add(new Event(s.first(), s, 0));
            this.Q.add(new Event(s.second(), s, 1));
        }
    }

    public void find_intersections() {
        while(!this.Q.isEmpty()) {
            Event e = this.Q.poll();
            double L = e.get_value();
            switch(e.get_type()) {
                case 0:
                    for(Segment s : e.get_segments()) {
                        this.recalculate(L);
                        this.T.add(s);
                        if(this.T.lower(s) != null) {
                            Segment r = this.T.lower(s);
                            this.report_intersection(r, s, L);
                        }
                        if(this.T.higher(s) != null) {
                            Segment t = this.T.higher(s);
                            this.report_intersection(t, s, L);
                        }
                        if(this.T.lower(s) != null && this.T.higher(s) != null) {
                            Segment r = this.T.lower(s);
                            Segment t = this.T.higher(s);
                            this.remove_future(r, t);
                        }
                    }
                    break;
                case 1:
                    for(Segment s : e.get_segments()) {
                        if(this.T.lower(s) != null && this.T.higher(s) != null) {
                            Segment r = this.T.lower(s);
                            Segment t = this.T.higher(s);
                            this.report_intersection(r, t, L);
                        }
                        this.T.remove(s);
                    }
                    break;
                case 2:
                    Segment s1 = e.get_segments().get(0);
                    Segment s2 = e.get_segments().get(1);
                    this.swap(s1, s2);
                    if(s1.get_value() < s2.get_value()) {
                        if(this.T.higher(s1) != null) {
                            Segment t = this.T.higher(s1);
                            this.report_intersection(t, s1, L);
                            this.remove_future(t, s2);
                        }
                        if(this.T.lower(s2) != null) {
                            Segment r = this.T.lower(s2);
                            this.report_intersection(r, s2, L);
                            this.remove_future(r, s1);
                        }
                    } else {
                        if(this.T.higher(s2) != null) {
                            Segment t = this.T.higher(s2);
                            this.report_intersection(t, s2, L);
                            this.remove_future(t, s1);
                        }
                        if(this.T.lower(s1) != null) {
                            Segment r = this.T.lower(s1);
                            this.report_intersection(r, s1, L);
                            this.remove_future(r, s2);
                        }
                    }
                    this.X.add(e.get_point());
                    break;
            }
        }
    }

    private void report_intersection(Segment s1, Segment s2, double L) {
        double x1 = s1.first().getX();
        double y1 = s1.first().getY();
        double x2 = s1.second().getX();
        double y2 = s1.second().getY();
        double x3 = s2.first().getX();
        double y3 = s2.first().getY();
        double x4 = s2.second().getX();
        double y4 = s2.second().getY();
        double r = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3);
        if(r != 0) {
            double t = ((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / r;
            double u = ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / r;
            if(t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                double x_c = x1 + t * (x2 - x1);
                double y_c = y1 + t * (y2 - y1);
                if(x_c > L) {
                    this.Q.add(new Event(new Point(x_c, y_c), new ArrayList<>(Arrays.asList(s1, s2)), 2));
                }
            }
        }
    }

    private void remove_future(Segment s1, Segment s2) {
        for(Event e : this.Q) {
            if(e.get_type() == 2) {
                if((e.get_segments().get(0) == s1 && e.get_segments().get(1) == s2) ||
                   (e.get_segments().get(0) == s2 && e.get_segments().get(1) == s1)) {
                    this.Q.remove(e);
                    return;
                }
            }
        }
    }

    private void swap(Segment s1, Segment s2) {
        this.T.remove(s1);
        this.T.remove(s2);
        double value = s1.get_value();
        s1.set_value(s2.get_value());
        s2.set_value(value);
        this.T.add(s1);
        this.T.add(s2);
    }

    private void recalculate(double L) {
        for (Segment segment : this.T) {
            segment.calculate_value(L);
        }
    }

    public void print_intersections() {
        for(Point p : this.X) {
            System.out.println("(" + p.getX() + ", " + p.getY() + ")");
        }
    }

    public ArrayList<Point> get_intersections() {
        return this.X;
    }
}