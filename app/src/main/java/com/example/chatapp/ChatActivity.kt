package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import org.w3c.dom.Text

class ChatActivity : AppCompatActivity() {


    private lateinit var chatRecycleview: RecyclerView
    private lateinit var chatBox:EditText
    private lateinit var sendBtn: ImageView
    private lateinit var messgAdapter: MessageAdapter
    private lateinit var messgList: ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference

    var senderRoom:String?=null
    var receiverRoom:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatRecycleview=findViewById(R.id.rvChatActivity)
        chatBox=findViewById(R.id.MessageBox)
        sendBtn=findViewById(R.id.imgview_send_btn)
        mDbRef=FirebaseDatabase.getInstance().getReference()


        val name=intent.getStringExtra("name")
        val receiver_uid=intent.getStringExtra("UID")
        supportActionBar?.title=name


        val sender_uid=FirebaseAuth.getInstance().currentUser?.uid
        senderRoom=receiver_uid+sender_uid
        receiverRoom=sender_uid+receiver_uid



        messgList=ArrayList()
        messgAdapter=MessageAdapter(this,messgList)


        chatRecycleview.layoutManager=LinearLayoutManager(this)
        chatRecycleview.adapter=messgAdapter


        //Logic to add data to the ChatActivity (Recycler View) (To display sent/Received messages)

        mDbRef.child("CHATS").child(senderRoom!!).child("Messages").
            addValueEventListener(object:ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    // snapshot give access to all values present in the database

                    messgList.clear()

                    for(x in snapshot.children){

                        val mssg=x.getValue(Message::class.java)
                        messgList.add(mssg!!)

                    }
                    messgAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })







        //Adding message to the database
        sendBtn.setOnClickListener{



            // 1) Read message written in the message Box

            val message_ChatBox=chatBox.text.toString()
            val messageObject=Message(message_ChatBox,sender_uid)

            // Creating a Node/Branch of Chats in our database

            mDbRef.child("CHATS").child(senderRoom!!).child("Messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("CHATS").child(receiverRoom!!).child("Messages").push()
                        .setValue(messageObject)
                }
            chatBox.setText("")

        }




    }



}