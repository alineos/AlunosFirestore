package br.edu.ifsp.dmo.alunosfirestore.data.repository

import android.util.Log
import br.edu.ifsp.dmo.alunosfirestore.data.model.Aluno
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
class AlunoRepository {
    companion object{
        const val TAG = "DMO"
        const val COLLECTION = "alunos"
        const val ATTR_NAME = "nome"
        const val ATTR_YEAR = "nascimento"
        const val ATTR_ID = "prontuario"
    }
    private val database = Firebase.firestore

    fun findAll(callback: (List<Aluno>) -> Unit) {
        database.collection(COLLECTION)
            .orderBy(ATTR_NAME, Query.Direction.ASCENDING)
            .addSnapshotListener{ querySnapshot, exception ->
                if (exception != null){
                    Log.e(TAG, "Listen fail.")
                    callback(emptyList())
                    return@addSnapshotListener
                }
                if (querySnapshot != null){
                    val list = querySnapshot.toObjects(Aluno::class.java)
                    callback(list)
                } else {
                    callback(emptyList())
                }
            }
    }
    fun insert(aluno: Aluno, callback: (Boolean) -> Unit) {
        database.collection(COLLECTION)
            .add(aluno)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}


/*
        ANOTAÇÃO DO PROFESSOR:

    // callback é um argumento recebido na chamada do método
    // findAll(). Na chamada deve-se passar a função que implementa
    // a ação para tratar a lista. Observa-se que o callback
    // é um método que recebe uma List de Aluno e retorna
    // um Unit. A utilização desta técnica é necessária porque o
    // processamento do Firebase pe assíncrono, de forma que não
    // é possível retornar a lista diretamente.

 */