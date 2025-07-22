package com.example.edumi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Tarefas
import com.google.firebase.firestore.FirebaseFirestore

class TarefaViewModel : ViewModel() {

    private val _tarefas = mutableStateListOf<Tarefas>()
    val tarefas: List<Tarefas> get() = _tarefas

    init {
        escutarTarefas()
    }

    private fun escutarTarefas() {
        FirebaseFirestore.getInstance()
            .collection("tarefas")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                _tarefas.clear()
                for (document in snapshot.documents) {
                    val tarefa = document.toObject(Tarefas::class.java)
                    if (tarefa != null) {
                        _tarefas.add(tarefa)
                    }
                }
            }
    }
}
