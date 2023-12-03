package com.example.myshop

import com.example.myshop.Data.User

object MySingleton {
    var user: User = User()

    fun setUser1(user1: User) {
        user = user1
    }

    fun getUser1(): User {
        return user
    }


}