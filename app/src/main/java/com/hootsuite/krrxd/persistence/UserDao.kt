package com.hootsuite.krrxd.persistence

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.hootsuite.krrxd.persistence.User
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Persistence interaction for [User] objects.
 */
@Dao
interface UserDao {

    /**
     * Gets all [User]'s as a [Flowable] that emits every time the result(s) change (e.g. a record is inserted).
     * See https://medium.com/google-developers/room-rxjava-acb0cd4f3757
     */
    @Query(value = "select * from ${User.TABLE_NAME}")
    fun getAllUsers(): Flowable<List<User>>

    /**
     * [Maybe] return type to be supported in the future - https://issuetracker.google.com/issues/62231019
     * For now, we have to wrap this in a fromCallable() in the consuming class.
     */
    @Query(value = "select * from ${User.TABLE_NAME} where ${User.COLUMN_USER_NAME} = :userName")
    fun getUser(userName: String): User

    /**
     * Inserts the user and returns [Unit].
     * Conflict is defined as https://sqlite.org/lang_conflict.html, see index on [User].
     */
    @Insert(onConflict = REPLACE)
    fun insertUser(user: User)

    /**
     * Deletes all users and returns [Unit].
     */
    @Query(value = "delete from ${User.TABLE_NAME}")
    fun deleteAllUsers()
}