package org.firstinspires.ftc.robotcontroller.internal.testcode;

import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/**
 * Created by juanjose1 on 9/24/16.
 */
@TeleOp(name = "Teleop" , group = "Teleop")
public class teleop9110 extends OpMode {

    //Drive Train
    DcMotor left;
    DcMotor right;

    //Shooter
    //  DcMotor shooterleft;
    //  DcMotor shooterright;

    //Intake and Outtake
    DcMotor intake;
    //  DcMotor outtake;

    //Linear Slide
    //  DcMotor slidegear;
    //  DcMotor slidesprocket;

    @Override
    public void init() {

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");

        //    shooterleft = hardwareMap.dcMotor.get("shooterleft");
        //    shooterright = hardwareMap.dcMotor.get("shooterright");

        //intake = hardwareMap.dcMotor.get("intake");
        //    outtake = hardwareMap.dcMotor.get("outtake");

        //    slidegear = hardwareMap.dcMotor.get("gear");
        //    slidesprocket = hardwareMap.dcMotor.get("sprocket");

        left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        //Drive Train
        float lefty = gamepad1.left_stick_y;
        float righty = gamepad1.right_stick_y;

        left.setPower(lefty);
        right.setPower(righty);

        //Intake
       /* if (gamepad1.a && !gamepad1.b) {

            intake.setPower(1.0);

        } else if (!gamepad1.a && gamepad1.b) {

            intake.setPower(-1.0);

        } else {

            intake.setPower(0.0);

        }*/

      /*  //Linear Slide
        float arm = gamepad2.left_stick_y;

        slidesprocket.setPower(arm);
        slidegear.setPower(arm);

        //Shooter
        if(gamepad2.a){

            shooterleft.setPower(-1.0);
            shooterright.setPower(1.0);

        }

        //Outtake
        float Outtake = gamepad2.right_stick_y;

        outtake.setPower(Outtake);


        }*/

    }
}

