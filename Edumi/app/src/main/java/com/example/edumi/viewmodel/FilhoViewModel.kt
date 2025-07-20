import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Filho
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class FilhoViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> = _saveStatus

    private val _filho = MutableLiveData<Filho?>()
    val filho: LiveData<Filho?> = _filho

    private val _listaFilhos = MutableLiveData<List<Filho>>()
    val listaFilhos: LiveData<List<Filho>> = _listaFilhos

    private var listenerRegistration: ListenerRegistration? = null

    fun salvarFilho(filho: Filho) {
        val collectionRef = db.collection("filhos")
        val docRef = if (filho.id.isNotEmpty()) {
            collectionRef.document(filho.id)
        } else {
            collectionRef.document()
        }

        val filhoParaSalvar = if (filho.id.isEmpty()) {
            filho.copy(id = docRef.id)
        } else {
            filho
        }

        docRef.set(filhoParaSalvar)
            .addOnSuccessListener { _saveStatus.value = true }
            .addOnFailureListener { _saveStatus.value = false }
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

    fun ouvirListaFilhos() {
        listenerRegistration = db.collection("filhos")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    _listaFilhos.value = emptyList()
                    return@addSnapshotListener
                }

                val filhos = snapshots?.documents?.mapNotNull { it.toObject(Filho::class.java) }
                _listaFilhos.value = filhos ?: emptyList()
            }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}
