package com.example.chatapp

class Message {

    var message:String?=null
    var senderId: String?=null

    constructor(){}

    constructor(a:String?,b:String?)
    {
        this.message=a
        this.senderId=b
    }

}