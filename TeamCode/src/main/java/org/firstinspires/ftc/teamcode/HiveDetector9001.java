package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorLimelight3A;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiveDetector9001 {

    private final Limelight3A limelight;

    private AprilTagProcessor aprilTag;

    public HiveDetector9001(HardwareMap hardwareMap,
                            String LimeLightName) {
        limelight = hardwareMap.get(Limelight3A.class, LimeLightName);

        //Set poll rate (Refresh Rate)
        limelight.setPollRateHz(80);

        limelight.pipelineSwitch(0);

        limelight.start();

        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

    }
     public List<AprilTagDetection> GetResults(){
         // Get list of detections from AprilTag Processor
         List<AprilTagDetection> currentDetections = aprilTag.getDetections();

         return currentDetections;
     }
}

