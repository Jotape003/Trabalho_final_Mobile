import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Filho
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class FilhoViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> = _saveStatus

    private val _filho = MutableLiveData<Filho?>()
    val filho: LiveData<Filho?> = _filho

    private val _listaFilhos = MutableLiveData<List<Filho>>()
    val listaFilhos: LiveData<List<Filho>> get() = _listaFilhos

    private var listenerRegistration: ListenerRegistration? = null

    fun salvarFilho(filho: Filho, fotoUri: Uri?, context: Context) {
        val collectionRef = db.collection("filhos")
        val docRef = if (filho.id.isNotEmpty()) {
            collectionRef.document(filho.id)
        } else {
            collectionRef.document()
        }

        val filhoId = if (filho.id.isEmpty()) docRef.id else filho.id

        if (fotoUri != null) {
            uploadImage(context, fotoUri,
                onSuccess = { imageUrl ->
                    val filhoComFoto = filho.copy(id = filhoId, imgUrl = imageUrl)

                    docRef.set(filhoComFoto)
                        .addOnSuccessListener { _saveStatus.value = true }
                        .addOnFailureListener { _saveStatus.value = false }
                },
                onFailure = {
                    _saveStatus.value = false
                }
            )
        } else {
            val filhoParaSalvar = if (filho.id.isEmpty()) {
                filho.copy(id = filhoId)
            } else {
                filho
            }

            docRef.set(filhoParaSalvar)
                .addOnSuccessListener { _saveStatus.value = true }
                .addOnFailureListener { _saveStatus.value = false }
        }
    }


    fun buscarFilhoPorId(id: String) {
        db.collection("filhos").document(id)
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    _filho.value = doc.toObject(Filho::class.java)
                } else {
                    _filho.value = null
                }
            }
            .addOnFailureListener {
                _filho.value = null
            }
    }

    fun ouvirFilhosDoResponsavel(idResponsavel: String) {
        listenerRegistration?.remove()
        listenerRegistration = db.collection("filhos")
            .whereEqualTo("idResponsavel", idResponsavel)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    _listaFilhos.value = emptyList()
                    return@addSnapshotListener
                }

                val filhos = snapshots?.documents?.mapNotNull { it.toObject(Filho::class.java) }
                _listaFilhos.value = filhos ?: emptyList()
            }
    }

    fun atualizarFilho(filho: Filho, callback: (Boolean) -> Unit) {
        val dadosAtualizados = mapOf(
            "name" to filho.name,
            "idade" to filho.idade,
            "idEscola" to filho.idEscola,
            "idTurma" to filho.idTurma
        )

        db.collection("filhos")
            .document(filho.id)
            .update(dadosAtualizados)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }


    fun deletarFilho(filhoId: String, callback: (Boolean) -> Unit) {
        db.collection("filhos")
            .document(filhoId)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}
