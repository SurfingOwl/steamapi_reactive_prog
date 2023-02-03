package com.esgi.steamstorereactive.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.esgi.steamstorereactive.R
import com.esgi.steamstorereactive.http.NetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordFragment : Fragment() {

    val requestObject: NetworkRequest = NetworkRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.password_forgotten_view, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val email = view.findViewById<EditText>(R.id.email_input)
        val send = view.findViewById<Button>(R.id.connect)
        val newPassword = view.findViewById<TextView>(R.id.new_password)

        super.onViewCreated(view, savedInstanceState)

        send.setOnClickListener { _ ->
/*            if (email.text.isNotEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                    try {
*/                       newPassword.text = getNewCredentials(email.text.toString())
/*                    } catch (e: IOException) {
                        Log.e("FORGOTPASS_ERR/ ", e.toString())
                    }
                }
            }
*/        }
    }

    private /*suspend*/ fun getNewCredentials(email: String): String {
        //return withContext(Dispatchers.IO) {
        //    requestObject.getNewCredentials(email)
        //}
        return "toto"
    }
}