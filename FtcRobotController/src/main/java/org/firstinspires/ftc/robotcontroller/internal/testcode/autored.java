package org.firstinspires.ftc.robotcontroller.internal.testcode;

import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

import java.sql.Driver;

/**
 * Created by juanjose1 on 9/25/16.
 */
@Autonomous(name = "red" , group = "Autonomous")

public class autored extends LinearOpMode {

    //Drive Train
    DcMotor left;
    DcMotor right;
  //  OpticalDistanceSensor ods;




    //Shooter
  /*  DcMotor shooterleft;
    DcMotor shooterright;

    //Intake and Outtake
    DcMotor intake;
    DcMotor outtake;

    //Linear Slide
    DcMotor slidegear;
    DcMotor slidesprocket;
*/
   // ColorSensor colorsensor;

    //Encoder Equation
  //  HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.5;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = -0.25;
    static final double TURN_SPEED = -0.3;

    @Override
    public void runOpMode() throws InterruptedException {

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        right.setDirection(DcMotorSimple.Direction.REVERSE);

       // ods = hardwareMap.opticalDistanceSensor.get("ods");

       /* shooterleft = hardwareMap.dcMotor.get("shooterleft");
        shooterright = hardwareMap.dcMotor.get("shooterright");

        intake = hardwareMap.dcMotor.get("intake");
        outtake = hardwareMap.dcMotor.get("outtake");

        slidegear = hardwareMap.dcMotor.get("gear");
        slidesprocket = hardwareMap.dcMotor.get("sprocket");*/

        waitForStart();
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                left.getCurrentPosition(),
                right.getCurrentPosition());
        telemetry.update();

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOff = false;

        // get a reference to our ColorSensor object.
        /*colorsensor = hardwareMap.colorSensor.get("color sensor");

        // Set the LED in the beginning
        colorsensor.enableLed(bLedOff);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // convert the RGB values to HSV values.
        Color.RGBToHSV(colorsensor.red() * 8, colorsensor.green() * 8, colorsensor.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        telemetry.addData("LED", bLedOff ? "On" : "Off");
        telemetry.addData("Clear", colorsensor.alpha());
        telemetry.addData("Red  ", colorsensor.red());
        telemetry.addData("Green", colorsensor.green());
        telemetry.addData("Blue ", colorsensor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        // change the background color to match the color detected by the RGB sensor.
        // pass a reference to the hue, saturation, and value array as an argument
        // to the HSVToColor method.
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }*/
        ;

        telemetry.update();


        encoderDrive(DRIVE_SPEED, 40, 40, 5.0);
        encoderDrive(DRIVE_SPEED, 20, 10, 5.0);
        encoderDrive(DRIVE_SPEED, -8, -10, 5.0);
        encoderDrive(DRIVE_SPEED, 4.5, -4.5, 5.0xq);





       /* if(colorsensor.red() * 8 > colorsensor.blue() * 8){

            encoderDrive(DRIVE_SPEED, 5, 5, 50);

        }
        else if(colorsensor.red() * 8 < colorsensor.blue() * 8){


        }*/
       /*  encoderDrive(TURN_SPEED, -9, 9, 5.0);
        encoderDrive(DRIVE_SPEED, 24, 24, 5.0);
        encoderDrive(TURN_SPEED, 9, -9, 5.0);

        /* if(colorsensor.red() * 8 > colorsensor.blue() * 8){

            encoderDrive(DRIVE_SPEED, 5, 5, 50);

        }
        else if(colorsensor.red() * 8 < colorsensor.blue() * 8){



        }*/
       /* encoderDrive(DRIVE_SPEED,-12, -12, 5.0 );
        encoderDrive(TURN_SPEED, -4.5, 4.5, 5.0);
        encoderDrive(DRIVE_SPEED, 12, 12, 5.0);
*/
        telemetry.addData("Path", "Complete");
        telemetry.update();



}


    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) throws InterruptedException {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = left.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = right.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            left.setTargetPosition(newLeftTarget);
            right.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            left.setPower(Math.abs(speed));
            right.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (left.isBusy() && right.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        left.getCurrentPosition(),
                        right.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            left.setPower(0);
            right.setPower(0);

            // Turn off RUN_TO_POSITION
            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

             sleep(1000);
        }
    }
}