package br.com.apkdoandroid.exemplofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.apkdoandroid.exemplofirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val autenticacao by lazy { FirebaseAuth.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonExecutar.setOnClickListener { cadastrodeUsuario() }
        binding.buttonLogin.setOnClickListener { login() }
        binding.buttonLogout.setOnClickListener { deslogar() }
    }

    override fun onStart() {
        super.onStart()
        verificarUsuarioLogado()
    }

    private  fun verificarUsuarioLogado(){
        var usuario = autenticacao.currentUser

        if(usuario != null){
            exibirMensagem("Usuario logado")
        }else{
            exibirMensagem("Não tem usuário logado")
        }
    }

    private fun cadastrodeUsuario(){
        val email = "elanilsonpp@hotmail.com"
        val senha = "sddsfdsf54@#$@#"


        autenticacao.createUserWithEmailAndPassword(email,senha)
            .addOnSuccessListener {
                val email = it.user?.email
                val id = it.user?.uid
                binding.textViewResultado.text = "Emil: $email \n id: $id"
                exibirMensagem("Sucesso ao cadastrar usuário")
            }
            .addOnFailureListener {
                binding.textViewResultado.text = "Erro: ${it.message}"
                exibirMensagem("Erro ${it.message}") }
    }

    private fun exibirMensagem(s: String) {
        Toast.makeText(this@MainActivity,s,Toast.LENGTH_SHORT).show()
    }

    private fun deslogar(){
        autenticacao.signOut()

        if(autenticacao.currentUser != null){
            exibirMensagem("Usuário ainda logado")
        }else{
            exibirMensagem("Deslogado")
        }

    }

    private fun login(){
        val email = "elanilsonpp@hotmail.com"
        val senha = "sddsfdsf54@#$@#"

        autenticacao.signInWithEmailAndPassword(email,senha)
            .addOnSuccessListener { exibirMensagem("Login com sucesso") }
            .addOnFailureListener { exibirMensagem("Login erro: ${it.message}") }
    }
}