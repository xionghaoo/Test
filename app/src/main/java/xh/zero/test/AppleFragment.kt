package xh.zero.test

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class AppleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apple, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_start).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_appleFragment_to_bananaFragment)
        }

        view.findViewById<View>(R.id.btn_open_gl).setOnClickListener {
            startActivity(Intent(activity, OpenGLES20Activity::class.java))
        }

        view.findViewById<View>(R.id.btn_launcher_test).setOnClickListener {
            startActivity(Intent(activity, LauncherTestActivity::class.java))
        }
    }

    companion object {
        fun newInstance() = AppleFragment()
    }
}