package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context:Context,private var messageList:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sent_mssg=itemView.findViewById<TextView>(R.id.tv_sent_mssg)

    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var received_mssg=itemView.findViewById<TextView>(R.id.tv_receive_mssg)

    }

    val item_sent=1
    val item_receive=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1)
        {
            return SentViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.sent,
                    parent,
                    false
                )
            )
        }
        else
        {
            return ReceiveViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.receive,
                    parent,
                    false
                )
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessg=messageList[position]

        if(holder.javaClass == SentViewHolder::class.java)
        {

            val viewHolder=holder as SentViewHolder
            holder.sent_mssg.text=currentMessg.message

        }

        else
        {

            val viewHolder=holder as ReceiveViewHolder
            holder.received_mssg.text=currentMessg.message

        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {

        val currMessg=messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currMessg.senderId))
        {
            return item_sent
        }
        else
        {
            return item_receive
        }

    }


}