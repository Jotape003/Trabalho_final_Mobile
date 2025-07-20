import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.edumi.models.Turma
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class TurmaViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val colecao = db.collection("turmas")

    private val _turmas = MutableLiveData<List<Turma>>()
    val turmas: LiveData<List<Turma>> = _turmas

    private var listener: ListenerRegistration? = null

    fun ouvirTurmasPorEscola(idEscola: String) {
        listener?.remove()
        listener = colecao.whereEqualTo("idEscola", idEscola)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    _turmas.value = emptyList()
                    return@addSnapshotListener
                }
                val lista = snapshot.documents.mapNotNull { it.toObject(Turma::class.java) }
                _turmas.value = lista
            }
    }

    fun pararDeOuvirTurmas() {
        listener?.remove()
        listener = null
        _turmas.value = emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}
