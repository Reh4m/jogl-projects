package org.yourorghere;

import java.awt.event.*;
import javax.swing.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

/**
 * This program demonstrates polygon stippling.
 *
 * @author Emmanuel Ruiz Pérez
 */
public class PoligonosPunteados implements GLEventListener, KeyListener {

    private GLU glu;

    private byte stipplePattern[] = {
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xc0, (byte) 0x00, (byte) 0x00,
        (byte) 0x01, (byte) 0xf0, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0xf0, (byte) 0x0f, (byte) 0x00,
        (byte) 0x1f, (byte) 0xe0, (byte) 0x1f, (byte) 0x80, (byte) 0x1f, (byte) 0xc0, (byte) 0x0f, (byte) 0xc0,
        (byte) 0x3f, (byte) 0x80, (byte) 0x07, (byte) 0xe0, (byte) 0x7e, (byte) 0x00, (byte) 0x03, (byte) 0xf0,
        (byte) 0xff, (byte) 0x80, (byte) 0x03, (byte) 0xf5, (byte) 0xff, (byte) 0xe0, (byte) 0x07, (byte) 0xfd,
        (byte) 0xff, (byte) 0xf8, (byte) 0x1f, (byte) 0xfc, (byte) 0xff, (byte) 0xe8, (byte) 0xff, (byte) 0xe3,
        (byte) 0xbf, (byte) 0x70, (byte) 0xde, (byte) 0x80, (byte) 0xb7, (byte) 0x00, (byte) 0x71, (byte) 0x10,
        (byte) 0x4a, (byte) 0x80, (byte) 0x03, (byte) 0x10, (byte) 0x4e, (byte) 0x40, (byte) 0x02, (byte) 0x88,
        (byte) 0x8c, (byte) 0x20, (byte) 0x05, (byte) 0x05, (byte) 0x04, (byte) 0x40, (byte) 0x02, (byte) 0x82,
        (byte) 0x14, (byte) 0x40, (byte) 0x02, (byte) 0x40, (byte) 0x10, (byte) 0x80, (byte) 0x02, (byte) 0x64,
        (byte) 0x1a, (byte) 0x80, (byte) 0x00, (byte) 0x92, (byte) 0x29, (byte) 0x00, (byte) 0x00, (byte) 0xb0,
        (byte) 0x48, (byte) 0x00, (byte) 0x00, (byte) 0xc8, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte) 0x85,
        (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x10, (byte) 0x00
    };

    public PoligonosPunteados() {}

    public static void main(String[] args) {
        GLCapabilities caps = new GLCapabilities();
        
        GLJPanel canvas = new GLJPanel(caps);
        PoligonosPunteados demo = new PoligonosPunteados();
        
        canvas.addGLEventListener(demo);
        canvas.addKeyListener(demo);

        JFrame frame = new JFrame("PoligonosPunteados");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        glu = new GLU();

        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_FLAT);
    }

    public synchronized void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        gl.glEnable(GL.GL_POLYGON_STIPPLE);
        gl.glPolygonStipple(stipplePattern, 0);
        gl.glRectf(125.0f, 25.0f, 325.0f, 225.0f);

        gl.glDisable(GL.GL_POLYGON_STIPPLE);
        
        gl.glFlush();
    }
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL gl = drawable.getGL();

        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, (double) w, 0.0, (double) h);
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    public void keyTyped(KeyEvent key) {}
    
    public void keyPressed(KeyEvent key) {}

    public void keyReleased(KeyEvent key) {}
}
