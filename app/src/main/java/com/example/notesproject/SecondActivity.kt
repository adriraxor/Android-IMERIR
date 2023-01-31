/**
 * @author Adrien FIGUERES
 */
package com.example.notesproject

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.notesproject.Class.Note
import com.example.notesproject.Database.NoteDatabase

class SecondActivity : AppCompatActivity() {

    lateinit var imPhoto: ImageView
    var inAddingMode: Boolean = false
    lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //--- Layouts elements ---//

        val title : EditText = findViewById(R.id.tvTitleParse)
        val content: EditText = findViewById(R.id.saContentNote)
        val btnConfirm : Button = findViewById(R.id.btnAddNoteToDb)
        val btnShare: Button = findViewById(R.id.btnShare)

        //--- database ---//

        val db : NoteDatabase = NoteDatabase(this)

        //--- Récupération du titre de la MainActivity ---//

        inAddingMode = intent.getStringExtra("inAddingMode").toBoolean()

        //--- Gestion du type de transaction (Ajout ou Modification) ---//

        if(inAddingMode){
            title.setText(intent.getStringExtra("title").toString())
        } else {
            note = intent.getSerializableExtra("note") as Note          //--- Récupération de l'objet Note

            title.setText(note.title)
            content.setText(note.content)
        }

        //--- Bouton d'ajout dans la base de données SQLite d'une note ---//

        btnConfirm.setOnClickListener(View.OnClickListener {

            //--- insertion des données ---//

            if(inAddingMode){
                db.insertData(title.text.toString(), content.text.toString())
                Toast.makeText(this, "La note a été enregistré !", Toast.LENGTH_SHORT).show()
            } else {

                note.title = title.text.toString()
                note.content = content.text.toString()

                db.updateData(note)

                Toast.makeText(this, "La note a été modifié !", Toast.LENGTH_SHORT).show()
            }

            //--- start activity ---//

            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        //--- Bouton de partage de la note ---//

        btnShare.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent()

            intent.type = "text/plain";
            intent.action = Intent.ACTION_SEND;
            intent.putExtra(Intent.EXTRA_TEXT, "" + title.text + "\n\n"+ content.text);

            startActivity(intent)
        })
    }
}