package com.esgi.steamstorereactive.signin

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.CredentialsTransfer
import com.esgi.steamstorereactive.model.User
import com.esgi.steamstorereactive.model.UserResponse
import kotlinx.coroutines.GlobalScope

class SignupFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.signup_view, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userCredential: CredentialsTransfer? = arguments?.get("credentials") as CredentialsTransfer?

        val username = view.findViewById<EditText>(R.id.username_input)
        val email = view.findViewById<EditText>(R.id.email_input)
        val password = view.findViewById<EditText>(R.id.password_input)
        val verifyPassword = view.findViewById<EditText>(R.id.verify_password)
        val connect = view.findViewById<Button>(R.id.connect)

        if (userCredential?.email?.isNotEmpty() == true) email.text.append(userCredential.email)
        if (userCredential?.password?.isNotEmpty() == true) password.text.append(userCredential.password)

        verifyPassword.setOnKeyListener(View.OnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN && verifyPassword.text.toString() != password.text.toString()) {
                verifyPassword.setBackgroundResource(R.drawable.verification_wrong)
                verifyPassword.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.warning, 0)
                return@OnKeyListener true
            } else if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN && verifyPassword.text.toString() == password.text.toString()) {
                verifyPassword.setBackgroundColor(resources.getColor(R.color.light_dark))
                verifyPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                return@OnKeyListener true
            }
            false
        })

        connect.setOnClickListener { _ ->
            if (username.text.isNotEmpty() &&
                email.text.isNotEmpty() &&
                password.text.toString().isNotEmpty() &&
                verifyPassword.text.toString().isNotEmpty() &&
                verifyPassword.text.toString() == password.text.toString()
            ) {
                // viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {  }
                val newUser = register(username.text.toString(), email.text.toString(), password.text.toString())
                if(newUser.success) {
                    findNavController().navigate(
                        SignupFragmentDirections.actionSignupFragmentToHomeFragment(newUser.user?.id)
                    )
                } else {
                    Toast.makeText(view.context, "Credentials incorrectes", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private /*suspend*/ fun register(username: String, email: String, password: String) : UserResponse {
//        return withContext(Dispatchers.IO) {
//                requestObject.registerUser(email, password) : UserResponse
//       }
        return UserResponse(success = true, user = User(1.toString(), username, CredentialsTransfer(email, password)))
    }
}