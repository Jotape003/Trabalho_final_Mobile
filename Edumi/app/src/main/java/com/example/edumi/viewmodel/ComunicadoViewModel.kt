package com.example.edumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Comunicado
import com.example.edumi.models.Filho
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ComunicadoViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    var comunicados by mutableStateOf<List<Comunicado>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun escutarComunicados(listaDeFilhos: List<Filho>) {
        listener?.remove()

        if (listaDeFilhos.isEmpty()) {
            comunicados = emptyList()
            return
        }

        val idsDosFilhos = listaDeFilhos.map { it.id }

        isLoading = true

        listener = db.collection("comunicados")
            .whereIn("idFilho", idsDosFilhos)
            .addSnapshotListener { snapshot, error ->
                isLoading = false
                if (error != null) {
                    return@addSnapshotListener
                }

                comunicados = snapshot?.toObjects(Comunicado::class.java) ?: emptyList()
            }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}