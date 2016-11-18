package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.android.internal.util.Predicate;
import com.qualcomm.*;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import android.graphics.Color;
import android.view.View;

@TeleOp(name = "Teleop" , group = "TeleOp")
public class Teleop9110 extends OpMode {

    //Drive Train
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

    Servo release;
    Servo link;

    @Override
    public void init() {

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

        release = hardwareMap.servo.get("release");

        link = hardwareMap.servo.get("link");


        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        outtake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        //Drive Train
        float lefty = gamepad1.left_stick_y;
        float righty = gamepad1.right_stick_y;

        int a = 0;
        int b = 0;
        int x = 0;

        frontleft.setPower(lefty);
        frontright.setPower(righty);
        backleft.setPower(lefty);
        backright.setPower(righty);

        if (gamepad1.start == true) {

            winch.setPower(1.0);

        } else if (gamepad1.back == true) {

            winch.setPower(-1.0);

        }
        if (gamepad1.a == true && a == 0) {

            shooter.setPower(1.0);
            a=1;

        }
        else if(gamepad1.a == false && a == 1){

            a = 2;

        }
        else if(gamepad1.a && a ==2){

            shooter.setPower(0.0);
            a = 3;

        }
        else if (!gamepad1.a && a == 3){

            a = 0;

        }
        if (gamepad1.b && b == 0){

            outtake.setPower(1.0);
            b = 1;

        }
        else if(!gamepad1.b && b == 1){

            b = 2;

        }
        else if (gamepad1.b && b == 2){

            outtake.setPower(0.0);
            b = 3;

        }
        else if (!gamepad1.b && b == 3){

            b = 0;

        }
        if (gamepad1.x && x == 0){

            link.setPosition(0.8);
            x = 1;

        }
        else if (!gamepad1.x && x == 1){

            x = 2;

        }
        else if (gamepad1.x && x == 2){

            link.setPosition(0.0);
            x = 3;

        }
        else if (!gamepad1.x && b == 3){

            x = 0;

        }
        if (gamepad1.left_bumper == true && gamepad1.right_bumper == false) {

            intake.setPower(1.0);

        } else if(gamepad1.left_bumper == false && gamepad1.right_bumper == true){

            intake.setPower(-1.0);

        } else{

            intake.setPower(0.0);

        }
        if (gamepad1.dpad_up){

            release.setPosition(0.5);

        }
        else if (gamepad1.dpad_down){

            release.setPosition(0.0 );

        }
        if(gamepad1.right_trigger < 0.5){

            rightb.setPosition(0.0);

        }
        else if (gamepad1.right_trigger > 0.5) {

            rightb.setPosition(1.0);

        }
        if (gamepad1.left_trigger < 0.5){

            leftb.setPosition(1.0);

        }
        else if (gamepad1.left_trigger > 0.5){

            leftb.setPosition(0.0);

        }

    }


}


