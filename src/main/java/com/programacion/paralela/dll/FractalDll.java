package com.programacion.paralela.dll;


import jnr.ffi.LibraryLoader;

import java.io.File;
import java.nio.Buffer;

public interface FractalDll {
    String LIBRARY_NAME = "libfractal_julia"; //DLL

//    static {
//        // Try to load the library from the resources directory
//        String libraryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "win32-x86-64";
//        System.setProperty("java.library.path", libraryPath + File.pathSeparator + System.getProperty("java.library.path", ""));
//    }

    FractalDll INSTANCE = LibraryLoader.create(FractalDll.class).load(LIBRARY_NAME);

    void julia_simd(
            double x_min, double y_min,
            double x_max, double y_max,
            int width, int height, int max_iterations,
            Buffer pixel_buffer);

}
