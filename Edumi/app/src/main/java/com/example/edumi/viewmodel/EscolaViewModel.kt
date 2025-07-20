import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Escola
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class EscolaViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val colecao = db.collection("escolas")

    private val _escolas = MutableLiveData<List<Escola>>()
    val escolas: LiveData<List<Escola>> = _escolas

    private var listener: ListenerRegistration? = null

    fun ouvirEscolas() {
        listener = colecao.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                _escolas.value = emptyList()
                return@addSnapshotListener
            }

            val lista = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Escola::class.java)
            }
            _escolas.value = lista
        }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}
