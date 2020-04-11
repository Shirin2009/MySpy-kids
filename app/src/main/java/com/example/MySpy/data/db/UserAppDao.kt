package com.example.MySpy.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.MySpy.model.db.UserApp
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserAppDao {

    @Query("SELECT * FROM userapp")
    fun getAll(): List<UserApp>

    @Query("SELECT * FROM userapp")
    fun getAllRx(): Flowable<List<UserApp>>

    @Insert
    fun insert(app: UserApp) : Completable

    @Delete
    fun delete(app: UserApp) : Completable
}

