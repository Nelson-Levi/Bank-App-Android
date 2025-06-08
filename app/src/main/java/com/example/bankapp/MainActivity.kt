package com.example.bankapp


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import com.example.bankapp.ui.AccountScreen
import com.example.bankapp.ui.theme.HomeImage


val JetchatTypography = Typography(
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    )
)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankAppUI()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun BankAppUI() {
        var selectedRadioOption by remember { mutableStateOf("Checking") }
        var displayedAccount by remember {
            mutableStateOf<Account>(
                CheckingAccount("Starter Checking Account", 1000.0, LocalDate.now().minusMonths(2))
            )
        }

        Surface(color = Color.White) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AccountScreen(
                    account = displayedAccount,
                    selectedRadioOption = selectedRadioOption,
                    setNewAccount = { displayedAccount = it }
                )
                RadioButtonAccountType(
                    selectedRadioOption = selectedRadioOption,
                    onOptionSelected = { selectedRadioOption = it }
                )
                HomeImage()
            }
        }
    }
}

