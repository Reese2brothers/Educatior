package com.educat.orteacher.assos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Mathematics::class, Language::class, Literature::class, Students::class,
                     Mums::class, Dads::class, MeetingHistory::class, MathematicsItem::class,
                     LanguageItem::class, LiteratureItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mathematicsDao(): MathematicsDao
    abstract fun languageDao(): LanguageDao
    abstract fun literatureDao(): LiteratureDao
    abstract fun studentsDao(): StudentsDao
    abstract fun mumsDao(): MumsDao
    abstract fun dadsDao(): DadsDao
    abstract fun meetinghistoryDao(): MeetingHistoryDao
    abstract fun mathematicsItemDao(): MathematicsItemDao
    abstract fun languageItemDao(): LanguageItemDao
    abstract fun literatureItemDao(): LiteratureItemDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}