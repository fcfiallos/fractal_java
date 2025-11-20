package com.programacion.paralela;

public class FractalParams {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    public static final int PALETTE_SIZE = 16;

    public static double x_min=-1.5;
    public static double x_max=1.5;
    public static double y_min=-1;
    public static double y_max=1;

    public static int max_iterations=10;

    public static double c_real = -0.7;
    public static double c_imag = 0.27815;
    public static int bswap32(int a) {
        return ((a & 0x000000FF) << 24) |
                ((a & 0x0000FF00) << 8)  |
                ((a & 0x00FF0000) >>> 8) |
                ((a & 0xFF000000) >>> 24);
    }
    public static  final int[]  color_ramp={
      (0xF3FF0FFF),
            (0xEDEF18FF),
            (0xE8E022FF),
            (0xE3D02BFF),
            (0xDDC135FF),
            (0xD8B23FFF),
           (0xD3A248FF),
           (0xCD9352FF),
           (0xC8835BFF),
            (0xC37465FF),
            (0xBD656FFF),
           (0xB85578FF),
          (0xB34682FF),
            (0xAD368BFF),
           (0xA82795FF),
         (0xA3189FFF)};

}
