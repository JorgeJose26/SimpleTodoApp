package com.example.todonetworkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class MainActivity : AppCompatActivity() {
    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container,IntroFragment.newInstance(),IntroFragment.javaClass.canonicalName)
            .commit();
        account = Auth0(
            "8ocgOi0lfwPxBQM1Q3P4JHLO5KVYfsZT",
            "dev-l8knny3r.us.auth0.com"
        )

    }
    public fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope

            WebAuthProvider.login(account)
                .withScheme("demo")
                .withScope("openid profile email")
                // Launch the authentication passing the callback where the results will be received
                .start(this, object :
                    Callback<Credentials, AuthenticationException> {
                    // Called when there is an authentication failure
                    override fun onFailure(exception: AuthenticationException) {
                        // Something went wrong!
                    }

                    // Called when authentication completed successfully
                    override fun onSuccess(credentials: Credentials) {
                        // Get the access token from the credentials object.
                        // This can be used to call APIs
                        // TODO: securely store access token for future usage (later)
                        val accessToken = credentials.accessToken
                        println(accessToken)

                        // TODO: Go to the TodoActivity

                    }
                })
        }
    }
