package xh.zero.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import xh.zero.test.launcher.App
import xh.zero.test.launcher.AppManager
import xh.zero.test.launcher.ItemOptionView

class LauncherTestActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LauncherTestActivity"

        private var instance: LauncherTestActivity? = null

        fun getLauncher() = instance

        fun setLauncher(launcher: LauncherTestActivity) {
            instance = launcher
        }
    }

    private lateinit var rcAppList: RecyclerView
    private lateinit var adapter: AppAdapter
    private var itemOptionView: ItemOptionView? = null

    private val itemTouchHelper by lazy {
        // 1. Note that I am specifying all 4 directions.
        //    Specifying START and END also allows
        //    more organic dragging than just specifying UP and DOWN.
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or
                    DOWN or
                    START or
                    END, 0) {

                override fun onMove(recyclerView: RecyclerView,
                                    viewHolder: RecyclerView.ViewHolder,
                                    target: RecyclerView.ViewHolder): Boolean {

                    val adapter = recyclerView.adapter as AppAdapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    // 2. Update the backing model. Custom implementation in
                    //    MainRecyclerViewAdapter. You need to implement
                    //    reordering of the backing model inside the method.
                    adapter.moveItem(from, to)
                    // 3. Tell adapter to render the model update.
                    adapter.notifyItemMoved(from, to)

                    return true
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                      direction: Int) {
                    // 4. Code block for horizontal swipe.
                    //    ItemTouchHelper handles horizontal swipe as well, but
                    //    it is not relevant with reordering. Ignoring here.
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher_test)

        setLauncher(this)

        AppManager.getInstance(this).init()
        AppManager.getInstance(this).addUpdateListener { apps ->
            apps.forEach { app ->
                // 获取已安装的app
                Log.d("LauncherTestActivity", "installed app: " + app._label + ", " + app._packageName + ", " + app._className);
            }
            adapter.updateItems(apps)
            false
        }

        itemOptionView = findViewById(R.id.item_option_view)

        rcAppList = findViewById<RecyclerView>(R.id.rc_app_list)

        rcAppList.layoutManager = GridLayoutManager(this, 5)
        adapter = AppAdapter(emptyList())
        rcAppList.adapter = adapter
        itemTouchHelper.attachToRecyclerView(rcAppList)
    }

    private class AppAdapter(
        private var apps: List<App>
    ) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {
        class AppViewHolder(v: View) : RecyclerView.ViewHolder(v)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return AppViewHolder(inflater.inflate(R.layout.item_app_label_icon, parent, false))
        }

        override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
            holder.itemView.layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT
            holder.itemView.layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT

            val ivIcon = holder.itemView.findViewById<ImageView>(R.id.iv_icon)
            val tvLabel = holder.itemView.findViewById<TextView>(R.id.tv_label)
            val tvMark = holder.itemView.findViewById<TextView>(R.id.tv_mark)

            val item = apps[position]
            ivIcon.setImageDrawable(item.icon)
            tvLabel.text = item._label

            holder.itemView.setOnLongClickListener {
                tvMark.visibility = View.VISIBLE
                getLauncher()?.itemOptionView?.showItemPopup()
                return@setOnLongClickListener false
            }



        }

        override fun getItemCount(): Int {
            return apps.size
        }

        fun moveItem(from: Int, to: Int) {
            Log.d(TAG, "from $from to $to")
        }

        fun updateItems(items: List<App>) {
            apps = items
            notifyDataSetChanged()
        }
    }
}