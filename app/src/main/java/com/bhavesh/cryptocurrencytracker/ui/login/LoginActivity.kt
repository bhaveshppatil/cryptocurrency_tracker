package com.bhavesh.cryptocurrencytracker.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.ActivityLoginBinding
import com.bhavesh.cryptocurrencytracker.ui.signup.SignUpActivity
import com.bhavesh.cryptocurrencytracker.ui.view.HomeActivity
import com.bhavesh.cryptocurrencytracker.utils.Constant.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var dataBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginWithGoogle()
        auth = FirebaseAuth.getInstance()

        dataBinding.apply {
            tvNewUser.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            ivGoogleLogin.setOnClickListener {
                signIn()
            }
            btnLoginUp.setOnClickListener {
                checkDataValidOrNot()
            }
            ivFacebookLogin.setOnClickListener {
                showToast("currently not available")
            }
            ivGithubLogin.setOnClickListener {
                showToast("currently not available")
            }
            ivInstaLogin.setOnClickListener {
                showToast("currently not available")
            }
            tvForgetPasswd.setOnClickListener {
                showToast("Try to login with new account")
            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun loginWithGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun checkDataValidOrNot() {
        dataBinding.apply {
            progressCircular.visibility = View.VISIBLE
            val email = etEmail.text.toString()
            val passwd = etPasswd.text.toString()

            if (email.isNotEmpty() && passwd.length >= 6) {
                signInUser(email, passwd)
            } else {
                showToast("Invalid formation entered")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = auth.currentUser
                        showToast("Login Successful")
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                    }
                })
    }

    private fun signInUser(email: String, passwd: String) {
        auth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                dataBinding.progressCircular.visibility = View.GONE
                showToast("Authentication success.")
                Log.d("TAG", "createUserWithEmail:success")
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Log.w("TAG", "createUserWithEmail:failure", it.exception)
                showToast("Authentication failed.")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}