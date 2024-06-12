package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.multicalculator.android.ui.theme.MultiCalculatorTheme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.multicalculator.Calculator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiCalculatorTheme {
                CalcView()
            }
        }
    }
}

@Composable
fun CalcView() {
    val displayText = remember { mutableStateOf("0") }

    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Row {
            CalcDisplay(displayText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Column(modifier = Modifier.weight(3f)) {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(displayText, i, 3)
                }
                Row {
                    CalcNumericButton(0, displayText)
                    CalcEqualsButton(displayText)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                val operations = listOf("+", "-", "*", "/")
                operations.forEach { operation ->
                    CalcOperationButton(operation, displayText)
                }
            }
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        modifier = Modifier
            .height(50.dp)
            .padding(5.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        color = Color.White,
        fontSize = 24.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons
    Row(modifier = Modifier.padding(0.dp)) {
        for (i in startNum until endNum) {
            CalcNumericButton(i, display)
        }
    }
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>) {
    Button(
        onClick = {
            if (display.value == "0") {
                display.value = number.toString()
            } else {
                display.value += number.toString()
            }
        },
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = number.toString(),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 20.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>) {
    Button(
        onClick = {
            display.value += " $operation "
        },
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            text = operation,
            color = MaterialTheme.colorScheme.onTertiary,
            fontSize = 20.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>) {
    Button(
        onClick = { display.value = "0" },
        modifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "=",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MultiCalculatorTheme {
        CalcView()
    }
}
