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

// @TeleOp(name = "Skeleton OpMode", group = "Training")
public class BioBuzzOpMode /* extends OpMode */ {

    // =====================================================================
    // 1. HARDWARE DEVICE DECLARATIONS
    // =====================================================================

    // -- Drivetrain (mecanum) --
    // private DcMotor leftFrontDrive;
    // private DcMotor leftBackDrive;
    // private DcMotor rightFrontDrive;
    // private DcMotor rightBackDrive;

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

        // --- 6a. Read driver input (gamepad1 / gamepad2), if teleop ---

        // --- 6b. Drivetrain: turn input into wheel power ---

        // --- 6c. Subsystem / state-machine step
        //          (check timers/isBusy()/flags, advance currentState) ---

        // --- 6d. Sensor reads (color sensor, vision, IMU heading) ---

        // --- 6e. Telemetry (always end the loop with this) ---
        // telemetry.update();

        MecanumDrive.drive(23.0, 2.0, 2.0);
    }

    // @Override
    public void stop() {
        // =================================================================
        // 7. Runs exactly once when the OpMode ends (STOP pressed, or
        //    match time expires). Put safety/cleanup here — e.g. make sure
        //    every motor is explicitly set to zero power.
        // =================================================================
    }

}
