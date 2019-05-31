package philosophicas.org.avgasyv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

var auth : FirebaseAuth? = null
var currentUser : FirebaseUser? = null

class MainActivity : AppCompatActivity() {

    lateinit var callbackManager: CallbackManager

    class MyFacebookCallback(var activity: MainActivity) : FacebookCallback<LoginResult> {


        override fun onSuccess(result: LoginResult?) {

            val token = result!!.accessToken.token
            val credential = FacebookAuthProvider.getCredential(token)
            auth?.signInWithCredential(credential)
                ?.addOnCompleteListener(activity) {
                    if(it.isSuccessful) {
                        currentUser = auth?.currentUser
                        activity.startActivity(
                            Intent(activity, AirportsStatusActivity::class.java)
                        )
                    }
                }

        }

        override fun onCancel() {
            Log.d("Facebook loh", "canceled")
        }


        override fun onError(error: FacebookException?) {
            Log.d("Facebook loh", error?.message)
            Toast.makeText(activity,error?.localizedMessage.toString(),Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Initialize facebook logoing
        AppEventsLogger.activateApp(application)

        //Configura facebook callbacks
        callbackManager = CallbackManager.Factory.create()
        loginButton.registerCallback(callbackManager, MyFacebookCallback(this))

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        currentUser = auth?.currentUser

        if (currentUser != null ) {

            this.startActivity(
                Intent(this, AirportsStatusActivity::class.java)
            )
        }
    }



}
