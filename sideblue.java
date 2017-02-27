package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import android.app.Activity;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by juanjose1 on 2/23/17.
 */
@Autonomous(name = "Red Side", group = "Auto")
public class sideblue extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();


    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    DcMotor shooter;

    //Intake
    DcMotor intake;

    OpticalDistanceSensor odsfront;
    OpticalDistanceSensor odsback;

    ColorSensor colorl;

    Servo sideleft;

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double COUNTS_PER_MOTOR_REV2 = 1120;
    static final double DRIVE_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double COUNTS_PER_INCH2 = (COUNTS_PER_MOTOR_REV2 * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    float hsvValues[] = {0F, 0F, 0F};

    int zAccumulated;  //Total rotation left/right
    int target = 0;  //Desired angle to turn to

    GyroSensor sensorGyro;  //General Gyro Sensor allows us to point to the sensor in the configuration file.
    ModernRoboticsI2cGyro mrGyro;  //ModernRoboticsI2cGyro allows us to .getIntegratedZValue()

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");

        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");

        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright");//This robot has two gears between motors and wheels. If your robot does not, you will need to reverse only the opposite motor

        shooter = hardwareMap.dcMotor.get("shooter");

        intake = hardwareMap.dcMotor.get("intake");

        sideleft = hardwareMap.servo.get("sl");

        colorl = hardwareMap.colorSensor.get("colorl");

        odsfront = hardwareMap.opticalDistanceSensor.get("ods_front");
        odsback = hardwareMap.opticalDistanceSensor.get("ods_back");

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);

        Color.RGBToHSV(colorl.red() * 8, colorl.green() * 8, colorl.blue() * 8, hsvValues);

        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sensorGyro = hardwareMap.gyroSensor.get("gyro");  //Point to the gyro in the configuration file
        mrGyro = (ModernRoboticsI2cGyro) sensorGyro;//ModernRoboticsI2cGyro allows us to .getIntegratedZValue()
        mrGyro.calibrate();  //Calibrate the sensor so it knows where 0 is and what still is. DO NOT MOVE SENSOR WHILE BLUE LIGHT IS SOLID
        while (mrGyro.isCalibrating()) { //Ensure calibration is complete (usually 2 seconds)
        }
        waitForStart();
        runtime.reset();
        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.addData("Front", odsfront.getRawLightDetected());
        telemetry.addData("Back", odsback.getRawLightDetected());


        telemetry.update();

        driveStraight(4500, 0.7);
        sideleft.setPosition(1.0);
        sleep(10);
        driveToLine(0.5);
        sideleft.setPosition(1.0);
        sleep(10);
        turn(-43);
        sideleft.setPosition(1.0);
        sleep(10);
        if (colorl.red() >= 4 || colorl.blue() < 4){

            encoderDrive(0.3, -3, -3, 5000);
            sleep(10);
            sideleft.setPosition(0.0);
            sleep(5000);
            sideleft.setPosition(1.0);
            sleep(3000);

        }
        else if (colorl.red() < 4 || colorl.blue() > 4) {

            driveToRed(-0.3);
            sleep(10);
            sideleft.setPosition(1.0);
            sleep(10);
            sideleft.setPosition(0.0);
            sleep(5000);
            sideleft.setPosition(1.0);
            sleep(3000);
        }
        driveStraight(3000, 0.7);
        sideleft.setPosition(1.0);
        sleep(10);
        driveToLine(0.5);
        sideleft.setPosition(1.0);
        sleep(10);
        encoderDrive(0.3, -2, -2, 5000);
        sleep(10);
        if (colorl.red() >= 4){

            sideleft.setPosition(0.0);
            sleep(5000);
            sideleft.setPosition(1.0);
            sleep(3000);

        }
        else if (colorl.red() < 4) {

            driveToRed(-0.3);
            sleep(10);
            sideleft.setPosition(1.0);
            sleep(10);
            sideleft.setPosition(0.0);
            sleep(5000);
            sideleft.setPosition(1.0);
            sleep(3000);
        }
        /*driveStraight(3000, 0.7);
        sideleft.setPosition(1.0);
        sleep(10);
        driveToLine(0.5);
        sideleft.setPosition(1.0);
        sleep(10);


        /*turnByLine(0.4);
        sleep(10);
        turnToBoth(0.2);
        sleep(5000);*/


    }

    public void driveToLine(double power) {

        while (odsfront.getRawLightDetected() < 0.08) {

            frontleft.setPower(power);
            frontright.setPower(power);
            backleft.setPower(power);
            backright.setPower(power);

        }
        idle();

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
        sleep(1000);

    }

    public void turnByLine(double power) {

        while (odsback.getRawLightDetected() < 0.90) {

            frontleft.setPower(power);
            frontright.setPower(-power);
            backleft.setPower(power);
            backright.setPower(-power);
        }
        idle();

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
        sleep(1000);

    }
    public void driveToRed(double speed){

        while(colorl.red() < 4){

            frontleft.setPower(speed);
            backleft.setPower(speed);
            frontright.setPower(speed);
            backright.setPower(speed);
            telemetry.addData("Red  ", colorl.red()*8);
            telemetry.addData("Blue ", colorl.blue()*8);
            telemetry.update();

        }
        idle();

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
        sleep(500);

    }
    public void driveStraight(double duration, double power) {
        double leftSpeed; //Power to feed the motors
        double rightSpeed;

        double target = mrGyro.getIntegratedZValue();  //Starting direction
        double startPosition = frontleft.getCurrentPosition();  //Starting position

        while (frontleft.getCurrentPosition() < (duration + startPosition) && opModeIsActive()) {  //While we have not passed out intended distance
            zAccumulated = mrGyro.getIntegratedZValue();  //Current direction

            leftSpeed = power + (zAccumulated - target) / 100;  //Calculate speed for each side
            rightSpeed = power - (zAccumulated - target) / 100;  //See Gyro Straight video for detailed explanation

            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);

            frontleft.setPower(leftSpeed);
            backleft.setPower(leftSpeed);
            frontright.setPower(rightSpeed);
            backright.setPower(rightSpeed);

            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            idle();

            telemetry.addData("1. Left", frontleft.getPower());
            telemetry.addData("2. Right", frontright.getPower());
            telemetry.addData("3. Distance to go", duration + startPosition - frontleft.getCurrentPosition());
            telemetry.update();
        }

        frontleft.setPower(0);
        backleft.setPower(0);
        frontright.setPower(0);
        backright.setPower(0);
        idle();
    }
    public void turnToBoth(double power) {

        while (odsback.getRawLightDetected() >= 0.90 && odsfront.getRawLightDetected() < 0.08) {

            frontleft.setPower(-power);
            frontright.setPower(power);
            backleft.setPower(-power);
            backright.setPower(power);
        }
        idle();

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
        sleep(1000);


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
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontleft.setPower(speed);
            frontright.setPower(speed);
            backleft.setPower(speed);
            backright.setPower(speed);

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

    public void turn(int target) throws InterruptedException {
        turnAbsolute(target + mrGyro.getIntegratedZValue());
    }

    //This function turns a number of degrees compared to where the robot was when the program started. Positive numbers trn left.
    public void turnAbsolute(int target) {
        zAccumulated = mrGyro.getHeading();  //Set variables to gyro readings
        double turnSpeed = 0.15;

        while (Math.abs(zAccumulated - target) > 3) {  //Continue while the robot direction is further than three degrees from the target
            if (zAccumulated > target) {  //if gyro is positive, we will turn right
                frontleft.setPower(turnSpeed);
                backleft.setPower(turnSpeed);
                frontright.setPower(-turnSpeed);
                backright.setPower(-turnSpeed);
            }

            if (zAccumulated < target) {  //if gyro is positive, we will turn left
                frontleft.setPower(-turnSpeed);
                backleft.setPower(-turnSpeed);
                frontright.setPower(turnSpeed);
                backright.setPower(turnSpeed);
            }

            zAccumulated = mrGyro.getIntegratedZValue();  //Set variables to gyro readings
            telemetry.addData("accu", String.format("%03d", zAccumulated));
            telemetry.update();
        }

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);

    }
}
