package org.firstinspires.ftc.robotcontroller.internal.testcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by juanjose1 on 11/15/16.
 */
@TeleOp(name ="color test", group = "OPmode")
public class color_method extends LinearOpMode{

    ColorSensor colorleft;
    ColorSensor colorright;

    Servo leftb;
    Servo rightb;

    float hsvValues[] = {0F,0F,0F};


    final float values[] = hsvValues;



    @Override
    public void runOpMode() throws InterruptedException{

        colorleft = hardwareMap.colorSensor.get("colorl");
        colorright = hardwareMap.colorSensor.get("colorr");

        leftb = hardwareMap.servo.get("leftb");
        rightb = hardwareMap.servo.get("rightb");

        colorleft.enableLed(false);
        colorright.enableLed(false);

        waitForStart();

        colordetect(true , false);
        sleep(1000);

    }

    public void colordetect(boolean hsvValuesred, boolean hsvValuesblue){




        if (colorleft.red() * 8 > 0.0 && colorright.blue() * 8 > 0.0){

            hsvValuesred = true;
            hsvValuesblue = false;
            telemetry.addData("Red", String.format("03d", hsvValuesred));

        }
        else if (colorright.red() * 8 > 0.0 && colorleft.blue() * 8 > 0.0){

            hsvValuesred = false;
            hsvValuesblue = true;
            telemetry.addData("Red", String.format("03d", hsvValuesred));

        }
        else if (colorleft.red() * 8 == 0.0 && colorright.blue() * 8 == 0.0){

            hsvValuesred = false;
            hsvValuesblue = false;
            telemetry.addData("Red", String.format("03d", hsvValuesred));
            telemetry.addData("Blue", String.format("03d", hsvValuesblue));

        }

       /* if (colorleft.blue() * 8 > colorright.blue() * 8){

            hsvValuesblue = true;
            telemetry.addData("Blue Detected", String.format("03d", hsvValuesblue));

        }
        else if (colorleft.blue() * 8 < colorright.blue() * 8){

            hsvValuesblue = false;
            telemetry.addData("Blue Not Detected", String.format("03d", hsvValuesblue));

        }*/
        if (hsvValuesred == true && hsvValuesblue == false){

            leftb.setPosition(1.0);
            rightb.setPosition(0.0);

        }
        else if (hsvValuesblue == true && hsvValuesred == false){

            leftb.setPosition(0.0);
            rightb.setPosition(1.0);

        }
        else if (hsvValuesblue == false && hsvValuesred == false){

            leftb.setPosition(0.0);
            rightb.setPosition(0.0);

        }

    }

}
