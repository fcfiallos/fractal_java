package com.programacion.paralela;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class FractalMain {

    private long window;
    private int textureID;

    private IntBuffer intBuffer;

    FractalCpu cpu;

    FractalMain() {
        // Lo ideal es definir el 1600*900 como constante
        intBuffer = BufferUtils.createIntBuffer(1600*900);
        cpu = new FractalCpu();
    }

    public void run() {
        System.out.println("Fractal Julia" + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");
        // configuración de GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        //Crea la ventana por eso se define el tamaño
        window = glfwCreateWindow(1600, 900, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // aqui epecificaremos el subir/bajar iteraciones
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);

            if (key == GLFW_KEY_UP && action == GLFW_RELEASE ){
                FractalParams.max_iterations+=10;
            }
            if (key == GLFW_KEY_DOWN && action == GLFW_RELEASE ){
                FractalParams.max_iterations-=10;
                if (FractalParams.max_iterations<0) FractalParams.max_iterations=10;
            }
        });
        //Centra la ventana
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,(vidmode.width()-1600)/2,(vidmode.height()-900)/2);

        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        GL.createCapabilitiesWGL();

        String version=GL11.glGetString(GL11.GL_VERSION);
        String vendor =GL11.glGetString(GL11.GL_VENDOR);
        String renderer =GL11.glGetString(GL11.GL_RENDERER);

        System.out.println("OpenGL version: " + version);
        System.out.println("OpenGL vendor: " + vendor);
        System.out.println("OpenGL renderer: " + renderer);

        {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-1, 1, -1, 1, -1, 1);// al ser ortogonar no se ve afectada las v4 y v5 ya que esos pertenecen al eje z
            glMatrixMode(GL_MODELVIEW);
            glEnable(GL_TEXTURE_2D);
            glLoadIdentity();
        }

        glfwSwapInterval(1);

        glfwShowWindow(window);

        setUpTexture();
    }

    void setUpTexture(){
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        // en el codigo c++ se dejo creando para RGBA con 8 pixeles
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,1600,900,0,GL_RGBA,GL_UNSIGNED_BYTE,NULL);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // liempia color y profundidad
            paint();

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }

    void paint() {
        // dibujar

        cpu.julia_serial_2();
        intBuffer.put(cpu.pixel_buffer);

        glEnable(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA8,
                1600,900,0,
                GL_RGBA,GL_UNSIGNED_BYTE,
                intBuffer
        );

        glBegin(GL_QUADS);
        {
            glTexCoord2f(0,0);
            glVertex2f(-1, -1);

            glTexCoord2f(0,1);
            glVertex2f(-1, 1);

            glTexCoord2f(1,1);
            glVertex2f(1, 1);

            glTexCoord2f(1,0);
            glVertex2f(1, -1);
        }
        glEnd();
    }

    public static void main(String[] args) {
        new FractalMain().run();
    }

}
