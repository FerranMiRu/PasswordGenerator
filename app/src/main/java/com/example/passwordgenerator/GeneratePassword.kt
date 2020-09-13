package com.example.passwordgenerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import java.security.SecureRandom

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GeneratePassword : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.generate_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_generate).setOnClickListener {
            val passwordTextview: TextView = view.findViewById(R.id.textview_password)
            val userLengthInput : TextInputLayout = view.findViewById(R.id.password_length_input)
            val length = if (userLengthInput.editText?.text.toString() != "") userLengthInput.editText?.text.toString().toInt() else 8
            passwordTextview.text = generatePassword(length)
        }
    }

    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        val random = SecureRandom()

        return random.nextInt(end - start + 1) + start
    }

    private fun generatePassword(length: Int = 8) : CharSequence? {
        if (length !in 4..32) { return("Incorrect length") }
        val password = CharArray(length)

        val minus = rand(97, 122)
        val majus = rand(65, 90)
        val num = rand(49, 57)
        val sym_possible_ranges = (listOf(33..47, 58..64, 91..96, 123..126)).shuffled().first()
        val sym = sym_possible_ranges.shuffled().first()

        val basic_positions = (0..(length-1)).shuffled()
        password[basic_positions[0]] = minus.toChar()
        password[basic_positions[1]] = majus.toChar()
        password[basic_positions[2]] = num.toChar()
        password[basic_positions[3]] = sym.toChar()

        for (i in 0 until length) {
            if (password[i] == '\u0000')
                password[i] = rand(33, 126).toChar()
        }

        return String(password)
    }

}