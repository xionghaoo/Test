package xh.zero.test

import android.app.Activity
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
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

        // vPMatrix is an abbreviation for "Model View Projection Matrix"
        private val vPMatrix = FloatArray(16)
        // 投影矩阵
        private val projectionMatrix = FloatArray(16)
        private val viewMatrix = FloatArray(16)

        override fun onDrawFrame(gl: GL10?) {
//            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

            // Set the camera position (View matrix)
            Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

            // Calculate the projection and view transformation
            Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

            mTriangle.draw(vPMatrix)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
//            GLES20.glViewport(0, 0, width, height)
            GLES20.glViewport(0, 0, width, height)

            val ratio: Float = width.toFloat() / height.toFloat()

            // this projection matrix is applied to object coordinates
            // in the onDrawFrame() method
            Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//            GLES20.glClearColor(0f, 0f, 0f, 1f)
            mTriangle = Triangle()
        }
    }
}