package andresaba.agilecontent.com.githubgetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.AsyncTask
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.util.ExceptionCatchingInputStream
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_info_screen.*
import org.json.JSONArray
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

class InfoScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_info_screen)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.show()

        AsyncTaskHandleJson().execute()
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg p0: String?): String {
            var text: String
            val connection = URL(theGitHubURL).openConnection() as HttpURLConnection

            try {
                connection.connect()
                text = connection.inputStream.use {
                    it.reader().use { reader -> reader.readText() }
                }
            } catch (ex: IOException) {
                text = ex.toString()
                return text
            } catch (exi: UnknownHostException) {
                text = exi.toString()
                return text
            }

            connection.disconnect()

            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result?.contains("FileNotFoundException") == true) {
                goHome(1)
            } else if (result?.contains("UnknownHostException") == true) {
             goHome(2)
            } else
                makeJsonThing(result)
        }
    }

    private fun goHome(getStringgo : Int) {
        connectionNotMade = getStringgo
        navigateUpTo(Intent(this, HomeScreen::class.java))
        finish()
    }

    private fun makeJsonThing(response : String?) {
        val jsonArray = JSONArray(response)

        val list = ArrayList<RepoInfo>()

        val responseLength = response?.length


            var i = 0
            while (i < jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                list.add(RepoInfo(
                        name = jsonObject.getString("name"),
                        lang = jsonObject.getString("language"),
                        owner = jsonObject.getJSONObject("owner").getString("avatar_url")
                ))

                imageURL = jsonObject.getJSONObject("owner").getString("avatar_url")
                //println(ownerResponse)
                i++
            }

            val adapter = ListAdapter(this, list)
            repos_list_view.adapter = adapter

            Glide.with(this ).
                    load(imageURL).
                    into(imview_info_screen)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        navigateUpTo(Intent(this, HomeScreen::class.java))
        finish()
        return true
    }

    companion object {
        var imageURL = "" // just a test url
        var theGitHubURL = ""
        var connectionNotMade = 0
        var responseString = ""
        var canExecute : Boolean = false
    }
}