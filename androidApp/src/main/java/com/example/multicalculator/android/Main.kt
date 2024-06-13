package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multicalculator.android.ui.theme.MultiCalculatorTheme

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
    val leftNumber = rememberSaveable { mutableStateOf(0) }
    val rightNumber = rememberSaveable { mutableStateOf(0) }
    val operation = rememberSaveable { mutableStateOf("") }
    val complete = rememberSaveable { mutableStateOf(false) }
    val displayText = rememberSaveable { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = displayText.value,
            modifier = Modifier
                .height(50.dp)
                .padding(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Column(modifier = Modifier.weight(3f)) {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(i, displayText) { number ->
                        if (complete.value) {
                            displayText.value = number.toString()
                            complete.value = false
                        } else {
                            displayText.value += number.toString()
                        }
                    }
                }
                Row {
                    CalcNumericButton(0, displayText) { number ->
                        if (complete.value) {
                            displayText.value = number.toString()
                            complete.value = false
                        } else {
                            displayText.value += number.toString()
                        }
                    }
                    CalcOperationButton("/", displayText, operation, complete, leftNumber, rightNumber)
                    CalcEqualsButton(displayText, operation, complete, leftNumber, rightNumber)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                listOf("+", "-", "*").forEach { op ->
                    CalcOperationButton(op, displayText, operation, complete, leftNumber, rightNumber)
                }
            }
        }
    }
}

@Composable
fun CalcRow(startNum: Int, display: MutableState<String>, onPress: (Int) -> Unit) {
    Row(modifier = Modifier.padding(0.dp)) {
        for (i in startNum until startNum + 3) {
            CalcNumericButton(i, display, onPress)
        }
    }
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>, onPress: (Int) -> Unit) {
    Button(
        onClick = { onPress(number) },
        modifier = Modifier
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = number.toString(),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 30.sp
        )
    }
}

@Composable
fun CalcOperationButton(
    operation: String,
    displayText: MutableState<String>,
    currentOperation: MutableState<String>,
    complete: MutableState<Boolean>,
    leftNumber: MutableState<Int>,
    rightNumber: MutableState<Int>
) {
    Button(
        onClick = {
            displayText.value += " $operation "
            currentOperation.value = operation
            rightNumber.value = leftNumber.value
            leftNumber.value = 0
            complete.value = false
        },
        modifier = Modifier
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            text = operation,
            color = MaterialTheme.colorScheme.onTertiary,
            fontSize = 30.sp
        )
    }
}

@Composable
fun CalcEqualsButton(
    displayText: MutableState<String>,
    operation: MutableState<String>,
    complete: MutableState<Boolean>,
    leftNumber: MutableState<Int>,
    rightNumber: MutableState<Int>
) {
    Button(
        onClick = {
            val result = when (operation.value) {
                "+" -> leftNumber.value + rightNumber.value
                "-" -> leftNumber.value - rightNumber.value
                "*" -> leftNumber.value * rightNumber.value
                "/" -> if (rightNumber.value != 0) leftNumber.value / rightNumber.value else "Error"
                else -> "Invalid operation"
            }
            displayText.value = result.toString()
            complete.value = true
        },
        modifier = Modifier
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "=",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 30.sp
        )
    }
}