package com.esgi.steamstorereactive.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import com.esgi.steamstorereactive.model.CredentialsTransfer

class LoginFragment : Fragment(){

    val requestObject: NetworkRequest = NetworkRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.login_view, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val email = view.findViewById<EditText>(R.id.email_input)
        val password = view.findViewById<EditText>(R.id.password_input)
        val connect = view.findViewById<Button>(R.id.connect)
        val signup = view.findViewById<Button>(R.id.signup)
        val forgotPassword = view.findViewById<TextView>(R.id.forgot_password)

        connect.setOnClickListener { _ ->
            // viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {  }
            // val credentialsResponse = validateCredentials(email, password)
            if (validateCredentials(email, password)/*credentialsRespnse.success*/) {
                // val user = credentialsResponse.user
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment(1.toString()/*user.id*/)
                )
            } else {
                Toast.makeText(view.context, "Credentials incorrectes", Toast.LENGTH_SHORT).show()
            }
        }

        signup.setOnClickListener { _ ->
            if (email.text.isNotEmpty()) {
                if (password.text.isNotEmpty()) {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToSignupFragment(
                            CredentialsTransfer(email.text.toString(), password.text.toString())
                        )
                    )
                } else {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToSignupFragment(
                            CredentialsTransfer(email.text.toString(), null)
                        )
                    )
                }
            } else {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToSignupFragment(null)
                )
            }
        }

        forgotPassword.setOnClickListener { _ ->
            if (email.text.isNotEmpty()) {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(email.text.toString())
                )
            } else {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(null)
                )
            }
        }
    }

    private /*suspend*/ fun validateCredentials(email: TextView, password: TextView): Boolean {
//        return withContext(Dispatchers.IO) {
//                requestObject.validateCredentials(email, password) : UserResponse
//       }
        return (email.text.toString() == "toto" && password.text.toString() == "toto")
    }
}