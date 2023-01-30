/**
 * @author Adrien FIGUERES
 */
package com.example.notesproject.Class

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.notesproject.R
import com.example.notesproject.SecondActivity

class RecyclerViewAdapter(val context: Context, val items: ArrayList<Note>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    /**
     * ViewHolder class
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title:  TextView  = itemView.findViewById(R.id.tvItemTitleData)
        val tv_content: TextView = itemView.findViewById(R.id.tvItemContentData)

        fun bindData(item: Note){
            tv_title.text = item.title
            tv_content.text = item.content

            //--- Sélection d'un item dans le recycler view ---//

            itemView.setOnClickListener(View.OnClickListener {
                println(item.title)

                val intent: Intent = Intent(context, SecondActivity::class.java)

                intent.putExtra("note", item)   //--- On passe la note sélectionné (objet note sérializé ---//

                context.startActivity(intent)
            })
        }
    }

    /**
     * Affichage graphique de la vue
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_adapter, parent, false))
    }

    /**
     * Nb notes
     */
    override fun getItemCount(): Int {
        return items.count()
    }

    /**
     * ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }
}