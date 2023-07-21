package ru.netology.nmedia3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.netology.nmedia3.dao.PostDao
import ru.netology.nmedia3.Entity.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao

}