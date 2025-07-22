package com.example.edumi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Filho
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CRUDFilhoViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _filho = MutableLiveData<Filho?>()
    val filho: LiveData<Filho?> = _filho

    fun carregarFilho(filhoId: String) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("filhos")
            .document(filhoId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val filho = document.toObject(Filho::class.java)?.copy(id = document.id)
                    _filho.value = filho
                } else {
                    _filho.value = null
                }
            }
            .addOnFailureListener {
                _filho.value = null
            }
    }

    fun atualizarFilho(filho: Filho, callback: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return callback(false)

        val dadosAtualizados = mapOf(
            "name" to filho.name,
            "idade" to filho.idade,
            "idEscola" to filho.idEscola,
            "idTurma" to filho.idTurma
        )

        db.collection("users")
            .document(userId)
            .collection("filhos")
            .document(filho.id)
            .update(dadosAtualizados)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }


    fun deletarFilho(filhoId: String, callback: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return callback(false)

        db.collection("users")
            .document(userId)
            .collection("filhos")
            .document(filhoId)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}


//    var items = mutableStateOf<List<Item>>(listOf())
//        private set
//
//    init {
//        listenerToItems()
//    }
//
//    private fun listenerToItems(){
//        listenerRegistration = db.collection("items")
//            .addSnapshotListener { snapshot, exception ->
//                if (exception != null){
//                    return@addSnapshotListener
//                }
//                if (snapshot != null){
//                    val fetchedItems = snapshot.documents.mapNotNull { document ->
//                        document.toObject(Item::class.java)?.copy(id = document.id)
//                    }
//                    items.value = fetchedItems
//                }
//
//            }
//    }
//
//    fun addItem(item: Item){
//        db.collection("items").add(item)
//    }
//
//    fun deleteItem(itemId: String){
//        db.collection("items").document(itemId).delete()
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        listenerRegistration?.remove()
//    }
//
//    fun updateItem(item: Item){
//        db.collection("items").document(item.id).set(item).addOnSuccessListener {
//            Log.d("viewmodel", "Item atualizado com sucesso")
//        }.addOnFailureListener {
//            Log.d("viewmodel", "Item não atualizado com sucesso")
//        }
//    }