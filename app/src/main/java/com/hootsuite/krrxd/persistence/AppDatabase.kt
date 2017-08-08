package com.hootsuite.krrxd.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Indicates which entities are contained within this app's database
 * and the version of it
 *
 * Exposes the DAO for given entities
 */
@Database(entities = arrayOf(User::class), version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}