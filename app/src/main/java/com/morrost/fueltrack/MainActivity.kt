package com.morrost.fueltrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity_morrost"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recordButton: Button = findViewById(R.id.submitNow)
        recordButton.setOnClickListener {
            showFuelEntryDialog()
        }
    }

    private fun showFuelEntryDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_fuel_entry, null)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Enter Fuel Details")
            .setPositiveButton("Save") { dialog, which ->
                val allFields = getAllFields(dialogView)


                // Validation
                if (!validation(allFields)) {
                    return@setPositiveButton
                }

                // If validation passes
                Log.i(
                    TAG,
                    "Fuel amount: ${allFields["fuelAmount"]}, Fuel cost: ${allFields["fuelCost"]}, Kilometers: ${allFields["kilometers"]}"
                )
                // Proceed with processing the data
            }
            .setNegativeButton("Cancel", null)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun getAllFields(dialogView: View): HashMap<String, String> {
        val fuelAmount = dialogView.findViewById<EditText>(R.id.fuelAmount).text.toString().trim()
        val fuelCost = dialogView.findViewById<EditText>(R.id.fuelCost).text.toString().trim()
        val kilometers = dialogView.findViewById<EditText>(R.id.kilometers).text.toString().trim()

        val hashMap = HashMap<String, String>()
        hashMap["fuelAmount"] = fuelAmount
        hashMap["fuelCost"] = fuelCost
        hashMap["kilometers"] = kilometers

        return hashMap
    }

    private fun validation(allFields: HashMap<String, String>): Boolean {
        for ((key, value ) in allFields) {
            if (value.isBlank() || !isValidPositiveNumber(value)) {
                Toast.makeText(this, "$key is not valid!", Toast.LENGTH_LONG).show()
                return false;
            }
        }
        return true
    }

    private fun isValidPositiveNumber(input: String): Boolean {
        val pattern = Regex("^\\d+(\\.\\d+)?$")
        return pattern.matches(input)
    }
}