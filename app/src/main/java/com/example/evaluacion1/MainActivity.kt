package com.example.evaluacion1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.sp
import android.widget.Button
import android.widget.TextView
import android.widget.EditText



abstract class Empleado (val sueldoBruto:Double){
    abstract fun calcularLiquido(): Double
}
class empleadoHonorarios(sueldoBruto:Double) : Empleado(sueldoBruto) {
    override fun calcularLiquido(): Double = sueldoBruto - (sueldoBruto * 0.13)
}
class empleadoRegular(sueldoBruto:Double) : Empleado(sueldoBruto) {
    override fun calcularLiquido(): Double = sueldoBruto - (sueldoBruto * 0.20)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            bienvenidoUI()
        }
    }
}

@Preview
@Composable
fun bienvenidoUI() {
    val contexto = LocalContext.current as ComponentActivity

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Button(
            onClick = { contexto.finish() },
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
        ) {
            Text("X Cerrar")
        }
        Text("Bienvenido!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )
        Text("Seleccione una opción para calcular el sueldo liquido a pagar.",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp),
        )
        Button(onClick = {
            val intent: Intent = Intent(contexto, EmpleadoHonorariosUIActivity::class.java)
            contexto.startActivity(intent)
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Empleado Honorarios")
        }
        Button(onClick = {
            val intent: Intent = Intent(contexto, EmpleadoRegularUIActivity::class.java)
            contexto.startActivity(intent)
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Empleado Regular")
        }
    }
}
class EmpleadoHonorariosUIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            empleadoHonorariosUI()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun empleadoHonorariosUI() {
    var honorario by remember { mutableStateOf( "" ) }
    var resultado by remember { mutableStateOf("") }
    val contexto = LocalContext.current as ComponentActivity

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Button(onClick = { contexto.finish() }) {
            Text("<- Volver")
        }
        Text("Cálculo de pago liquido",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )

        TextField(
            value = honorario,
            onValueChange = { honorario = it },
            label = { Text("Honorarios") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(onClick = {
            val honorarioValor = honorario.toIntOrNull() ?: 0
            val empleado = empleadoHonorarios(honorarioValor.toDouble())
            val honorarios = empleado.calcularLiquido()
            resultado = "Con la retención corresponde a un pago liquido de $honorarios"
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Calcular Pago")
        }
        Text(resultado,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
class EmpleadoRegularUIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        btnCalcular.setOnClickListener {
            val resultado = findViewById<TextView>(R.id.tvResultado)

            val sueldoBrutoEditText = findViewById<EditText>(R.id.etHonorarios)
            val sueldoBruto = sueldoBrutoEditText.text.toString().toDoubleOrNull() ?: 0.0

            val empleado = empleadoRegular(sueldoBruto)
            val sueldoLiquido = empleado.calcularLiquido()

            resultado.text = "El sueldo líquido es $sueldoLiquido"
        }
    }
    fun volverAtras(view: View) {
        finish()
    }
}