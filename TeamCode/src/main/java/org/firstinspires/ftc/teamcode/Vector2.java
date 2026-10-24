package org.firstinspires.ftc.teamcode;


//Contains two doubles, x and y. Can be helpful for Velocity(X, Y) etc.
public class Vector2 {
    public double x;
    public double y;

    public Vector2() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double angle(){
       return Math.atan2(this.x, this.y);
       //The angle of the vector2, ex: (1.0, 0.0) is 90deg, (-1.0, 0.0) is -90deg, (0.0, 1.0) is 0deg, (0.0, -1.0) is 180deg, (0.5, 0.5) is 45deg
    }

    public Vector2 normalized(){
        double length = Math.sqrt((this.x * this.x) + (this.y * this.y));
        if (length == 0.0){
            return new Vector2(0.0, 0.0);
        }
        return new Vector2(this.x / length, this.y / length);
    }
}
