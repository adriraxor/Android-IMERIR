package com.example.notesproject.Class

/**
 * Modèle de note
 */
class Note(id: Int, title: String, content: String): java.io.Serializable {
    var id: Int = 0
    var title: String = ""
    var content: String = ""

    init {
        this.id = id
        this.title = title
        this.content = content
    }
}