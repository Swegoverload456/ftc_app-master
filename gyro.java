package org.firstinspires.ftc.robotcontroller.internal.testcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by juanjose1 on 11/15/16.
 */
public class gyro extends LinearOpMode {

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    GyroSensor sensorgyro;
    ModernRoboticsI2cGyro mrgyro;

    int zAccumulated;
    int target = 0;

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

        sleep(1000);
        mrgyro.calibrate();

        waitForStart();

        while (mrgyro.isCalibrating()) {
        }

        while (opModeIsActive()) {

            zAccumulated = mrgyro.getIntegratedZValue();

            while (Math.abs(zAccumulated - target) > 3) {


                if (zAccumulated > 0) {

                    frontleft.setPower(0.15);
                    frontright.setPower(-0.15);
                    backleft.setPower(0.15);
                    backright.setPower(-0.15);

                }
                if (zAccumulated < 0) {

                    frontleft.setPower(-0.15);
                    frontright.setPower(0.15);
                    backleft.setPower(-0.15);
                    backright.setPower(0.15);

                }
                waitOneFullHardwareCycle();

                zAccumulated = mrgyro.getIntegratedZValue();

                telemetry.addData("1 accu", String.format("03d", zAccumulated));

            }

                frontleft.setPower(0);
                frontright.setPower(0);
                backleft.setPower(0);
                backright.setPower(0);

                telemetry.addData("1 accu", String.format("03d", zAccumulated));

                waitOneFullHardwareCycle();



        }
    }
}