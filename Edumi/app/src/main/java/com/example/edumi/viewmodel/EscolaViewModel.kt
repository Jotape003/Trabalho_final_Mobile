import android.util.Log
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

    fun getNomeEscolaPorId(id: String, onResult: (String) -> Unit) {
        colecao.document(id).get()
            .addOnSuccessListener { doc ->
                val escola = doc.toObject(Escola::class.java)
                onResult(escola?.nome ?: "Escola desconhecida")
            }
            .addOnFailureListener {
                onResult("Erro ao buscar escola")
            }
    }


    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}
