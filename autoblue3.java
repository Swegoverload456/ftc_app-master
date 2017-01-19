package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by juanjose1 on 12/2/16.
 */
//@Disabled
@Autonomous(name = "blue beacon" , group = "Autonomous")
public class autoblue3 extends LinearOpMode {

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    //Shooter
    DcMotor shooter;


    //Intake
    DcMotor intake;

    //Button Pushers
    Servo leftb;
    Servo rightb;

    ColorSensor color;

    int target = 0;

    private ElapsedTime runtime = new ElapsedTime();

    float hsvValues[] = {0F,0F,0F};

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double COUNTS_PER_MOTOR_REV2 = 1120;
    static final double DRIVE_GEAR_REDUCTION = 4/3;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double COUNTS_PER_INCH2 = (COUNTS_PER_MOTOR_REV2 * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException {

        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");

        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright");

        shooter = hardwareMap.dcMotor.get("shooter");

        intake = hardwareMap.dcMotor.get("intake");

        leftb = hardwareMap.servo.get("leftb");
        rightb = hardwareMap.servo.get("rightb");

        color = hardwareMap.colorSensor.get("colorr");

        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);

        color.enableLed(false);

        Color.RGBToHSV(color.red() * 8, color.green() * 8, color.blue() * 8, hsvValues);

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


        waitForStart();
        telemetry.update();

        encoderDrive(0.25, -9, -9, 5000);
        sleep(50);
        shooter.setPower(0.85);
        intake.setPower(0.0);
        sleep(500);
        shooter.setPower(0.85);
        intake.setPower(1.0);
        sleep(1000);
        shooter.setPower(0.0);
        intake.setPower(0.0);
        sleep(50);
        encoderDrive(0.25, -14, 14, 5000);
        sleep(50);
        encoderDrive(0.25, 19, 19, 5000);
        sleep(50);
        encoderDrive(0.25, 6, -6, 5000);
        sleep(50);
        encoderDrive(0.25, 8, 8, 5000);
        sleep(50);
        if (color.red() * 8 > color.blue() * 8){

            rightb.setPosition(1.0);
            leftb.setPosition(1.0);


        }
        else if(color.red() * 8 < color.blue() * 8){

            rightb.setPosition(0.0);
            leftb.setPosition(0.0);

        }
        else if(color.red() * 8 < 0.0 && color.blue() * 8 < 0.0){

            rightb.setPosition(1.0);
            leftb.setPosition(0.0);

        }
        sleep(1000);
        encoderDrive(0.25, 4, 4, 5000);
        sleep(50);
        encoderDrive(0.25, -4, -4, 5000);
        sleep(50);
        encoderDrive(0.25, -9, 9, 5000);
        sleep(50);
        encoderDrive(0.25, 20.5, 20.5, 5000);
        sleep(50);
        encoderDrive(0.25, 10.5, -10.5, 5000);
        sleep(50);
        if (color.red() * 8 > color.blue() * 8){

            rightb.setPosition(1.0);
            leftb.setPosition(1.0);


        }
        else if(color.red() * 8 < color.blue() * 8){

            rightb.setPosition(0.0);
            leftb.setPosition(0.0);

        }
        else if(color.red() * 8 < 0.0 && color.blue() * 8 < 0.0){

            rightb.setPosition(1.0);
            leftb.setPosition(0.0);

        }
        sleep(1000);
        encoderDrive(0.25, 4, 4, 5000);
        sleep(50);
        encoderDrive(0.25, -4, -4, 5000);
        sleep(50);

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