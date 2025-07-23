package com.example.edumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Comunicado
import com.google.firebase.firestore.FirebaseFirestore

class ComunicadoViewModel : ViewModel() {

    private val _comunicados = mutableStateListOf<Comunicado>()
    val comunicados: List<Comunicado> get() = _comunicados
    var isLoading by mutableStateOf(true)
        private set

    init {
        escutarComunicados()
    }

    private fun escutarComunicados() {
        FirebaseFirestore.getInstance()
            .collection("comunicados")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                _comunicados.clear()
                for (document in snapshot.documents) {
                    val comunicado = document.toObject(Comunicado::class.java)
                    if (comunicado != null) {
                        _comunicados.add(comunicado)
                    }
                }
                isLoading = false
            }
    }
}
