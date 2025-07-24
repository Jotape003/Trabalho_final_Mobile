package com.example.edumi.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Evento
import com.example.edumi.models.Filho
import com.example.edumi.notifications.agendarNotificacaoEvento
import com.example.edumi.notifications.cancelarNotificacaoEvento
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EventoViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    var isLoading by mutableStateOf(true)
        private set

    var eventos by mutableStateOf<List<Pair<String, Evento>>>(emptyList())
        private set

    fun carregarEventos(listaDeFilhos: List<Filho>) {
        listener?.remove()

        if (listaDeFilhos.isEmpty()) {
            eventos = emptyList()
            isLoading = false
            return
        }

        val idsDosFilhos = listaDeFilhos.map { it.id }

        isLoading = true

        listener = db.collection("eventos")
            .whereIn("idFilho", idsDosFilhos)
            .addSnapshotListener { snapshot, error ->
                isLoading = false
                if (error != null) {
                    return@addSnapshotListener
                }

                val novosEventos = snapshot?.map { doc ->
                    doc.toObject(Evento::class.java).copy(idFilho = doc.id)
                } ?: emptyList()

                eventos = novosEventos.map { it.idFilho to it }
            }
    }

    fun agendamentoDeNotificacoes(habilitar: Boolean, context: Context) {
        Log.d("NotificationDebug", "Função 'gerenciar' chamada. Habilitar: $habilitar. Número de eventos na lista: ${eventos.size}")

        if (habilitar) {
            eventos.forEach { (_, evento) ->
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val eventoDate = LocalDate.parse(evento.data, formatter)
                val eventoTime = LocalTime.parse(evento.horaInicio)
                val eventoDateTime = LocalDateTime.of(eventoDate, eventoTime)

                if (eventoDateTime.isAfter(LocalDateTime.now())) {
                    agendarNotificacaoEvento(context, evento)
                }
            }
        } else {
            eventos.forEach { (_, evento) ->
                cancelarNotificacaoEvento(context, evento)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}