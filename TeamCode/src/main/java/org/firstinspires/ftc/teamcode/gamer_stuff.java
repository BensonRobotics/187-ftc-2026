package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name="gamer", group="Linear OpMode")

public class gamer_stuff extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    private float[][] Pollen = new float[4][4];
    private float[][] halfStop = new float[5][5];
    private float X;
    private float X1;
    private float X2;
    private float X3;

    private float Y;
    private float Y1;
    private float Y2;
    private float Y3;

    private float step;
    private double x;
    private double y;
    private double stickthata;
    private double stickmag;
    private double feldthata;

    GoBildaPinpointDriver Odometry;



    @Override
    public void runOpMode() {


   // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "FrontLeft");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "BackLeft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "FrontRight");
        rightBackDrive = hardwareMap.get(DcMotor.class, "BackRight");
        Odometry = hardwareMap.get(GoBildaPinpointDriver.class, "Odometry");
        Odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        Odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        // intake = hardwareMap.get(DcMotor.class,"intake_motor");
        //   turretbase = hardwareMap.get(DcMotor.class,"turret_base");
        // launcherMotor = hardwareMap.get(DcMotorEx.class,"launcher_motor");
        // launcherServo = hardwareMap.get(Servo.class,"launcher_servo");
        // hopper = hardwareMap.get(DcMotorEx.class,"Hopper_motor");

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);














            IMU imu;
            // Retrieve the IMU from the hardware map
            imu = hardwareMap.get(IMU.class, "imu");
            // Adjust the orientation parameters to match your robot
            IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
            // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
            imu.initialize(parameters);






            // Initialize the hardware variables. Note that the strings used here must correspond
            // to the names assigned during the robot configuration step on the DS or RC devices.
            leftFrontDrive  = hardwareMap.get(DcMotor.class, "FrontLeft");
            leftBackDrive  = hardwareMap.get(DcMotor.class, "BackLeft");
            rightFrontDrive = hardwareMap.get(DcMotor.class, "FrontRight");
            rightBackDrive = hardwareMap.get(DcMotor.class, "BackRight");
            // intake = hardwareMap.get(DcMotor.class,"intake_motor");
            //   turretbase = hardwareMap.get(DcMotor.class,"turret_base");
            // launcherMotor = hardwareMap.get(DcMotorEx.class,"launcher_motor");
            // launcherServo = hardwareMap.get(Servo.class,"launcher_servo");
            // hopper = hardwareMap.get(DcMotorEx.class,"Hopper_motor");

            leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ////  turretbase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        X1=0;
        Y1=0;


        X2=0;
        Y2=0.5f;


        Y3=0.25f;
        X3=0.25f;




            waitForStart();
           Odometry.resetPosAndIMU();
            while (opModeIsActive()){




                stickthata = Math.atan2(Y-Odometry.getPosY(DistanceUnit.METER),X-Odometry.getPosX(DistanceUnit.METER));
                stickmag = Math.hypot(Y-Odometry.getPosY(DistanceUnit.METER),X-Odometry.getPosX(DistanceUnit.METER));
                feldthata = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
                x = Math.sin(stickthata-feldthata)*0.5;
                y = Math.cos(stickthata-feldthata)*0.5;

                leftBackDrive.setPower(y-x);
                rightBackDrive.setPower(y+x);
                leftFrontDrive.setPower(y+x);
                rightFrontDrive.setPower(y-x);



                X = ((X1-((X3-(X1+X2)/2)+X3))*step+((X3-(X1-X2)/2)+X3)-((((X3-(X1-X2)/2)+X3)-X2)*step+X2))*step+(((X3-(X1+X2)/2)+X3)-X2)*step+X2;

                Y = ((Y1-((Y3-(Y1+Y2)/2)+Y3))*step+((Y3-(Y1-Y2)/2)+Y3)-((((Y3-(Y1-Y2)/2)+Y3)-Y2)*step+Y2))*step+(((Y3-(Y1+Y2)/2)+Y3)-Y2)*step+Y2;

                if(Math.hypot((Y-Odometry.getPosY(DistanceUnit.METER)),((X-Odometry.getPosX(DistanceUnit.METER))))<0.1f)
                {
                    step = step+1;
                }

                Odometry.update();
                telemetry.addData("X",Odometry.getPosX(DistanceUnit.METER));
                telemetry.addData("Y",Odometry.getPosY(DistanceUnit.METER));
               telemetry.update();
            }




    }}

