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
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "BioBuzz187 2026", group = "Robot")
public class BioBuzzOpMode extends OpMode {

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

    private Vector2 end_pos;

    private double end_pos_x;
    private double end_pos_y;

    private Servo snowPlowServo;


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



        cdSensor = hardwareMap.get(BioBuzzColorDistanceSensor.class, "colordistanceSensor");
        // You need to initialize it

        Odometry = hardwareMap.get(GoBildaPinpointDriver.class, "Odometry");
        Odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        Odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);




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
    public void RunOpmode() {
        if (gamepad1.left_bumper) {
            if (snowPlowServo.getPosition()>0.1){
                snowPlowServo.setPosition(0.0);
            }
            else {
                snowPlowServo.setPosition(0.3694);
            }
        }
        // Move servo to midway position (approx 90/180 degrees)


        // Send telemetry data to the Driver Station
        telemetry.addData("Servo Position", snowPlowServo.getPosition());
        telemetry.update();
    }

    private void wait(boolean b) {
    }

    // @Override
    public void loop() {
        // =================================================================
        // 6. MAIN LOOP — called repeatedly (nominally every ~20ms) until
        //    the OpMode is stopped. MUST NOT BLOCK.
        // =================================================================

        // --- 6a. Read driver input (gamepad1 / gamepad2), if teleop ---
        double distancemm = cdSensor.GetDistance();
        BioBuzzColorDistanceSensor.color color = cdSensor.GetColor();
        telemetry.addData("color", color);
        telemetry.addData("distance (mm)", "%.3f", distancemm);
        telemetry.update();

        if (gamepad1.xWasPressed()) {
            auto_mave = !auto_mave;
            Odometry.setPosX(0.0, DistanceUnit.METER);
            Odometry.setPosY(0.0, DistanceUnit.METER);
            end_pos_x = 1.0;
            end_pos_y = 0.5;
        }

        if (auto_mave) {
            if (!(Odometry.getPosition().getX(DistanceUnit.METER) >= end_pos_x)) {
                XYdrive.driveXY(0.2, 0.0, 0.0);
            }
            else if (!(Odometry.getPosition().getY(DistanceUnit.METER) >= end_pos_y)) {
                XYdrive.driveXY(0.0, 0.2, 0.0);
            }
            else {
                auto_mave = false;
                XYdrive.stop();
            }
        } else {
            XYdrive.driveXY(gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);
        }

        // --- 6b. Drivetrain: turn input into wheel power ---

        // --- 6c. Subsystem / state-machine step
        //          (check timers/isBusy()/flags, advance currentState) ---

        // --- 6d. Sensor reads (color sensor, vision, IMU heading) ---

        Odometry.update();

        // --- 6e. Telemetry (always end the loop with this) ---

        // telemetry.update();

        telemetry.addData("OdomX", Odometry.getPosX(DistanceUnit.METER));
        telemetry.addData("OdomY", Odometry.getPosY(DistanceUnit.METER));
        telemetry.addData("OdomX", end_pos_x);
        telemetry.addData("OdomY", end_pos_y);



    }

    // @Override
    public void stop() {
        // =================================================================
        // 7. Runs exactly once when the OpMode ends (STOP pressed, or
        //    match time expires). Put safety/cleanup here — e.g. make sure
        //    every motor is explicitly set to zero power.
        // =================================================================

        XYdrive.stop();
        snowPlowServo.setPosition(0.0);
    }


}
