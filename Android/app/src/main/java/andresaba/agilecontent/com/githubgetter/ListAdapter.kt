package andresaba.agilecontent.com.githubgetter

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ListAdapter (val context : Context, val list : ArrayList<RepoInfo>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view  = LayoutInflater.from(context).inflate(R.layout.row_layout, p2, false)

        val lang = view?.findViewById(R.id.repoLang) as AppCompatTextView
        val name = view?.findViewById(R.id.repoName) as AppCompatTextView

        lang.setText(list[p0].lang)
        name.setText(list[p0].name)

        return view
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}