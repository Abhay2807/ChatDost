package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList=ArrayList()
        adapter=UserAdapter(this,userList)

        mAuth=FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()

        userRecyclerView=findViewById(R.id.userRv)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=adapter


        mDbRef.child("USERS").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for(x in snapshot.children){

                    val crUser=x.getValue(User::class.java)


                    if(mAuth.currentUser?.uid != crUser?.uid){
                    userList.add(crUser!!)}

                }

                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        }
        )


    }

    override fun onCreateOptionsMenu(menup: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_logout,menup)
        return super.onCreateOptionsMenu(menup)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            // Write Logic for logout

            mAuth.signOut()
            val intent = Intent(this@MainActivity,Login::class.java)

            finish()
            startActivity(intent)

            return true
        }

        return true
    }

}