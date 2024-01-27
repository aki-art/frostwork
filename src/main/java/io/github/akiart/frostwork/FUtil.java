package io.github.akiart.frostwork;

import org.joml.Vector3f;

public class FUtil {

    public static float remap (float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    public static class Color {

        public static Vector3f toVector(int color) {
            return new Vector3f((float) getR(color), (float) getG(color), (float) getB(color));
        }

        public static int toARGB(int rgba) {
            return rgba >> 8 | 0xFF000000;
        }

        public static int toRGBA(int argb) {
            return argb << 8 | 0x000000FF;
        }

        public static double getR(int argb) { return (double)(argb >> 16 & 255) / 255; }
        public static double getG(int argb) { return (double)(argb >> 8 & 255) / 255; }
        public static double getB(int argb) { return (double)(argb & 255) / 255; }
    }
}
