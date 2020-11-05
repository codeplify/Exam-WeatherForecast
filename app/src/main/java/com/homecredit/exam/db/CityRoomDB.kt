package com.homecredit.exam.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.homecredit.exam.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//
@Database(entities = arrayOf(City::class), version = 3, exportSchema = false)
abstract class CityRoomDB : RoomDatabase(){

    abstract fun cityDao(): CityDao

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                    database ->
                scope.launch {
                    var cityDao = database.cityDao()
                }
            }
        }
    }

    //singleton
    companion object{
        @Volatile
        private var INSTANCE: CityRoomDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CityRoomDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, CityRoomDB::class.java,"city_weather_db")
                    .fallbackToDestructiveMigration().addCallback(DatabaseCallback(scope)).build()

                INSTANCE = instance
                instance
            }

        }
    }

}