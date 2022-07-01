package com.example.hypecoachclean.data.db

import androidx.room.*

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserEntity(user: UserEntity)

    @Delete
    fun deleteUserEntity(user: UserEntity)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getUserEntity(): UserEntity

    @Query("DELETE FROM user_table")
    fun deleteAll()
}