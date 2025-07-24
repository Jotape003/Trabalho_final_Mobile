package com.example.edumi.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Comunicado
import com.example.edumi.models.Filho
import com.example.edumi.notifications.agendarNotificacaoComunicado
import com.example.edumi.notifications.cancelarNotificacaoComunicado
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

    fun agendamentoDeComunicados(habilitar: Boolean, context: Context) {
        if (habilitar) {
            comunicados.forEach { comunicado ->
                agendarNotificacaoComunicado(context, comunicado)
            }
        } else {
            comunicados.forEach { comunicado ->
                cancelarNotificacaoComunicado(context, comunicado)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}