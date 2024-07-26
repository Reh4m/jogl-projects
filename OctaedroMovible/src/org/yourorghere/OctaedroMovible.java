package org.yourorghere;

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;

/**
 * Construir tres solidos regulares con un número 8 caras (Octaedro).
 * Cada uno debe estar inscritos en una esfera de diametro de máximo 2 unidades.
 * Cada cara debe tener un color diferente.
 * Cada solido debe estar girando sobre su eje.
 * Al arrastrar el mouse los tres deben girar en el ejex y el eje y dependiendo del movimiento del raton.
 * 
 * author: Emmanuel Ruiz Pérez 20030124
 */

public class OctaedroMovible implements GLEventListener, MouseListener, MouseMotionListener {
    public static void main(String[] args) {
        Frame frame = new Frame("Octaedro");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new OctaedroMovible());
        frame.add(canvas);
        frame.setSize(800, 600);

        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
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

        frame.show();
        animator.start();
    }

    private float view_rotx = 20.0f, view_roty = 30.0f, view_rotz = 0.0f;
    private float angle = 0.0f;
    private int octahedron1, octahedron2, octahedron3;

    private int prevMouseX, prevMouseY;
    private boolean mouseRButtonDown = false;

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.setSwapInterval(1);

        octahedron1 = gl.glGenLists(1);
        gl.glNewList(octahedron1, GL.GL_COMPILE);
        drawOctahedron(gl);
        gl.glEndList();

        octahedron2 = gl.glGenLists(1);
        gl.glNewList(octahedron2, GL.GL_COMPILE);
        drawOctahedron(gl);
        gl.glEndList();

        octahedron3 = gl.glGenLists(1);
        gl.glNewList(octahedron3, GL.GL_COMPILE);
        drawOctahedron(gl);
        gl.glEndList();

        gl.glEnable(GL.GL_NORMALIZE);

        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        float h = (float) height / (float) width;

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glFrustum(-1.0f, 1.0f, -h, h, 5.0f, 60.0f);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -40.0f);
    }

    public void display(GLAutoDrawable drawable) {
        angle += 2.0f;

        GL gl = drawable.getGL();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);

        gl.glPushMatrix();
        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);

        // Dibuja el primer octaedro
        gl.glPushMatrix();
        gl.glTranslatef(-2.0f, 0.0f, 0.0f);
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        gl.glCallList(octahedron1);
        gl.glPopMatrix();

        // Dibuja el degundo octaedro
        gl.glPushMatrix();
        gl.glTranslatef(2.0f, 0.0f, 0.0f);
        gl.glRotatef(angle, 1.0f, 0.0f, 0.0f);
        gl.glCallList(octahedron2);
        gl.glPopMatrix();

        // Dibuja el primer octaedro
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 2.0f);
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
        gl.glCallList(octahedron3);
        gl.glPopMatrix();

        gl.glDisable(GL.GL_CULL_FACE);
        gl.glPopMatrix();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public static void drawOctahedron(GL gl) {
        float[][] vertices = {
            {0.0f, 0.0f, 1.0f},
            {1.0f, 0.0f, 0.0f},
            {0.0f, 1.0f, 0.0f},
            {-1.0f, 0.0f, 0.0f},
            {0.0f, -1.0f, 0.0f},
            {0.0f, 0.0f, -1.0f}
        };

        int[][] faces = {
            {0, 1, 2},
            {0, 2, 3},
            {0, 3, 4},
            {0, 4, 1},
            {5, 2, 1},
            {5, 3, 2},
            {5, 4, 3},
            {5, 1, 4}
        };

        float[][] colors = {
            {1.0f, 0.0f, 0.0f},
            {0.0f, 1.0f, 0.0f},
            {0.0f, 0.0f, 1.0f},
            {1.0f, 1.0f, 0.0f},
            {1.0f, 0.0f, 1.0f},
            {0.0f, 1.0f, 1.0f},
            {1.0f, 0.5f, 0.0f},
            {0.5f, 0.0f, 1.0f}
        };

        for (int i = 0; i < faces.length; i++) {
            gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3fv(colors[i], 0);
            for (int j = 0; j < 3; j++) {
                gl.glVertex3fv(vertices[faces[i][j]], 0);
            }
            gl.glEnd();
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        prevMouseX = e.getX();
        prevMouseY = e.getY();
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            mouseRButtonDown = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            mouseRButtonDown = false;
        }
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();

        float thetaY = 360.0f * ((float) (x - prevMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);

        prevMouseX = x;
        prevMouseY = y;

        view_rotx += thetaX;
        view_roty += thetaY;
    }

    public void mouseMoved(MouseEvent e) {}
}

