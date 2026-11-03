package org.firstinspires.ftc.teamcode;


// --- FTC SDK imports you'll typically need ---
// import com.qualcomm.robotcore.eventloop.opmode.OpMode;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;   // or @Autonomous
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.hardware.Servo;
// import com.qualcomm.robotcore.hardware.IMU;
// import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * OP MODE SKELETON (event-driven, non-blocking)
 *
 * The SDK calls init()/loop()/stop() for you, repeatedly and on its own
 * schedule. loop() MUST return quickly every call — no sleep(), no long
 * waits. Anything that used to be "wait N ms, then do the next step" has
 * to become a state machine: store an ElapsedTime (or a target timestamp)
 * in a field, and on each loop() call just check "has enough time passed
 * yet / has the hardware reported busy==false yet" before advancing to
 * the next state. This is the style PedroPathing's Follower requires,
 * because follower.update() must be called every single tick to keep
 * the robot tracking its path.
 *
 * See SkeletonLinearOpMode.java for the simpler, blocking alternative.
 */

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Autonomous(name = "BioBuzz187 2026 AUTOMODE", group = "Robot")
public class BioBuzzAutoMode extends OpMode {

    // =====================================================================
    // 1. HARDWARE DEVICE DECLARATIONS
    // =====================================================================

    // -- Drivetrain (mecanum) --
    // private DcMotor leftFrontDrive;
    // private DcMotor leftBackDrive;
    // private DcMotor rightFrontDrive;
    // private DcMotor rightBackDrive;

    private MecanumDrive XYdrive;

    private HiveDetector9001 Limelight;
    GoBildaPinpointDriver Odometry;

    private boolean auto_mave;

    private BioBuzzColorDistanceSensor cdSensor;
    private class Vector2 {
        public double x;
        public double y;

        // Default constructor (0, 0)
        public Vector2() {
            this.x = 0;
            this.y = 0;
        }

        // Constructor with coordinates
        public Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }



    private Servo snowPlowServo;

    private double odom_end_pos_x;
    private double odom_end_pos_y;

    // -- Subsystems (rename/add per robot) --
    // private DcMotor intakeMotor;
    // private DcMotorEx revolverMotor;
    // private DcMotorEx launchMotor;
    // private Servo    kickerServo;

    // -- Sensors --
    // private IMU imu;
    // private NormalizedColorSensor colorSensor;

    // =====================================================================
    // 2. STATE — because loop() can't block, anything you'd normally track
    //    as a local variable inside a sequential routine has to live here
    //    as a field instead, so it survives between loop() calls.
    // =====================================================================
    // private ElapsedTime stateTimer = new ElapsedTime();
    // private int currentState = 0;
    // private boolean someStepDoneYet = false;



    // @Override
    public void init() {
        // =================================================================
        // 3. HARDWARE MAP LOOKUP + ONE-TIME CONFIGURATION
        //    Runs exactly once, right after INIT is pressed on the DS.
        //    Same content as steps 2+3 in the LinearOpMode skeleton.
        // =================================================================


//        cdSensor = hardwareMap.get(BioBuzzColorDistanceSensor.class, "colordistanceSensor");
        // You need to initialize it
        Odometry = hardwareMap.get(GoBildaPinpointDriver.class, "Odometry");
        Odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        Odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        Odometry.setPosition(new Pose2D(DistanceUnit.METER, 0, 0, AngleUnit.DEGREES, 0)); //Tare Odom position
        odom_end_pos_x = 1.0;
        odom_end_pos_y = -1.0;

        Limelight = new HiveDetector9001(hardwareMap, "LimeLight");
        XYdrive = new MecanumDrive(hardwareMap, "FrontLeft", "BackLeft", "FrontRight", "BackRight");
        // 3 -- BackRight, 2 -- BackLeft, 1 -- FrontRight, 0 -- FrontLeft //

    }

    // @Override
    public void init_loop() {
        // =================================================================
        // 4. (OPTIONAL) Runs repeatedly between INIT and PLAY.
        //    Must not block. Useful for e.g. showing live sensor/dashboard
        //    telemetry before the match starts. Leave empty if unneeded.
        // =================================================================
    }

    // @Override
    public void start() {
        // =================================================================
        // 5. Runs exactly once, right when PLAY is pressed.
        //    Reset timers/state here so they start counting from "now".
        // =================================================================
    }


    // @Override
    public void loop() {
        // =================================================================
        // 6. MAIN LOOP — called repeatedly (nominally every ~20ms) until
        //    the OpMode is stopped. MUST NOT BLOCK.
        // =================================================================
        if (Odometry.getPosition().getX(DistanceUnit.METER) != odom_end_pos_x) {
            XYdrive.driveXY(odom_end_pos_x, 0.0, 0.0);
        }else if (Odometry.getPosition().getY(DistanceUnit.METER) != odom_end_pos_y) {
            XYdrive.driveXY(0.0, odom_end_pos_y, 0.0);
        }

        Odometry.update();
    }

    // @Override
    public void stop() {
        // =================================================================
        // 7. Runs exactly once when the OpMode ends (STOP pressed, or
        //    match time expires). Put safety/cleanup here — e.g. make sure
        //    every motor is explicitly set to zero power.
        // =================================================================

//        XYdrive.stop();
//        snowPlowServo.setPosition(0.0);
    }


}
