package org.firstinspires.ftc.robotcontroller.internal.testcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by juanjose1 on 12/2/16.
 */

@Autonomous(name = "blue beacon" , group = "Autonomous")
public class autoblue3 extends LinearOpMode {

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    //Shooter
    DcMotor shooter;


    //Intake and Outtake
    DcMotor intake;
    DcMotor outtake;

    //Linear Slide
    DcMotor winch;

    //Button Pushers
    Servo leftb;
    Servo rightb;

    GyroSensor sensorgyro;
    ModernRoboticsI2cGyro mrgyro;

    ColorSensor color;

    int zAccumulated;
    int target = 0;

    // ColorSensor colorsensor;

    //Encoder Equation
    //  HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    float hsvValues[] = {0F,0F,0F};

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double COUNTS_PER_MOTOR_REV2 = 1120;
    static final double DRIVE_GEAR_REDUCTION = 4/3;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double COUNTS_PER_INCH2 = (COUNTS_PER_MOTOR_REV2 * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = -0.5;
    static final double TURN_SPEED = -0.25;

    @Override
    public void runOpMode() throws InterruptedException {

        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");

        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright");

        shooter = hardwareMap.dcMotor.get("shooter");

        intake = hardwareMap.dcMotor.get("intake");
        outtake = hardwareMap.dcMotor.get("outtake");

        winch = hardwareMap.dcMotor.get("winch");

        leftb = hardwareMap.servo.get("leftb");
        rightb = hardwareMap.servo.get("rightb");

        sensorgyro = hardwareMap.gyroSensor.get("gyro");
        mrgyro = (ModernRoboticsI2cGyro) sensorgyro;

        color = hardwareMap.colorSensor.get("colorr");

        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);

        // ods = hardwareMap.opticalDistanceSensor.get("ods");

       /* shooterleft = hardwareMap.dcMotor.get("shooterleft");
        shooterright = hardwareMap.dcMotor.get("shooterright");

        intake = hardwareMap.dcMotor.get("intake");
        outtake = hardwareMap.dcMotor.get("outtake");

        slidegear = hardwareMap.dcMotor.get("gear");
        slidesprocket = hardwareMap.dcMotor.get("sprocket");*/

        sleep(1000);
        mrgyro.calibrate();

        color.enableLed(false);

        Color.RGBToHSV(color.red() * 8, color.green() * 8, color.blue() * 8, hsvValues);

        waitForStart();
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                backleft.getCurrentPosition(),
                backright.getCurrentPosition(),
                backleft.getCurrentPosition(),
                backright.getCurrentPosition());
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


        encoderDrive(0.3, -11, -11, 5000);
        sleep(300);
        shooter.setPower(1.0);
        sleep(500);
        intake.setPower(1.0);
        sleep(500);
        intake.setPower(0.0);
        sleep(300);
        intake.setPower(1.0);
        sleep(1000);
        shooter.setPower(0.0);
        intake.setPower(0.0);
        sleep(250);
        encoderDrive(0.3, -1, -1, 5000);
        sleep(200);
        encoderDrive(TURN_SPEED, -22, 22, 5000);
        sleep(300);
        encoderDrive(0.3, 22 , 22, 5000);
        sleep(300);
        encoderDrive(0.3, 4, -3, 5000);
        sleep(300);
        encoderDrive(0.3, 4, 4, 5000);
        sleep(300);
        if (color.red() * 8 < color.blue() * 8){

            rightb.setPosition(1.0);
            leftb.setPosition(1.0);


        }
        else if(color.red() * 8 > color.blue() * 8){

            rightb.setPosition(0.0);
            leftb.setPosition(0.0);

        }
        else if(color.red() * 8 < 0.0 && color.blue() * 8 < 0.0){

            rightb.setPosition(1.0);
            leftb.setPosition(0.0);

        }
        sleep(1000);
        encoderDrive(0.3, 4, 4, 5000);
        sleep(3000);













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
            newLeftTarget = frontleft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH2);
            newRightTarget = frontright.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH2);
            frontleft.setTargetPosition(newLeftTarget);
            frontright.setTargetPosition(newRightTarget);
            newLeftTarget = backleft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = backright.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            backleft.setTargetPosition(newLeftTarget);
            backright.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontleft.setPower(Math.abs(speed));
            frontright.setPower(Math.abs(speed));

            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            backleft.setPower(Math.abs(speed));
            backright.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        frontleft.getCurrentPosition(),
                        frontright.getCurrentPosition(),
                        backleft.getCurrentPosition(),
                        backright.getCurrentPosition());

                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);

            // Turn off RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(1000);
        }
    }

}
