package com.example.edumi.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.edumi.R
import com.example.edumi.models.Responsavel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> get() = _isUserLoggedIn

    init {
        // Inicializa o estado de login e observa futuras mudanças
        auth.addAuthStateListener { firebaseAuth ->
            _isUserLoggedIn.value = firebaseAuth.currentUser != null
        }
    }

    fun checkCurrentUserLoggedIn(): Boolean {
        return isUserLogged() // Usa a sua função existente no repositório
    }


    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        telefone: String,
        sexo: String,
        pais: String
    ): Boolean {
        return try {
            val res = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = res.user?.uid
            if (uid != null) {
                val user = hashMapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email,
                    "telefone" to telefone,
                    "sexo" to sexo,
                    "pais" to pais,
                    "created_at" to System.currentTimeMillis()
                )
                firestore.collection("users").document(uid).set(user).await()
            }
            true
        } catch (e: Exception) {
            Log.e("authRepository", "Error ao registrar $name: $e")
            false
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.e("authRepository", "Error ao realizar login: $e")
            false
        }
    }

    suspend fun resetPassword(email: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            Log.e("authRepository", "Error ao resetar o password: $e")
            false
        }
    }

    suspend fun getUserName(): String? {
        return try {
            val uid = auth.currentUser?.uid
            if (uid != null) {
                val snapshot = firestore.collection("users").document(uid).get().await()
                snapshot.getString("name")
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("authRepository", "Error ao resgatar o nome do usuário: $e")
            null
        }
    }

    suspend fun getUserInfos(): Responsavel {
        return try {
            val uid = auth.currentUser?.uid

            if (uid != null) {
                val snapshot = firestore.collection("users").document(uid).get().await()
                Responsavel(
                    id = uid,
                    name = snapshot.getString("name") ?: "",
                    email = snapshot.getString("email") ?: "",
                    telefone = snapshot.getString("telefone") ?: "",
                    sexo = snapshot.getString("sexo") ?: "",
                    pais = snapshot.getString("pais") ?: ""
                )
            } else {
                // UID é nulo, retorna um Responsavel vazio
                Responsavel("", "", "", "", "", "")
            }
        } catch (e: Exception) {
            Log.e("authRepository", "Erro ao resgatar informações do usuário: $e")
            // Em caso de erro, também retorna um Responsavel vazio
            Responsavel("", "", "", "", "", "")
        }
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // Token do cliente configurado no Firebase
            .requestEmail() // Solicita o email do usuário
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    suspend fun loginWithGoogle(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, "")
            val res = auth.signInWithCredential(credential).await()
            val user = res.user
            user?.let {
                val uid = it.uid
                val name = it.displayName ?: "Usuário"
                val email = it.email ?: ""

                val userRef = firestore.collection("users").document(uid)
                val snapshot = userRef.get().await()

                if (!snapshot.exists()) {
                    val userData = hashMapOf(
                        "uid" to uid,
                        "name" to name,
                        "email" to email,
                        "created_at" to System.currentTimeMillis()
                    )
                    userRef.set(userData).await()
                }
            }
            true
        } catch (e: Exception) {
            Log.e("authRepository", "Error no login com Google: $e")
            false
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLogged(): Boolean {
        return auth.currentUser != null
    }
}