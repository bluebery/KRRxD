package com.hootsuite.krrxd.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.hootsuite.krrxd.persistence.User.Companion.COLUMN_USER_NAME
import com.hootsuite.krrxd.persistence.User.Companion.TABLE_NAME

/**
 * Represents a User in persistence.
 * Defined by user name and password, with a generated id.
 *
 * Unique by username. Replace on conflict due to definition in [UserDao]
 */
@Entity(tableName = TABLE_NAME,
        indices = arrayOf(Index(value = COLUMN_USER_NAME, unique = true)))
data class User(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                @ColumnInfo(name = COLUMN_USER_NAME) var userName: String,
                var password: String) {

    companion object {

        internal const val TABLE_NAME = "user"
        internal const val COLUMN_USER_NAME = "user_name"
    }
}