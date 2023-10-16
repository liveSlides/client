package com.harun.liveSlide.model;

public class MouseCoordinate {
    public double x;
    public double y;

    public MouseCoordinate(double x , double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "MouseCoordinate(x : " + x + " ," + "y : " + y +")\n";
    }
}
