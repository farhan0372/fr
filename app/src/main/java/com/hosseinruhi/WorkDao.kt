package com.hosseinruhi


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface WorkDao {

    @Insert
     fun insertTask(works: Works): Long

    @Query("SELECT * FROM Works where isFinished == 0")
    fun getTask(): LiveData<List<Works>>

    @Query("Update Works Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("UPDATE Works Set isShow = :isShow  where id LIKE :id")
   fun isShownUpdate(id:Long , isShow : Int)

    @Query("SELECT * from Works where id Like :id")
    fun getWorks(id : Long): Works

    @Query("Delete from Works  where id=:uid")
    fun deleteTask(uid:Long)



}