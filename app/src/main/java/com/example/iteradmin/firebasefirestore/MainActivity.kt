package com.example.iteradmin.firebasefirestore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var database:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database= FirebaseFirestore.getInstance()

        val name=findViewById<EditText>(R.id.name)
        val branch=findViewById<EditText>(R.id.branch)
        val city=findViewById<EditText>(R.id.city)

        val save=findViewById<Button>(R.id.save)
        val load=findViewById<Button>(R.id.load)

        save.setOnClickListener{
            val n:String=name.text.toString()
            val b:String=branch.text.toString()
            val c:String=city.text.toString()
            val map= hashMapOf(
                    "name" to n,
                    "branch" to b,
                    "city" to c
            )
            database.collection("users")
                    .add(map)
                    .addOnSuccessListener {documentReference ->
                        Toast.makeText(this,"added to firestore",Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
                    }
        }
        load.setOnClickListener{
            database.collection("users").get()
                    .addOnSuccessListener { querySnapshot ->
                        for(d in querySnapshot.documents){
                            val name:String=d.data?.get("name").toString()
                            Toast.makeText(this,"name",Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                    }
        }
    }
}
