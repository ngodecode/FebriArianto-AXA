package id.fxlib.crawler.febriarianto_axa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.fxlib.crawler.febriarianto_axa.model.UserContent
import id.fxlib.crawler.list.ListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        val list    = ArrayList<UserContent>()
        val adapter = ListAdapter(list)
        rcvList.adapter = adapter

        viewModel.data.observe(this, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.tabCounter.observe(this, Observer {
            for(i in 0 until it) {
                var tab  = Button(this)
                tab.text = "Tab $i"

                var param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                param.leftMargin = 20

                tabLayout.addView(tab, param)

                tab.setOnClickListener {
                    viewModel.fetchTab(i)
                }
            }
        })

        viewModel.fetch()

    }
}
