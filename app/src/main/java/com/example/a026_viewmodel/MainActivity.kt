package com.example.a026_viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a026_viewmodel.Data.DataForm
import com.example.a026_viewmodel.Data.DataSource.jenis
import com.example.a026_viewmodel.ui.theme._026_ViewModelTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a026_viewmodel.Data.DataSource.status
import com.example.a026_viewmodel.ui.theme.CobaViewModel
import java.time.temporal.JulianFields

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _026_ViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLaypout()
                }
            }
        }
    }
}

@Composable
fun TampilLaypout(modifier: Modifier = Modifier){
    Card(modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ){
            Register()
            Judul()
            Spacer(modifier = Modifier.padding(10.dp))
            TampilForm()
        }
    }
}

@Composable
fun Register(){
    Row() {
        Image(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "",
            modifier = Modifier.size(40.dp))

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = "Register", fontSize = 20.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun Judul(){
    Text(text = "Create Your Account", fontSize = 20.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel: CobaViewModel = viewModel()){

    var textNama by remember { mutableStateOf("") }
    var textTlp by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }

    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState

    OutlinedTextField(value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nama Lengkap") },
        onValueChange = {
            textNama = it
        }
    )
    OutlinedTextField(value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telepon") },
        onValueChange = {
            textNama = it
        }
    )
    OutlinedTextField(value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email") },
        onValueChange = {
            textNama = it
        }
    )
    SelectJK(
        options= jenis.map {id-> context.resources.getString(id) },
        onSelectionChanged = { cobaViewModel.setJK(it) })

    SelectStatus(options= status.map {id-> context.resources.getString(id) },
        onSelectionChanged = { cobaViewModel.setST(it) }
    )
    Button(
        modifier= Modifier.fillMaxWidth(),
        onClick = {
            cobaViewModel.InsertData(textNama, textTlp, dataForm.sex, dataForm.status)
        }
    ) {
        Text(
            text= stringResource(R.string.submit),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(100.dp))
    TextHasil(
        namanya = cobaViewModel.namaUsr,
        teleponnya = cobaViewModel.noTlp,
        jenisnya = cobaViewModel.jenisKL,
        statusnya = cobaViewModel.status,
    )

}

@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }
    Text(text = "Jenis Kelamin: ")
    Row (modifier = Modifier.padding(16.dp)){
        options.forEach{item ->
            Row(
                modifier= Modifier.selectable(
                    selected= selectedValue == item,
                    onClick = {selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(selected= selectedValue == item,
                    onClick ={
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}

@Composable
fun SelectStatus(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}
){
    var selectedValue by rememberSaveable { mutableStateOf("") }
    Text(text = "Status: ")
    Row (modifier = Modifier.padding(16.dp)){
        options.forEach{item ->
            Row(
                modifier= Modifier.selectable(
                    selected= selectedValue == item,
                    onClick = {selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(selected= selectedValue == item,
                    onClick ={
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}

@Composable
fun TextHasil(namanya: String, teleponnya: String, jenisnya: String, statusnya: String){
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(text = "Nama : " + namanya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))
        Text(text = "Telepon : " + teleponnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))
        Text(text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _026_ViewModelTheme {
        TampilLaypout()
    }
}