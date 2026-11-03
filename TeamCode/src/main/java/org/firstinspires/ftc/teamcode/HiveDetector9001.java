package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorLimelight3A;
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


    public HiveDetector9001(HardwareMap hardwareMap,
                            String LimeLightName) {
        limelight = hardwareMap.get(Limelight3A.class, LimeLightName);

        //Set poll rate (Refresh Rate)
        limelight.setPollRateHz(80);

        limelight.pipelineSwitch(0);

        limelight.start();

    }
     public Map<Integer, AprilTag187> Update(){
        LLResult latestresult = limelight.getLatestResult();


        List<LLResultTypes.FiducialResult> fiducialResults = latestresult.getFiducialResults();

        Map<Integer, AprilTag187> metadata = new HashMap<>();

         for (LLResultTypes.FiducialResult fiducial : fiducialResults) { //
             int id = fiducial.getFiducialId(); //
             double tx = fiducial.getTargetXDegrees();
             double ty = fiducial.getTargetYDegrees();
             double ta = fiducial.getTargetArea();
             metadata.put(id, new AprilTag187(tx, ty, ta));
         }
         return metadata;

     }
}

