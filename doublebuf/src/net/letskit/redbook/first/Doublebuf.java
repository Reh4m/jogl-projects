package net.letskit.redbook.first;
import net.letskit.redbook.glskeleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;

import javax.media.opengl.GLEventListener;

/**
 * Este programa dibuja un octaedro. Puedes utilizar el ratón para girar el objeto.
 *
 * @author emmarp.
 */
public class Doublebuf extends glskeleton implements GLEventListener, KeyListener, MouseListener {
    private float spin = 0f, spinDelta = 0f;
    private boolean isDragging = false;
    private int lastMouseX, lastMouseY;
    private float xOffset = 0.0f;
    private float yOffset = 0.0f;

    private float[][] vertices = {
        {0.0f, 0.0f, 1.0f},
        {1.0f, 0.0f, 0.0f},
        {0.0f, 1.0f, 0.0f},
        {-1.0f, 0.0f, 0.0f},
        {0.0f, -1.0f, 0.0f},
        {0.0f, 0.0f, -1.0f}
    };

    private int[][] faces = {
        {0, 1, 2},
        {0, 2, 3},
        {0, 3, 4},
        {0, 4, 1},
        {5, 2, 1},
        {5, 3, 2},
        {5, 4, 3},
        {5, 1, 4}
    };

    private float[][] colors = {
        {1.0f, 0.0f, 0.0f},
        {0.0f, 1.0f, 0.0f},
        {0.0f, 0.0f, 1.0f},
        {1.0f, 1.0f, 0.0f},
        {1.0f, 0.0f, 1.0f},
        {0.0f, 1.0f, 1.0f},
        {1.0f, 0.5f, 0.0f},
        {0.5f, 0.0f, 1.0f}
    };

    public Doublebuf() {}

    public static void main(String[] args) {
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);

        GLJPanel canvas = new GLJPanel(caps);
        Doublebuf demo = new Doublebuf();

        demo.setCanvas(canvas);
        canvas.addGLEventListener(demo);
        demo.setDefaultListeners(demo);
        demo.setDefaultAnimator(24);

        JFrame frame = new JFrame("Octaedro");
        frame.setSize(512, 512);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
        demo.animate();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_FLAT);
    }

    public synchronized void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glPushMatrix();
        gl.glRotatef(spin, 1.0f, 0.0f, 0.0f);

        for (int i = 0; i < faces.length; i++) {
            gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3fv(colors[i], 0);
            for (int j = 0; j < 3; j++) {
                gl.glVertex3fv(vertices[faces[i][j]], 0);
            }
            gl.glEnd();
        }

        gl.glPopMatrix();
        gl.glFlush();

        spinDisplay();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL gl = drawable.getGL();

        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        float aspect = (float) w / (float) h;
        float sphereRadius = 1.0f;
        float halfSphereDiameter = 2 * sphereRadius;

        if (w <= h) {
            gl.glOrtho(-halfSphereDiameter, halfSphereDiameter, -halfSphereDiameter / aspect, halfSphereDiameter / aspect, -halfSphereDiameter, halfSphereDiameter);
        } else {
            gl.glOrtho(-halfSphereDiameter * aspect, halfSphereDiameter * aspect, -halfSphereDiameter, halfSphereDiameter, -halfSphereDiameter, halfSphereDiameter);
        }

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    private void spinDisplay() {
        spin = spin + spinDelta;
        if (spin > 360f)
            spin = spin - 360;
    }

    public void keyTyped(KeyEvent key) {}

    public void keyPressed(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                super.runExit();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent key) {}

    public void mouseClicked(MouseEvent key) {    }

    public void mousePressed(MouseEvent mouse) {
        switch (mouse.getButton()) {
            case MouseEvent.BUTTON1:
                spinDelta = 2f;
                break;
            case MouseEvent.BUTTON3:
                spinDelta = 0f;
                break;
        }
        super.refresh();
    }

    public void mouseReleased(MouseEvent mouse) {
    }

    public void mouseEntered(MouseEvent mouse) {
    }

    public void mouseExited(MouseEvent mouse) {
    }
}
