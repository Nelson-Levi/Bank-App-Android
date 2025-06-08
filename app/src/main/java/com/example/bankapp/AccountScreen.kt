package com.example.bankapp.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bankapp.*
import java.time.LocalDate
import com.example.bankapp.ui.theme.HomeImage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountScreen(
    account: Account,
    selectedRadioOption: String,
    setNewAccount: (Account) -> Unit) {
//    This is how you can keep the displayed values dynamic - using the Remember API.
    var currentAmount by remember { mutableStateOf(account.amount) }
    Column(
        Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Name: ${account.name}", style = JetchatTypography.bodyLarge)
        Text(text = "Amount: ${account.amount}", style = JetchatTypography.bodyLarge)
        Text(
            text = "Last Checked Date: ${account.lastCheckedDate}",
            style = JetchatTypography.bodyLarge
        )

//        OLD EXAMPLE: These were buttons I created as I was learning Kotlin and Jetpack Compose. I'm keeping them around as examples/reference.
//        Button(
//            onClick = {
//                account.withdrawal(10.0)
////                This is what dynamically updates the screen!
//                currentAmount = account.amount
//            }
//        ) {
//            Text("Withdraw $10")
//        }
//        Button(
//            onClick = {
//                account.deposit(10.0)
////                Again, going to do this so the screen dynamically updates:
//                currentAmount = account.amount
//            }
//        ) {
//            Text("Deposit $10")
//        }


//         Much like the buttons above, I'll define a mutable state variable to hold the current text in the input field.
//        'remember' keeps this value across recompositions so the screen will update every time it's changed.
        var depositText by remember { mutableStateOf("") }
//        Display a text input field. The value shown is 'currentText', and any change will update that value, thanks to 'remember'.
        OutlinedTextField(
            value = depositText, // This binds the input field's text to currentText
            onValueChange = {
                depositText = it
            }, // Updates 'currentText' to whatever the user types
            label = { Text("Deposit Amount") }, // Placeholder text :)
            singleLine = true // Limit input to just one line.
        )
        Button(
            onClick = {
                val depositDouble = depositText.toDouble()
                account.deposit(depositDouble)
                depositText = ""
//                Again, going to do this so the screen dynamically updates:
                currentAmount = account.amount
            }
        ) {
            Text("Submit Deposit")
        }

//        Repeating this for the Withdraw field.
        var withdrawalText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = withdrawalText,
            onValueChange = { withdrawalText = it },
            label = { Text("Withdrawal Amount") },
            singleLine = true
        )
        Button(
            onClick = {
                val withdrawalDouble = withdrawalText.toDouble()
                account.withdrawal(withdrawalDouble)
                withdrawalText = ""
//                Again, going to do this so the screen dynamically updates:
                currentAmount = account.amount
            }
        ) {
            Text("Submit Withdrawal")
        }

//        The following text boxes will be used to allow users to open a new account.
        var newAccountName by remember { mutableStateOf("") }
        OutlinedTextField(
            value = newAccountName,
            onValueChange = { newAccountName = it },
            label = { Text("New Account Name") },
            singleLine = true
        )
        var newBalance by remember { mutableStateOf("") }
        OutlinedTextField(
            value = newBalance,
            onValueChange = { newBalance = it },
            label = { Text("New Account Balance") },
            singleLine = true
        )
        Button(
            onClick = {
                var newAccount = when (selectedRadioOption) {
                    "Checking" -> CheckingAccount(
                        newAccountName,
                        newBalance.toDouble(),
                        LocalDate.now()
                    )

                    "Certificate" -> CertificateAccount(
                        newAccountName,
                        newBalance.toDouble(),
                        LocalDate.now()
                    )

                    else -> SavingsAccount(newAccountName, newBalance.toDouble(), LocalDate.now())
                }

                var displayableString = newAccount.getCSVString()
                Log.d("BankApp", displayableString)
                setNewAccount(newAccount)
            }
        ) {
            Text("Create New Account")
        }
    }
}