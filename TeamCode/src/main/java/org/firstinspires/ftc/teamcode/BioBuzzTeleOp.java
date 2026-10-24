 /* Copyright (c) 2021 FIRST. All rights reserved.
  *
  * Redistribution and use in source and binary forms, with or without modification,
  * are permitted (subject to the limitations in the disclaimer below) provided that
  * the following conditions are met:
  *
  * Redistributions of source code must retain the above copyright notice, this list
  * of conditions and the following disclaimer.
  *
  * Redistributions in binary form must reproduce the above copyright notice, this
  * list of conditions and the following disclaimer in the documentation and/or
  * other materials provided with the distribution.
  *
  * Neither the name of FIRST nor the names of its contributors may be used to endorse or
  * promote products derived from this software without specific prior written permission.
  *
  * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
  * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
  * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
  * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
  * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
  * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
  * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
  * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  */

 package org.firstinspires.ftc.teamcode;

 import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 import com.qualcomm.robotcore.hardware.DcMotor;
 import com.qualcomm.robotcore.hardware.DcMotorEx;
 import com.qualcomm.robotcore.hardware.IMU;
 import com.qualcomm.robotcore.hardware.Servo;

 import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;



 @TeleOp(name="Field Centric Mecanum Teleop", group="Linear OpMode")

 public class BioBuzzTeleOp extends LinearOpMode {

     // Declare OpMode members for each of the 4 motors.

     private DcMotor leftFrontDrive = null;
     private DcMotor leftBackDrive = null;
     private DcMotor rightFrontDrive = null;
     private DcMotor rightBackDrive = null;
     private DcMotorEx launcherMotor = null;
     private Servo launcherServo = null;
     private DcMotor intake = null;
     private DcMotor turretbase = null;
     private DcMotorEx hopper = null;

     private double x;
     private double y;
     private double stickthata;
     private double stickmag;
     private double feldthata;
     private double fx;
     private double launchAngle;
     private double launchSpeed;
     private double Vy;
     private double Vx;
     private double Distance;








     @Override
     public void runOpMode() {



         IMU imu;
         // Retrieve the IMU from the hardware map
         imu = hardwareMap.get(IMU.class, "imu");
         // Adjust the orientation parameters to match your robot
         IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                 RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                 RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
         // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
         imu.initialize(parameters);






         // Initialize the hardware variables. Note that the strings used here must correspond
         // to the names assigned during the robot configuration step on the DS or RC devices.
         leftFrontDrive  = hardwareMap.get(DcMotor.class, "FrontLeft");
         leftBackDrive  = hardwareMap.get(DcMotor.class, "BackLeft");
         rightFrontDrive = hardwareMap.get(DcMotor.class, "FrontRight");
         rightBackDrive = hardwareMap.get(DcMotor.class, "BackRight");
         intake = hardwareMap.get(DcMotor.class,"intake_motor");
         turretbase = hardwareMap.get(DcMotor.class,"turret_base");
         launcherMotor = hardwareMap.get(DcMotorEx.class,"launcher_motor");
         launcherServo = hardwareMap.get(Servo.class,"launcher_servo");
         hopper = hardwareMap.get(DcMotorEx.class,"Hopper_motor");






         waitForStart();
         while (opModeIsActive()){
             intake.setPower(1);


            if(gamepad1.x)
            {
                imu.resetYaw();
            }

             stickthata = Math.atan2( -gamepad1.left_stick_y , gamepad1.left_stick_x );
             stickmag = Math.hypot( gamepad1.left_stick_x , -gamepad1.left_stick_y);
             feldthata = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

             x = Math.sin(feldthata+stickthata)*stickmag;
             y = Math.cos(feldthata+stickthata)*stickmag;

             leftBackDrive.setPower(y -x +gamepad1.right_stick_x);
             rightBackDrive.setPower(y+ x -gamepad1.right_stick_x);
             leftFrontDrive.setPower(y+x +gamepad1.right_stick_x);
             rightFrontDrive.setPower(y -x -gamepad1.right_stick_x);

             leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
             leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
             rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
             rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
             turretbase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



             //------------------------------------------------------------------------------------




            Distance = 0.5;

             Math.atan(Vy/Vx);

             Vx = Math.pow(Distance,4)*(-0.123)+Math.pow(Distance,3)*(0.5)-Math.pow(Distance,2)*(0.978)+ Distance *(2.13)-0.004;

             Vy=(4.9*Math.pow(Distance /Vx,2)+1.1)/(Distance /Vx);

             launchAngle = Math.atan(Vy/Vx);

             launchSpeed = ((60*Math.sqrt(Math.pow(1.1,2)+Math.pow(Distance,2))/Math.PI*0.048)/6000)+0.53;


             launcherServo.setPosition(launchAngle);
             launcherMotor.setVelocity(2800*launchSpeed);
             if(gamepad2.left_bumper)
             {
                 hopper.setPower(1);
             }
             turretbase.setPower(gamepad2.left_stick_x);


















         }




     }}

