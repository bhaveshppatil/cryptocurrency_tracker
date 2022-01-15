package com.bhavesh.cryptocurrencytracker.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.ActivitySignUpBinding
import com.bhavesh.cryptocurrencytracker.ui.login.LoginActivity
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivitySignUpBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        loginWithGoogle()
        auth = Firebase.auth

        dataBinding.apply {
            ivFbSignup.setOnClickListener {
                showToast("currently not available")
            }
            ivGithubSignup.setOnClickListener {
                showToast("currently not available")
            }
            ivInstaSignup.setOnClickListener {
                showToast("currently not available")
            }
            tvHaveAnAccountUp.setOnClickListener {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            ivGoogleSignup.setOnClickListener {
                signIn()
            }
            btnSignup.setOnClickListener {
                checkDataIsValidOrNot();
            }
        }
    }

    private fun checkDataIsValidOrNot() {
        dataBinding.apply {
            val name = etNameUp.text.toString()
            val email = etEmailSignUp.text.toString()
            val number = etNumberUp.text.toString()
            val passwd = etPasswdUp.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && number.length == 10 && passwd.length >= 6) {
                registerNewUser(email, passwd)
            } else {
                showToast("Invalid formation entered")
            }
        }
    }

    private fun registerNewUser(email: String, passwd: String) {
        auth.createUserWithEmailAndPassword(email, passwd)
            .addOnCompleteListener(this@SignUpActivity) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    showToast("Authentication success.")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    showToast("Authentication failed.")
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

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}