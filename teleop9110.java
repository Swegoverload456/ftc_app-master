package org.firstinspires.ftc.robotcontroller.internal.testcode;

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
        if (gamepad1.a == true) {

            shooter.setPower(1.0);

        }
        else if(gamepad1.a == false){

            shooter.setPower(0.0);

        }
        if (gamepad1.b && gamepad1.x == false && gamepad1.dpad_left == false){

            outtake.setPower(1.0);


        }
        else if (gamepad1.b == false && gamepad1.x && gamepad1.dpad_left == false){

            outtake.setPower(0.0);


        }
        else if (!gamepad1.b && !gamepad1.x && gamepad1.dpad_left){

            outtake.setPower(-1.0);

        }
        if (gamepad1.y){

            link.setPosition(0.0);

        }
        else{

            link.setPosition(1);

        }
        if (gamepad1.left_bumper == true && gamepad1.right_bumper == false) {

            intake.setPower(1.0);

        } else if(gamepad1.left_bumper == false && gamepad1.right_bumper == true){

            intake.setPower(-1.0);

        } else{

            intake.setPower(0.0);

        }
        if (gamepad1.dpad_up){

            release.setPosition(0.0);

        }
        else if (gamepad1.dpad_down){

            release.setPosition(0.5);

        }
        if(gamepad1.right_trigger < 0.5){

            rightb.setPosition(1.0);

        }
        else if (gamepad1.right_trigger > 0.5) {

            rightb.setPosition(0.0);

        }
        if (gamepad1.left_trigger < 0.5){

            leftb.setPosition(0.0);

        }
        else if (gamepad1.left_trigger > 0.5){

            leftb.setPosition(1.0);

        }

    }


}
