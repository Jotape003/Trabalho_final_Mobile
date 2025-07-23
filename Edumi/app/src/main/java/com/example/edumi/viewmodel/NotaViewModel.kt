package com.example.edumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Nota
import com.google.firebase.firestore.FirebaseFirestore

class NotaViewModel : ViewModel() {

    private val _notas = mutableStateListOf<Nota>()
    val notas: List<Nota> get() = _notas

    var isLoading by mutableStateOf(true)
        private set

    init {
        escutarNotas()
    }

    private fun escutarNotas() {
        FirebaseFirestore.getInstance()
            .collection("notas")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                _notas.clear()
                for (document in snapshot.documents) {
                    val nota = document.toObject(Nota::class.java)
                    if (nota != null) {
                        _notas.add(nota)
                    }
                }

                isLoading = false
            }
    }
}

