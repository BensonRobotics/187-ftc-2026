package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrive {

    private final DcMotor leftFront;
    private final DcMotor leftBack;
    private final DcMotor rightFront;
    private final DcMotor rightBack;

    public MecanumDrive(HardwareMap hardwareMap,
                        String leftFrontName, String leftBackName,
                        String rightFrontName, String rightBackName) {
        leftFront = hardwareMap.get(DcMotor.class, leftFrontName);
        leftBack = hardwareMap.get(DcMotor.class, leftBackName);
        rightFront = hardwareMap.get(DcMotor.class, rightFrontName);
        rightBack = hardwareMap.get(DcMotor.class, rightBackName);

        // Defaults for a direct-drive GoBilda-style frame. Existing OpModes
        // in this repo disagree with each other on these directions (compare
        // mecanumFieldCentricTeleop.java vs. BasicAutoBlue.java's GOBILDA
        // case) because it depends on how your particular robot is built —
        // always confirm by pushing the stick forward and watching the
        // wheels, then flip whichever ones run backward.
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Drive the four wheels as a single unit.
     *
     * @param angleRadians direction of travel, measured counter-clockwise
     *                      from straight ahead (0 = forward, +PI/2 = strafe
     *                      left) — robot-centric, not field-centric.
     * @param speed         magnitude of translation, 0.0 to 1.0.
     * @param turn          rotation rate, -1.0 (clockwise) to 1.0
     *                      (counter-clockwise), applied independently of
     *                      translation.
     */
    public void drive(double angleRadians, double speed, double turn) {
        double x = Math.cos(angleRadians) * speed; // forward component
        double y = Math.sin(angleRadians) * speed; // strafe-left component

        double leftFrontPower = x - y - turn;
        double rightFrontPower = x + y + turn;
        double leftBackPower = x + y - turn;
        double rightBackPower = x - y + turn;

        double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower)),
                Math.max(Math.abs(leftBackPower), Math.abs(rightBackPower))));

        leftFront.setPower(leftFrontPower / max);
        leftBack.setPower(leftBackPower / max);
        rightFront.setPower(rightFrontPower / max);
        rightBack.setPower(rightBackPower / max);
    }

    /**
     * Field-centric convenience overload: rotates the requested travel
     * angle by the robot's current heading before handing off to drive().
     *
     * @param robotHeadingRadians current heading from the IMU
     *                            (e.g. imu.getRobotYawPitchRollAngles().getYaw(RADIANS)),
     *                            re-zeroed at the start of the match.
     */
    public void driveFieldCentric(double angleRadians, double speed, double turn,
                                  double robotHeadingRadians) {
        drive(angleRadians - robotHeadingRadians, speed, turn);
    }

    /** Convenience for the common teleop case: x/y joystick + rotation stick. */
    public void driveXY(double x, double y, double turn) {
        drive(Math.atan2(y, x), Math.hypot(x, y), turn);
    }

    public void stop() {
        drive(0, 0, 0);
    }
}
