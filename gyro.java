package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "gyro" , group = "TeleOp")
/**
 * Created by juanjose1 on 2/19/17.
 */

public class gyro extends LinearOpMode{

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

    ModernRoboticsI2cGyro gyro;

    private ElapsedTime runtime = new ElapsedTime();

    private static final int CLOSE_ENOUGH_TO_ZERO = 3;

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double COUNTS_PER_MOTOR_REV2 = 1120;
    static final double DRIVE_GEAR_REDUCTION = 4 / 3;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double COUNTS_PER_INCH2 = (COUNTS_PER_MOTOR_REV2 * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException{

        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");

        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright");

        shooter = hardwareMap.dcMotor.get("shooter");

        intake = hardwareMap.dcMotor.get("intake");

        leftb = hardwareMap.servo.get("leftb");
        rightb = hardwareMap.servo.get("rightb");

        gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");

        int zAccumulated;

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gyro.calibrate();

        waitForStart();

        /*turnByGyro(-0.3, 90);
        idle();*/
        //1000 = 30 inches
        driveStraight(1, -0.25);
        sleep(10);
        shooter.setPower(0.85);
        intake.setPower(0.0);
        sleep(450);
        shooter.setPower(0.90);
        intake.setPower(1.0);
        sleep(1000);
        shooter.setPower(0.0);
        intake.setPower(0.0);
        sleep(10);
    }
    public void driveStraight(int inches, double power) throws InterruptedException{

        double leftSpeed;
        double rightSpeed;

        int newLeftTarget;
        int newRightTarget;


        double target = gyro.getIntegratedZValue();
        double startPosition = frontleft.getCurrentPosition();

        while(frontleft.getCurrentPosition() < (inches * COUNTS_PER_INCH) + startPosition){

            int zAccumulated = gyro.getIntegratedZValue();

            /*newLeftTarget = frontleft.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH2) * 2000;
            newRightTarget = frontright.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH2) * 2000;
            frontleft.setTargetPosition(newLeftTarget);
            frontright.setTargetPosition(newRightTarget);
            newLeftTarget = backleft.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH) * 2000;
            newRightTarget = backright.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH) * 2000;
            backleft.setTargetPosition(newLeftTarget);
            backright.setTargetPosition(newRightTarget);
*/

            leftSpeed = power + (zAccumulated - target)/100;
            rightSpeed = power - (zAccumulated - target)/100;

            leftSpeed = Range.clip(leftSpeed, -1.0, 1.0);
            rightSpeed = Range.clip(rightSpeed , -1.0, 1.0);

            frontleft.setPower(leftSpeed);
            backleft.setPower(leftSpeed);
            frontright.setPower(rightSpeed);
            backright.setPower(rightSpeed);
            idle();

        }
            frontleft.setPower(0);
            backleft.setPower(0);
            frontright.setPower(0);
            backright.setPower(0);

            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            idle();

    }
    public void turnByGyro(double power, int degrees) throws InterruptedException {

        double constantOfDegrees = (2 / 3);

        //int s = -1;
        boolean turnComplete = false;
        double initialPosition = gyro.getHeading();
        gyro.resetZAxisIntegrator();

        while (!turnComplete) {


            double currentPosition = gyro.getHeading();
            double target = initialPosition + (degrees - 35);

            if ((Math.abs(target)) > currentPosition) {
                frontleft.setPower(-power);
                backleft.setPower(-power);
                frontright.setPower(power);
                backright.setPower(power);

            } else
                turnComplete = true;
            telemetry.addData("Degrees: " + currentPosition, null);
            telemetry.update();
        }
        frontleft.setPower(0);
        backleft.setPower(0);
        frontright.setPower(0);
        backright.setPower(0);
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

}
