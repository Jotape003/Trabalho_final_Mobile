package com.example.edumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Evento
import com.google.firebase.firestore.FirebaseFirestore

class EventoViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val eventosCollection = db.collection("eventos")
    var isLoading by mutableStateOf(true)
        private set


    var eventos by mutableStateOf<List<Pair<String, Evento>>>(emptyList())
        private set

    var mensagem by mutableStateOf("")

    init {
        carregarEventos()
    }

    fun carregarEventos() {
        eventosCollection.addSnapshotListener { snapshots, error ->
            if (error != null || snapshots == null) {
                eventos = emptyList()
                mensagem = "Erro ao carregar eventos: ${error?.message}"
                return@addSnapshotListener
            }

            eventos = snapshots.map { doc ->
                doc.id to doc.toObject(Evento::class.java)
            }
            isLoading = false
        }

    }
}
