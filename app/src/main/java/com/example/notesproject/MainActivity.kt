/**
 * @author Adrien FIGUERES
 */
package com.example.notesproject

/**
 * @author Adrien FIGUERES
 */
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.Class.Note
import com.example.notesproject.Class.RecyclerViewAdapter
import com.example.notesproject.Database.NoteDatabase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //--- Layouts elements ---//

        val btnAddNote: Button = findViewById(R.id.btnAddNote)
        val titleNote: EditText = findViewById(R.id.saNoteTitle)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //--- vars --- //

        var notes = ArrayList<Note>()
        val db: NoteDatabase = NoteDatabase(this)

        //--- récupération de toutes les notes issus de la base de données --//

        notes = db.fetchAllNotes()

        //--- adapter du recyclerview --//

        val adapter = RecyclerViewAdapter(this, notes)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter

        //--- Swipe vers la droite pour supprimer un item (=== une note) --- //

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition

                try{
                    notes.removeAt(position)
                    db.deleteNoteById(notes[position].id)

                } catch (e: IndexOutOfBoundsException) {
                    println("Plus d'élements [Out of bounds] " + position)
                }

                adapter.notifyDataSetChanged()
            }
        }

        //--- ajout de la gestion du swipe au recycler view ---//

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //--- Listeners sur le bouton d'ajout d'une note --- //

        btnAddNote.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, SecondActivity::class.java)
            println(titleNote.text.toString())
            intent.putExtra("title", "" + titleNote.text)
            intent.putExtra("inAddingMode", "true")
            startActivity(intent)
        })
    }
}