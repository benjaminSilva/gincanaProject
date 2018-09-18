package com.bsoftwares.benjamin.ideia01

import android.util.Log
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object QuestionModel : Observable() {


    private var mValueDataListener : ValueEventListener? = null
    private var questionList: ArrayList<Question>? = ArrayList()

    private fun getDatabaseRef():DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("questions")
    }

    init {
        if (mValueDataListener != null){
            getDatabaseRef()?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener = null
        Log.i("CakeModel","Data init line 26")

        mValueDataListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                if(p0!=null){
                    Log.i("CakeModel","Deu ruim")
                }
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    Log.i("CakeModel","Data Updated Line 29")
                    val data: ArrayList<Question> = ArrayList()
                    if(dataSnapshot != null){
                        for (snapshot : DataSnapshot in dataSnapshot.children){
                            try {
                                data.add(Question(snapshot))
                            } catch (e:Exception){
                                e.printStackTrace()
                            }
                        }
                        questionList = data
                        Log.i("CakeModel","DataUpdated + " + questionList!!.size)
                        setChanged()
                        notifyObservers()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData():ArrayList<Question>?{
        return questionList
    }
}