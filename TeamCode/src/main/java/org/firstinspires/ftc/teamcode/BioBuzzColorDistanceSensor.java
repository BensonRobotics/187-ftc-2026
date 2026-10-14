package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class BioBuzzColorDistanceSensor {

    public enum color{
        RED, YELLOW, BLUE, UNKNOWN
    }
    private NormalizedColorSensor test_color;

    private RevColorSensorV3 colorSensorV3;

    public BioBuzzColorDistanceSensor(HardwareMap hardwareMap,
                                      String ColorSensorName){
        test_color = hardwareMap.get(NormalizedColorSensor.class, ColorSensorName);
        test_color.setGain(18.0f);
        colorSensorV3 = hardwareMap.get(RevColorSensorV3.class, ColorSensorName);
    }

    public double GetDistance(){
        double rawProximity = colorSensorV3.getDistance(DistanceUnit.MM);
        return rawProximity;
    }

    float normalizedRgbaToHsv2(float r, float g, float b) {

        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float delta = max - min;

        // 1. Calculate Value (V)
        float v = max;

        // 2. Calculate Saturation (S)
        float s = 0;
        float hue = 0;
        if (max > 0.0f) {
            s = delta / max;
        } else {
            // R = G = B = 0
            s = 0.0f;
            hue = 0.0f; // Hue is undefined, but set to 0
            return hue;
        }

        // 3. Calculate Hue (H)
        if (delta == 0.0f) {
            hue = 0.0f; // Achromatic case (grey)
        } else {
            if (max == r) {
                hue = (g - b) / delta + (g < b ? 6.0f : 0.0f);
            } else if (max == g) {
                hue = (b - r) / delta + 2.0f;
            } else if (max == b) {
                hue = (r - g) / delta + 4.0f;
            }
            hue *= 60.0f; // Convert to degrees
        }

        return hue;
    }

    public color GetColor() {
        //test_color2 = hardwareMap.get(RevColorSensorV3.class, "test_color");


        NormalizedRGBA colors = test_color.getNormalizedColors();
        float redgain = 1.05f;
        float greengain = 0.95f;
        float bluegain = 1.0f;
        float hue = normalizedRgbaToHsv2(colors.red * redgain, colors.green * greengain, colors.blue * bluegain);
        //System.out.println(hue); // Output: H: 30.0°, S: 1.00, V: 1.00
        //Determining the amount of red, green, and blue




        color c= color.UNKNOWN;
        if (49.9 < hue && hue < 175) {
        c= color.YELLOW;
        } else if (330 < hue || hue < 45) {
        c= color.RED;
        } else if (180 < hue && hue < 275) {
        c= color.BLUE;
        }
    return c;


    }


}
