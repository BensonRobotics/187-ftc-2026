package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class AprilTag187 {
    public int id;
    public double tx;
    public double ty;
    public double ta;
    public Pose3D pose;

    //Has
    public AprilTag187(int id, double tx, double ty, double ta, Pose3D pose) {
        this.id = id;
        this.tx = tx;
        this.ty = ty;
        this.ta = ta;
        this.pose = pose;
    }

    @Override
    public String toString(){
        return String.format("%.2f, %.2f, %.0f%%, %s\n", tx, ty, ta * 100, pose, getQuadrant());
    }

    public String getQuadrant(){
        if (id == 34 || id == 35 || id == 36 || id == 37){
            return "Red Audience";
        } else if (id == 30 || id == 31 || id == 32 || id == 33){
            return "Red Scoring";
        } else if (id == 42 || id == 43 || id == 44 || id == 45){
            return "Blue scoring";
        } else if (id == 38 || id == 39 || id == 40 || id == 41){
            return "Blue Audience";
        } else {
            return "None";
        }
    }
}
