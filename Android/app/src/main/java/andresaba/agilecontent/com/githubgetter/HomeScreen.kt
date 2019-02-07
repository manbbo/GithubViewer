package andresaba.agilecontent.com.githubgetter

import android.content.Intent
import android.icu.text.IDNA
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_home_screen.*
import okhttp3.*
import java.io.IOException

public class HomeScreen : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_screen)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.show()

        // Set up the user interaction to manually show or hide the system UI.
        search_button.setOnClickListener {
            if (user_name_text.text.toString() != null && user_name_text.text.toString() != "") {
                InfoScreen.theGitHubURL = "https://api.github.com/users/${user_name_text.text.toString()}/repos"

                val intent = Intent(this, InfoScreen::class.java)
                startActivity(intent)
            }
        }

        if (InfoScreen.connectionNotMade == 1) {
            createAlert("User not found. Please enter another name")
        } else if (InfoScreen.connectionNotMade == 2) {
            createAlert("A network error has occurred. " +
            "Check your internet connection and try again later")
        }
    }

    private fun createAlert(text : String) {
        val alert = AlertDialog.Builder(this)
        alert.setMessage("ERROR - $text")
                .setPositiveButton("OK", null)
        var dialog = alert.create()
        dialog.setTitle("ERROR")
        alert.show()
        InfoScreen.connectionNotMade = 0
    }
}