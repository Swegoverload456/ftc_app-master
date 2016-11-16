package org.firstinspires.ftc.robotcontroller.internal.testcode;

import android.graphics.Color;

import com.qualcomm.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "colorsensor", group = "Teleop")
/**
 * Created by juanjose1 on 9/29/16.
 */
public class colorsensor extends OpMode {

    Servo arm;

    ColorSensor color;

    float hsvValues[] = {0F,0F,0F};


    final float values[] = hsvValues;


    @Override
    public void init(){

        arm = hardwareMap.servo.get("arm");

        color = hardwareMap.colorSensor.get("color");

    }
    @Override
    public void loop(){

        color.enableLed(false);

        Color.RGBToHSV(color.red() * 8, color.green() * 8, color.blue() * 8, hsvValues);

        if (color.red() * 8 > color.blue() * 8){

            arm.setPosition(1.0);

        }
        else if(color.red() * 8 < color.blue() * 8){

            arm.setPosition(0.0);

        }
        else if(color.red() * 8 < 0.0 && color.blue() * 8 < 0.0){

            arm.setPosition(0.5);

        }

    }


}
