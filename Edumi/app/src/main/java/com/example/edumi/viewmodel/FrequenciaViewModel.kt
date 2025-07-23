package com.example.edumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Frequencia
import com.google.firebase.firestore.FirebaseFirestore

class FrequenciaViewModel : ViewModel() {

    private val _frequencias = mutableStateListOf<Frequencia>()
    val frequencias: List<Frequencia> get() = _frequencias
    var isLoading by mutableStateOf(true)
        private set


    init {
        escutarFrequencias()
    }

    private fun escutarFrequencias() {
        FirebaseFirestore.getInstance()
            .collection("frequencias")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                _frequencias.clear()
                for (document in snapshot.documents) {
                    val frequencia = document.toObject(Frequencia::class.java)
                    if (frequencia != null) {
                        _frequencias.add(frequencia)
                    }
                }
                isLoading = false
            }
    }
}
