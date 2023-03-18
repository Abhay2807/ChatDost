package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.userViewholder>()

{


    class userViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

         val txtName=itemView.findViewById<TextView>(R.id.txt_name)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewholder {

        return userViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_layout,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: userViewholder, position: Int) {

        val currentUser=userList[position]
        holder.txtName.text=currentUser.name

        holder.itemView.setOnClickListener {

            val intent= Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("UID",currentUser.uid)

            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
          return userList.size
    }

}