package org.firstinspires.ftc.robotcontroller.internal.testcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by juanjose1 on 11/15/16.
 */
public class gyro_method extends LinearOpMode {

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    GyroSensor sensorgyro;
    ModernRoboticsI2cGyro mrgyro;


    @Override
    public void runOpMode() throws InterruptedException {

        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");

        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright");

        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);

        sensorgyro = hardwareMap.gyroSensor.get("gyro");
        mrgyro = (ModernRoboticsI2cGyro) sensorgyro;

        /*frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
*/
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();

        mrgyro.calibrate();

        while (mrgyro.isCalibrating())  {
            Thread.sleep(50);
            idle();
        }

        telemetry.addData(">", "Gyro Calibrated.  Press Start.");
        telemetry.update();


        waitForStart();


        while (opModeIsActive()) {

            turnabsolute(90);
            sleep(1000);
            turnabsolute(0);
            sleep(1000);

        }
    }




    public void turnabsolute(int target) throws InterruptedException{

        int zAccumulated = mrgyro.getHeading();

        while (Math.abs(zAccumulated - target) > 3) {


            if (zAccumulated > target) {

                frontleft.setPower(0.15);
                frontright.setPower(-0.15);
                backleft.setPower(0.15);
                backright.setPower(-0.15);

            }
            if (zAccumulated < target) {

                frontleft.setPower(-0.15);
                frontright.setPower(0.15);
                backleft.setPower(-0.15);
                backright.setPower(0.15);

            }

            zAccumulated = mrgyro.getHeading();

            telemetry.addData("1 accu", String.format("03d", zAccumulated));
        }


            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);

            telemetry.addData("1 accu", String.format("03d", zAccumulated));



        }
    }


