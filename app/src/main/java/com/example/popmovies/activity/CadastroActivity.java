package com.example.popmovies.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.popmovies.R;
import com.example.popmovies.helper.ConfiguracaoFirebase;
import com.example.popmovies.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private ProgressBar progressBar;

    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

        //Cadastrar usuario
        progressBar.setVisibility(View.GONE);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               String textoNome = campoNome.getText().toString();
               String textoEmail = campoEmail.getText().toString();
               String textoSenha = campoSenha.getText().toString();

               if(!textoNome.isEmpty()){
                   if(!textoEmail.isEmpty()){
                       if(!textoSenha.isEmpty()){

                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrar(usuario);

                       }else{
                           Toast.makeText(CadastroActivity.this, "Prencha a senha!", Toast.LENGTH_LONG).show();
                       }

                   }else{
                       Toast.makeText(CadastroActivity.this, "Prencha o Email!", Toast.LENGTH_LONG).show();
                   }

               }else{
                   Toast.makeText(CadastroActivity.this, "Prencha o nome!", Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    /*
    * método responsável por cadastrar usuário com e-mail e senha
    * e fazer validações ao fazer o cadastro
    */

    public void cadastrar(Usuario usuario){

        progressBar.setVisibility(View.GONE);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
            usuario.getEmail(),
            usuario.getSenha()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this,
                                    "Cadastro com sucesso",
                                    Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        }else {
                            progressBar.setVisibility(View.GONE);

                            String erroExcecao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Por favor, digite um e-mail válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Esta conta já foi cadastrada";
                            }catch (Exception e ){
                                erroExcecao = "Ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this,
                                    "erro:" + erroExcecao ,
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );

    }

    public void inicializarComponentes(){
        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoCadastrar = findViewById(R.id.buttonEntrar);
        progressBar = findViewById(R.id.progressLogin);

        campoNome.requestFocus();

    }


}
