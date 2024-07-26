
// External imports
import java.awt.*;
import java.awt.event.*;


import com.sun.opengl.util.Animator;
import javax.media.opengl.GL;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.GLEventListener;

// Local imports
// None

/**
 * Example application that demonstrates how to put together a single-threaded
 * rendering system.
 *
 * @author Justin Couch
 * @version $Revision: 1.5 $
 */
public class HelloWorldDemo extends Frame
    implements GLEventListener
{
    public HelloWorldDemo()
    {
        super("Basic JOGL Demo");

        setLayout(new BorderLayout());

        setSize(400, 400);
        setLocation(40, 40);

        // Need to set visible first before starting the rendering thread due
        // to a bug in JOGL. See JOGL Issue #54 for more information on this.
        // http://jogl.dev.java.net
        setVisible(true);

        setupJOGL();
    }

    //---------------------------------------------------------------
    // Methods defined by GLEventListener
    //---------------------------------------------------------------

    /**
     * Called by the drawable immediately after the OpenGL context is
     * initialized; the GLContext has already been made current when
     * this method is called.
     *
     * @param drawable The display context to render to
     */
    public void init(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();

        gl.glClearColor(0, 0, 0, 0);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 1, 0, 1, -1, 1);
    }

    /**
     * Called by the drawable when the surface resizes itself. Used to
     * reset the viewport dimensions.
     *
     * @param drawable The display context to render to
     */
    public void reshape(GLAutoDrawable drawable,
                        int x,
                        int y,
                        int width,
                        int height)
    {
    }

    /**
     * Called by the drawable when the display mode or the display device
     * associated with the GLDrawable has changed
     */
    public void displayChanged(GLAutoDrawable drawable,
                               boolean modeChanged,
                               boolean deviceChanged)
    {
    }

    /**
     * Called by the drawable to perform rendering by the client.
     *
     * @param drawable The display context to render to
     */
    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glBegin(GL.GL_TRIANGLES);

        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(0.25f, 0.25f, 0);

        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(0.5f, 0.25f, 0);

        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(0.25f, 0.5f, 0);

        gl.glEnd();
        gl.glFlush();
    }

    //---------------------------------------------------------------
    // Local methods
    //---------------------------------------------------------------

    /**
     * Create the basics of the JOGL screen details.
     */
    private void setupJOGL()
    {
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);

        GLCanvas canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);

        add(canvas, BorderLayout.CENTER);

        Animator anim = new Animator(canvas);
        anim.start();
    }

    public static void main(String[] args)
    {
        HelloWorldDemo demo = new HelloWorldDemo();
        demo.setVisible(true);
    }
}