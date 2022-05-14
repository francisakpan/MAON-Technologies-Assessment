package com.francis.maonassessment.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.francis.maonassessment.data.model.table.CompetitionEntity
import com.francis.maonassessment.data.model.table.PlayerEntity
import com.francis.maonassessment.data.model.table.TeamEntity
import com.francis.maonassessment.datasource.local.dao.CompetitionDao
import com.francis.maonassessment.datasource.local.dao.SquadDao
import com.francis.maonassessment.datasource.local.dao.TeamDao

@Database(
    entities = [CompetitionEntity::class, TeamEntity::class, PlayerEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun competitionDao(): CompetitionDao
    abstract fun teamDao(): TeamDao
    abstract fun squadDao(): SquadDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "assessment_db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}