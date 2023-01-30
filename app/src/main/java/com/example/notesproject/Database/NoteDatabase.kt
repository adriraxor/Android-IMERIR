/**
 * @author Adrien FIGUERES
 */
package com.example.notesproject.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notesproject.Class.Note

const val DATABASE_NAME = "note.db"
const val DATABASE_VERSION = 1

class NoteDatabase(val context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    val database: SQLiteDatabase = this.writableDatabase

    //
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Note(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT, content TEXT);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    /**
     * Insertion des données dans la base
     */
    fun insertData(title: String, content: String) : Long{
        val values = ContentValues()


        values.put("title", title)
        values.put("content", content)

        return database.insert("Note", null, values)
    }

    /**
     * Mise à jour d'une note
     *
     *
     * Récupération d'un objet Note
     */
    fun updateData(noteModel: Note): Boolean {

        val values = ContentValues().apply {
            put("title", noteModel.title)
            put("content", noteModel.content)
        }

        val whereClause = "id = ?"
        val whereArgs   = arrayOf(noteModel.id.toString())

        val count = database.update("Note", values, whereClause, whereArgs)

        return count == 1
    }

    @SuppressLint("Range")
    fun fetchAllNotes(): ArrayList<Note>{

        val listNote: ArrayList<Note> = ArrayList()
        val cursor: Cursor = database.rawQuery("SELECT * FROM Note", null)

        while(cursor.moveToNext()){
            listNote.add(Note(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("content"))))
        }

        return listNote
    }

    /**
     * Suppression d'une note via un ID
     */
    fun deleteNoteById(id: Int): Int {
        if(id==0){
            return -1
        }

        return database.delete("Note", "id=?", arrayOf(id.toString()))
    }
}