package xh.zero.test

import android.app.Activity
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLES20Activity : Activity() {

    lateinit var glSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView = MyGLSurfaceView(this)
        setContentView(glSurfaceView)
    }

    class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {
        private var renderer: MyGLRenderer
        init {
            setEGLContextClientVersion(2)
            renderer = MyGLRenderer()
            setRenderer(renderer)
        }
    }

    class MyGLRenderer : GLSurfaceView.Renderer {

        private lateinit var mTriangle: Triangle
//    private lateinit var mSquare: Square

        override fun onDrawFrame(gl: GL10?) {
//            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
            mTriangle.draw()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
//            GLES20.glViewport(0, 0, width, height)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//            GLES20.glClearColor(0f, 0f, 0f, 1f)
            mTriangle = Triangle()
        }
    }
}