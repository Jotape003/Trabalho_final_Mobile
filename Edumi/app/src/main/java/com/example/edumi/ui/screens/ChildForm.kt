package com.example.edumi.ui.screens

import EscolaViewModel
import FilhoViewModel
import TurmaViewModel
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.edumi.R
import com.example.edumi.models.Filho
import com.example.edumi.models.Escola

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildForm(
    navController: NavController,
    filhoViewModel: FilhoViewModel = viewModel(),
    escolaViewModel: EscolaViewModel = viewModel(),
    turmaViewModel: TurmaViewModel = viewModel()
) {
    val context = LocalContext.current

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    // Escola autocomplete
    val escolas by escolaViewModel.escolas.observeAsState(emptyList())
    var escolaSelecionada by remember { mutableStateOf<Escola?>(null) }
    var escolaInput by remember { mutableStateOf("") }
    var escolaExpanded by remember { mutableStateOf(false) }

    // Turma autocomplete
    val turmas by turmaViewModel.turmas.observeAsState(emptyList())
    var turmaSelecionada by remember { mutableStateOf<String?>(null) }
    var turmaInput by remember { mutableStateOf("") }
    var turmaExpanded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract = GetContent()) { uri: Uri? ->
        if (uri != null) {
            fotoUri = uri
        }
    }

    LaunchedEffect(Unit) {
        escolaViewModel.ouvirEscolas()
    }

    LaunchedEffect(escolaSelecionada?.id) {
        escolaSelecionada?.id?.let { idEscola ->
            turmaViewModel.ouvirTurmasPorEscola(idEscola)
            turmaSelecionada = null
            turmaInput = ""
        } ?: run {
            turmaViewModel.pararDeOuvirTurmas()
            turmaSelecionada = null
            turmaInput = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Adicionar vínculo",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        val bitmap = remember(fotoUri) {
            try {
                fotoUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }
                }
            } catch (e: Exception) {
                null
            }
        }

        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Foto do filho",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_default_avatar),
                contentDescription = "Foto padrão",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }

        TextButton(onClick = { launcher.launch("image/*") }) {
            Text("Escolher Foto")
        }

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nome") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = idade,
            onValueChange = { idade = it.filter { c -> c.isDigit() } },
            label = { Text("Idade") },
            leadingIcon = { Icon(Icons.Default.Cake, contentDescription = "Idade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        // Autocomplete Escola
        ExposedDropdownMenuBox(
            expanded = escolaExpanded,
            onExpandedChange = { escolaExpanded = !escolaExpanded }
        ) {
            OutlinedTextField(
                value = escolaInput,
                onValueChange = {
                    escolaInput = it
                    escolaExpanded = true
                },
                label = { Text("Escola") },
                leadingIcon = { Icon(Icons.Default.School, contentDescription = "Escola") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = escolaExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = escolaExpanded,
                onDismissRequest = { escolaExpanded = false }
            ) {
                escolas
                    .filter { it.nome.contains(escolaInput, ignoreCase = true) }
                    .forEach { escola ->
                        DropdownMenuItem(
                            text = { Text(escola.nome) },
                            onClick = {
                                escolaSelecionada = escola
                                escolaInput = escola.nome
                                escolaExpanded = false
                            }
                        )
                    }
            }
        }

        // Autocomplete Turma (aparece só se escola selecionada)
        if (escolaSelecionada != null) {
            ExposedDropdownMenuBox(
                expanded = turmaExpanded,
                onExpandedChange = { turmaExpanded = !turmaExpanded }
            ) {
                OutlinedTextField(
                    value = turmaInput,
                    onValueChange = {
                        turmaInput = it
                        turmaExpanded = true
                    },
                    label = { Text("Turma") },
                    leadingIcon = { Icon(Icons.Default.Group, contentDescription = "Turma") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = turmaExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = turmaExpanded,
                    onDismissRequest = { turmaExpanded = false }
                ) {
                    turmas
                        .filter { it.nome.contains(turmaInput, ignoreCase = true) }
                        .forEach { turma ->
                            DropdownMenuItem(
                                text = { Text(turma.nome) },
                                onClick = {
                                    turmaSelecionada = turma.nome
                                    turmaInput = turma.nome
                                    turmaExpanded = false
                                }
                            )
                        }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (
                    nome.isNotBlank() &&
                    idade.isNotBlank() &&
                    escolaSelecionada != null &&
                    turmaSelecionada != null
                ) {
                    val novoFilho = Filho(
                        name = nome,
                        idade = idade.toInt(),
                        idEscola = escolaSelecionada!!.id,
                        turma = turmaSelecionada!!,
                        foto = if (fotoUri == null) R.drawable.ic_default_avatar else 0,
                        idResponsavel = "99"
                    )
                    filhoViewModel.salvarFilho(novoFilho)
                    navController.navigate("home")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Adicionar")
        }
    }
}
