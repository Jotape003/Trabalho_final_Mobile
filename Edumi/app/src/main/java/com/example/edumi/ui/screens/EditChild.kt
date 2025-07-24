package com.example.edumi.ui.screens

import EscolaViewModel
import FilhoViewModel
import TurmaViewModel
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Person
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
import coil.compose.rememberAsyncImagePainter
import com.example.edumi.R
import com.example.edumi.models.Escola
import com.example.edumi.models.Filho
import com.example.edumi.models.Turma

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditChildScreen(
    navController: NavController,
    filhoId: String,
    filhoViewModel: FilhoViewModel = viewModel(),
    escolaViewModel: EscolaViewModel = viewModel(),
    turmaViewModel: TurmaViewModel = viewModel()
) {
    val context = LocalContext.current

    val filho by filhoViewModel.filho.observeAsState()
    val escolas by escolaViewModel.escolas.observeAsState(emptyList())
    val turmas by turmaViewModel.turmas.observeAsState(emptyList())

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    var escolaSelecionada by remember { mutableStateOf<Escola?>(null) }
    var escolaInput by remember { mutableStateOf("") }
    var escolaExpanded by remember { mutableStateOf(false) }

    var turmaSelecionada by remember { mutableStateOf<Turma?>(null) }
    var turmaInput by remember { mutableStateOf("") }
    var turmaExpanded by remember { mutableStateOf(false) }

    var escolaFoiSelecionadaManualmente by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract = GetContent()) { uri: Uri? ->
        if (uri != null) {
            fotoUri = uri
        }
    }

    val contexto = LocalContext.current

    val bitmap = remember(fotoUri) {
        fotoUri?.let { uri ->
            try {
                if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(contexto.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(contexto.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    LaunchedEffect(Unit) {
        escolaViewModel.ouvirEscolas()
    }

    LaunchedEffect(escolaSelecionada?.id) {
        escolaSelecionada?.id?.let { idEscola ->
            turmaViewModel.ouvirTurmasPorEscola(idEscola)
        } ?: turmaViewModel.pararDeOuvirTurmas()
    }

    LaunchedEffect(filhoId) {
        filhoViewModel.buscarFilhoPorId(filhoId)
    }

    LaunchedEffect(filho) {
        filho?.let {
            nome = it.name
            idade = it.idade.toString()

            escolaSelecionada = escolas.find { escola -> escola.id == it.idEscola }
            escolaInput = escolaSelecionada?.nome ?: ""

            turmaSelecionada = turmas.find { turma -> turma.id == it.idTurma }
            turmaInput = turmaSelecionada?.nome ?: ""
        }
    }

    LaunchedEffect(turmas, filho) {
        if (filho != null && turmaSelecionada == null) {
            turmaSelecionada = turmas.find { it.id == filho!!.idTurma }
            turmaInput = turmaSelecionada?.nome ?: ""
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (filho == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Editar vínculo",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Foto do filho",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable { launcher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    val painter = if (!filho?.imgUrl.isNullOrBlank()) {
                        rememberAsyncImagePainter(filho?.imgUrl)
                    } else {
                        painterResource(id = R.drawable.ic_default_avatar)
                    }
                    Image(
                        painter = painter,
                        contentDescription = "Foto do filho",
                        modifier = Modifier
                            .size(120.dp)
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

                ExposedDropdownMenuBox(
                    expanded = escolaExpanded,
                    onExpandedChange = { escolaExpanded = !escolaExpanded }
                ) {
                    OutlinedTextField(
                        value = escolaInput,
                        onValueChange = { escolaInput = it },
                        label = { Text("Escola") },
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .clickable { escolaExpanded = true }
                    )

                    DropdownMenu(
                        expanded = escolaExpanded,
                        onDismissRequest = { escolaExpanded = false }
                    ) {
                        escolas.forEach { escola ->
                            DropdownMenuItem(
                                text = { Text(escola.nome) },
                                onClick = {
                                    if (escolaSelecionada?.id != escola.id) {
                                        turmaSelecionada = null
                                        turmaInput = ""
                                    }

                                    escolaSelecionada = escola
                                    escolaInput = escola.nome
                                    escolaExpanded = false
                                    escolaFoiSelecionadaManualmente = true
                                }
                            )
                        }
                    }
                }

                val turmasFiltradas = turmas.filter { it.idEscola == escolaSelecionada?.id }

                ExposedDropdownMenuBox(
                    expanded = turmaExpanded,
                    onExpandedChange = { turmaExpanded = !turmaExpanded }
                ) {
                    OutlinedTextField(
                        value = turmaInput,
                        onValueChange = { turmaInput = it },
                        label = { Text("Turma") },
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .clickable { turmaExpanded = true }
                    )

                    DropdownMenu(
                        expanded = turmaExpanded,
                        onDismissRequest = { turmaExpanded = false }
                    ) {
                        turmasFiltradas.forEach { turma ->
                            DropdownMenuItem(
                                text = { Text(turma.nome) },
                                onClick = {
                                    turmaSelecionada = turma
                                    turmaInput = turma.nome
                                    turmaExpanded = false
                                }
                            )
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
                            val filhoAtualizado = filho!!.copy(
                                name = nome,
                                idade = idade.toInt(),
                                idEscola = escolaSelecionada!!.id,
                                idTurma = turmaSelecionada!!.id,
                            )
                            if (filhoAtualizado == filho && fotoUri == null) {
                                Toast.makeText(context, "Nenhuma alteração feita!", Toast.LENGTH_SHORT)
                                    .show()
                                navController.popBackStack()
                                return@Button
                            }
                            filhoViewModel.atualizarFilho(filhoAtualizado, fotoUri, context) { sucesso ->
                                if (sucesso) {
                                    Toast.makeText(context, "Vínculo atualizado!", Toast.LENGTH_SHORT)
                                        .show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Erro ao atualizar", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Salvar alterações")
                }

                OutlinedButton(
                    onClick = {
                        filhoViewModel.deletarFilho(filho!!.id) { sucesso ->
                            if (sucesso) {
                                Toast.makeText(context, "Vínculo deletado!", Toast.LENGTH_SHORT).show()
                                navController.navigate("home") {
                                    popUpTo(0) { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Excluir vínculo")
                }
            }
        }
    }
}
