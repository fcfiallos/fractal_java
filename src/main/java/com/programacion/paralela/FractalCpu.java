package com.programacion.paralela;

import static com.programacion.paralela.FractalParams.*;

public class FractalCpu {
    public int[] pixel_buffer;

    public FractalCpu(){
        pixel_buffer = new int[FractalParams.WIDTH * FractalParams.HEIGHT];
        julia_serial_2();
    }

    int divergente_2(double x, double y) {
        int iter = 1;
        double zr = x;
        double zi = y;

        while ((zr*zr + zi*zi) < 4.0 && iter < FractalParams.max_iterations) {
            double dr = zr*zr - zi*zi + FractalParams.c_real;
            double di = 2.0*zr*zi + FractalParams.c_imag;

            zr = dr;
            zi = di;
            iter++;
        }

        if (iter < FractalParams.max_iterations) {
            int index = iter % FractalParams.PALETTE_SIZE;
            return FractalParams.color_ramp[index];
        }

        return 0xFF000000; // color negro
    }

    void julia_serial_2() {
        double dx = (FractalParams.x_max - FractalParams.x_min) / (FractalParams.WIDTH);
        double dy = (FractalParams.y_max - FractalParams.y_min) / (FractalParams.HEIGHT);

        for (int i = 0; i < FractalParams.WIDTH; i++) {
            for (int j = 0; j < FractalParams.HEIGHT; j++) {
                double x = FractalParams.x_min + i * dx;
                double y = FractalParams.y_min + j * dy;

                var color = divergente_2(x, y);
                pixel_buffer[j * FractalParams.WIDTH + i] = color;
            }
        }
    }
}