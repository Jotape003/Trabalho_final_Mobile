package com.example.edumi.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edumi.data.AuthRepository
import com.example.edumi.models.Responsavel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch


/**
 * ViewModel para gerenciar a autenticação do usuário e comunicação com o AuthRepository.
 * A ViewModel atua como intermediária entre a UI e o repositório, garantindo que as operações assíncronas sejam executadas de forma segura.
 */
class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    // Variáveis de callback para retorno do resultado do login e registro
    var currentUserInfo = mutableStateOf<Responsavel?>(null)
        private set
    var loginResult: ((Boolean) -> Unit)? = null
    var registerResult: ((Boolean) -> Unit)? = null
    val isUserLoggedIn: LiveData<Boolean> = repository.isUserLoggedIn

    var userVersion = mutableStateOf(false)
        private set

    fun notifyUserChanged() {
        userVersion.value = !userVersion.value
    }
    /**
     * Método para registrar um novo usuário utilizando email e senha.
     * Após a criação do usuário no Firebase Authentication, seus dados são armazenados no Firestore.
     *
     * @param email Email do usuário
     * @param password Senha do usuário
     * @param name Nome do usuário
     * @param onResult Callback que retorna `true` se o registro for bem-sucedido, `false` caso contrário.
     */
    fun register(email: String, password: String, name: String, telefone : String, sexo : String, pais : String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.registerUser(email, password, name, telefone, sexo, pais)
            onResult(success) // Retorna o resultado para a UI
        }
    }

    /**
     * Método para realizar login com email e senha.
     * Executa a operação dentro de uma corrotina para evitar bloqueio da UI.
     *
     * @param email Email do usuário
     * @param password Senha do usuário
     * @param onResult Callback para retornar o resultado da operação (true ou false)
     */
    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.loginUser(email, password)
            onResult(success) // Notifica a UI com o resultado
        }
    }

    /**
     * Método para solicitar redefinição de senha.
     * O Firebase enviará um email com um link para redefinir a senha do usuário.
     *
     * @param email Email do usuário
     * @param onResult Callback para indicar sucesso ou falha na solicitação
     */
    fun resetPassword(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.resetPassword(email)
            onResult(success)
        }
    }

    /**
     * Método para buscar o nome do usuário atualmente autenticado.
     * Os dados são recuperados do Firestore e passados para a interface via callback.
     *
     * @param onResult Callback que recebe o nome do usuário ou null se houver erro
     */
    fun getUserName(onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val name = repository.getUserName()
            onResult(name)
        }
    }

    fun getUserInfos(onResult: (Responsavel) -> Unit) {
        viewModelScope.launch {
            val resp = repository.getUserInfos()
            currentUserInfo.value = resp
            userVersion.value = !userVersion.value // <-- Notifica que houve mudança
            onResult(resp)
        }
    }
    /**
     * Método para realizar login com uma conta Google.
     * Utiliza o ID Token recebido após a autenticação com o Google Sign-In.
     *
     * @param idToken Token do usuário autenticado pelo Google
     * @param onResult Callback que retorna `true` se o login for bem-sucedido, `false` caso contrário.
     */
    fun loginWithGoogle(idToken: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.loginWithGoogle(idToken)
            onResult(success)
        }
    }

    /**
     * Método para obter o cliente de login do Google.
     * Esse cliente é usado para iniciar o fluxo de autenticação com a conta Google.
     *
     * @param context Contexto da aplicação necessário para inicialização
     * @return Retorna um objeto `GoogleSignInClient` configurado.
     */
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        return repository.getGoogleSignInClient(context)
    }

    fun fetchUserInfos() {
        viewModelScope.launch {
            val resp = repository.getUserInfos()
            currentUserInfo.value = resp
        }
    }

    /**
     * Método para realizar logout do usuário.
     * Essa função desloga o usuário do Firebase e o remove da sessão ativa.
     */
    fun logout() {
        repository.logout()
        currentUserInfo.value = null  // Limpa dados do usuário atual
    }


}