package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * Octaedro.java <BR> author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Octaedro implements GLEventListener {

    private float spinX = 0f, spinY = 0f;
    private boolean isRotating = false;

    public static void main(String[] args) {
        Frame frame = new Frame("Octahedron Rotation");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Octaedro());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -6.0f);

        if (isRotating) {
            spinX += 1.0f;
            spinY += 1.0f;
        }

        gl.glRotatef(spinX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(spinY, 0.0f, 1.0f, 0.0f);

        // Draw the octahedron
        drawOctahedron(gl);

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    // Draw the octahedron
    private void drawOctahedron(GL gl) {
        gl.glBegin(GL.GL_TRIANGLES);
        // Top triangle
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-1.0f, 0.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);

        // Bottom triangles
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, -1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-1.0f, 0.0f, 0.0f);

        // Right top triangle
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 1.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);

        // Right bottom triangle
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, -1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 1.0f);

        // Left top triangle
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, -1.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-1.0f, 0.0f, 0.0f);

        // Left bottom triangle
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, -1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 0.0f, 0.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, -1.0f);

        gl.glEnd();
    }

    public void toggleRotation() {
        isRotating = !isRotating;
    }
}
