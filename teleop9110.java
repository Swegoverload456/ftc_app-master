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
public class teleop9110 extends OpMode {

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
    Servo sideleft;

    Servo release;
    Servo link;

    int c1 = 0;//shooter
    int c2 = 0;//intake
    int c3 = 0;//winch up
    int c4 = 0;//winch down
    int c5 = 0;//release
    boolean mult = false;

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
        sideleft = hardwareMap.servo.get("sl");

        release = hardwareMap.servo.get("release");

        link = hardwareMap.servo.get("link");

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        outtake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        //Drive Train
        float lefty = -gamepad1.left_stick_y;
        float righty = -gamepad1.right_stick_y;
        float winchy = gamepad2.left_stick_y;

            frontleft.setPower(lefty);
            frontright.setPower(righty);
            backleft.setPower(lefty);
            backright.setPower(righty);

            winch.setPower(winchy);

        if (gamepad2.a == true && c1 == 0) {

            c1 = 1;

        }
        else if(gamepad2.a == false && c1 == 1){

            shooter.setPower(0.85);
            c1 = 2;

        }
        else if (gamepad2.a == true && c1 == 2){

            c1 = 3;

        }
        else if (gamepad2.a == false && c1 == 3){

            shooter.setPower(0.0);
            c1 = 0;

        }
        if (gamepad1.a == true && c2 == 0){

            c2 = 1;


        }
        else if (gamepad1.a == false && c2 == 1){

            outtake.setPower(1.0);
            c2 = 2;

        }
        else if (gamepad1.a == true && c2 == 2){

            c2 = 3;

        }
        else if (gamepad1.a == false && c2 == 3){

            outtake.setPower(0.0);
            c2 = 0;

        }
        if (gamepad1.y && c3 == 0){

            c3 = 1;

        }
        else if (!gamepad1.y && c3 == 1){

            link.setPosition(0.35);
            c3 = 2;

        }
        else if (gamepad1.y && c3 == 2){

            c3 = 3;

        }
        else if (!gamepad1.y && c3 == 3){

            link.setPosition(0);
            c3 = 0;  

        }
        if (gamepad2.left_bumper == true && gamepad2.right_bumper == false) {

            intake.setPower(1.0);

        } else if(gamepad2.left_bumper == false && gamepad2.right_bumper == true){

            intake.setPower(-1.0);

        } else{

            intake.setPower(0.0);

        }
        if (gamepad2.b && c5 == 0){

           c5 = 1;

        }
        else if (!gamepad2.b && c5 == 1){

            release.setPosition(0.0);
            c5 = 2;

        }
        else if (gamepad2.b && c5 == 2){

            c5 = 3;

        }
        else if (!gamepad2.b && c5 == 3){

            release.setPosition(0.5);
            c5 = 0;

        }
        if(gamepad1.right_trigger < 0.5){

            rightb.setPosition(0.0);

        }
        else if (gamepad1.right_trigger > 0.5) {

            rightb.setPosition(1.0);

        }
        if (gamepad1.left_trigger < 0.5){

            leftb.setPosition(1.0);
            sideleft.setPosition(1.0);

        }
        else if (gamepad1.left_trigger > 0.5){

            leftb.setPosition(0.0);
            sideleft.setPosition(0.0);

        }

    }


}
