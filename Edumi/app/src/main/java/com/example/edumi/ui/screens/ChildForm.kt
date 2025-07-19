package com.example.edumi.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.edumi.R
import com.example.edumi.models.Filho

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildForm(
    navController: NavController,
    onChildAdded: (Filho) -> Unit
) {
    val context = LocalContext.current

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var escola by remember { mutableStateOf("") }
    var turma by remember { mutableStateOf("") }

    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = GetContent()) { uri: Uri? ->
        if (uri != null) {
            fotoUri = uri
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
            modifier = Modifier
                .padding(horizontal = 24.dp)
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

        OutlinedTextField(
            value = escola,
            onValueChange = { escola = it },
            label = { Text("Escola") },
            leadingIcon = { Icon(Icons.Default.School, contentDescription = "Escola") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = turma,
            onValueChange = { turma = it },
            label = { Text("Turma") },
            leadingIcon = { Icon(Icons.Default.Group, contentDescription = "Turma") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nome.isNotBlank() && idade.isNotBlank() && escola.isNotBlank() && turma.isNotBlank()) {
                    val novoFilho = Filho(
                        id = (0..9999).random(),
                        name = nome,
                        idade = idade.toInt(),
                        escola = escola,
                        turma = turma,
                        foto = if (fotoUri == null) R.drawable.ic_default_avatar else 0
                    )
                    onChildAdded(novoFilho)
                    navController.popBackStack()
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
