package com.programacion.paralela;

import com.programacion.paralela.dll.FractalDll;

import java.nio.ByteBuffer;

public class FractalSimd {
    public ByteBuffer pixel_buffer;

    public FractalSimd() {
        pixel_buffer = ByteBuffer.allocateDirect(FractalParams.WIDTH * FractalParams.HEIGHT * 4);
    }

    void julia_simd() {
        FractalDll.INSTANCE.julia_simd(FractalParams.x_min,
                FractalParams.y_min,
                FractalParams.x_max,
                FractalParams.y_max,
                FractalParams.WIDTH,
                FractalParams.HEIGHT, FractalParams.max_iterations,
                pixel_buffer);
    }
}
