package com.educat.orteacher.assos.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MathematicsDao {
    @Query("SELECT * FROM mathematics")
    fun getAll(): Flow<List<Mathematics>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mathematics: Mathematics)

    @Update
    suspend fun update(mathematics: Mathematics)

    @Delete
    suspend fun delete(mathematics: Mathematics)

    @Query("DELETE FROM mathematics")
    suspend fun deleteAll()

}

@Dao
interface LanguageDao {
    @Query("SELECT * FROM language")
    fun getAll(): Flow<List<Language>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(language: Language)

    @Update
    suspend fun update(language: Language)

    @Delete
    suspend fun delete(language: Language)

    @Query("DELETE FROM language")
    suspend fun deleteAll()

}

@Dao
interface LiteratureDao {
    @Query("SELECT * FROM literature")
    fun getAll(): Flow<List<Literature>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(literature: Literature)

    @Update
    suspend fun update(literature: Literature)

    @Delete
    suspend fun delete(literature: Literature)

    @Query("DELETE FROM literature")
    suspend fun deleteAll()

}

@Dao
interface StudentsDao {
    @Query("SELECT * FROM students")
    fun getAll(): Flow<List<Students>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(students: Students)

    @Update
    suspend fun update(students: Students)

    @Delete
    suspend fun delete(students: Students)

    @Query("DELETE FROM students")
    suspend fun deleteAll()

}

@Dao
interface MumsDao {
    @Query("SELECT * FROM mums")
    fun getAll(): Flow<List<Mums>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mums: Mums)

    @Query("SELECT * FROM mums WHERE titlekey = :key LIMIT 1")
    fun getMumByKey(key: String): Flow<Mums?>

    @Query("DELETE FROM mums WHERE titlekey = :key")
    suspend fun deleteByKey(key: String)

    @Update
    suspend fun update(mums: Mums)

    @Delete
    suspend fun delete(mums: Mums)

    @Query("DELETE FROM mums")
    suspend fun deleteAll()

}

@Dao
interface DadsDao {
    @Query("SELECT * FROM dads")
    fun getAll(): Flow<List<Dads>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dads: Dads)

    @Query("SELECT * FROM dads WHERE titlekey = :key LIMIT 1")
    fun getDadByKey(key: String): Flow<Dads?>

    @Query("DELETE FROM dads WHERE titlekey = :key")
    suspend fun deleteByKey(key: String)

    @Update
    suspend fun update(dads: Dads)

    @Delete
    suspend fun delete(dads: Dads)

    @Query("DELETE FROM dads")
    suspend fun deleteAll()

}

@Dao
interface MeetingHistoryDao {
    @Query("SELECT * FROM meetinghistory")
    fun getAll(): Flow<List<MeetingHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meetinghistory: MeetingHistory)

    @Update
    suspend fun update(meetinghistory: MeetingHistory)

    @Delete
    suspend fun delete(meetinghistory: MeetingHistory)

    @Query("DELETE FROM meetinghistory")
    suspend fun deleteAll()

    @Query("DELETE FROM meetinghistory WHERE id = :id")
    suspend fun deleteById(id: Int)

}

@Dao
interface MathematicsItemDao {
    @Query("SELECT * FROM mathematicsitem")
    fun getAll(): Flow<List<MathematicsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mathematicsitem: MathematicsItem)

    @Update
    suspend fun update(mathematicsitem: MathematicsItem)

    @Delete
    suspend fun delete(mathematicsitem: MathematicsItem)

    @Query("DELETE FROM mathematicsitem")
    suspend fun deleteAll()

    @Query("DELETE FROM mathematicsitem WHERE numberkey = :key")
    suspend fun deleteByKey(key: String)

    @Query("SELECT * FROM mathematicsitem WHERE numberkey = :key LIMIT 1")
    fun getByKey(key: String): Flow<MathematicsItem?>

}

@Dao
interface LanguageItemDao {
    @Query("SELECT * FROM languageitem")
    fun getAll(): Flow<List<LanguageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(languageitem: LanguageItem)

    @Update
    suspend fun update(languageitem: LanguageItem)

    @Delete
    suspend fun delete(languageitem: LanguageItem)

    @Query("DELETE FROM languageitem")
    suspend fun deleteAll()

    @Query("DELETE FROM languageitem WHERE numberkey = :key")
    suspend fun deleteByKey(key: String)

    @Query("SELECT * FROM languageitem WHERE numberkey = :key LIMIT 1")
    fun getByKey(key: String): Flow<LanguageItem?>

}

@Dao
interface LiteratureItemDao {
    @Query("SELECT * FROM literatureitem")
    fun getAll(): Flow<List<LiteratureItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(literatureitem: LiteratureItem)

    @Update
    suspend fun update(literatureitem: LiteratureItem)

    @Delete
    suspend fun delete(literatureitem: LiteratureItem)

    @Query("DELETE FROM literatureitem")
    suspend fun deleteAll()

    @Query("DELETE FROM literatureitem WHERE numberkey = :key")
    suspend fun deleteByKey(key: String)

    @Query("SELECT * FROM literatureitem WHERE numberkey = :key LIMIT 1")
    fun getByKey(key: String): Flow<LiteratureItem?>

}

@Dao
interface MathematicsPointsDao {
    @Query("SELECT * FROM mathematicspoints")
    fun getAll(): Flow<List<MathematicsPoints>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mathematicspoints: MathematicsPoints)

    @Update
    suspend fun update(mathematicspoints: MathematicsPoints)

    @Delete
    suspend fun delete(mathematicspoints: MathematicsPoints)

    @Query("DELETE FROM mathematicspoints")
    suspend fun deleteAll()

}

@Dao
interface LanguagePointsDao {
    @Query("SELECT * FROM languagepoints")
    fun getAll(): Flow<List<LanguagePoints>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(languagepoints: LanguagePoints)

    @Update
    suspend fun update(languagepoints: LanguagePoints)

    @Delete
    suspend fun delete(languagepoints: LanguagePoints)

    @Query("DELETE FROM languagepoints")
    suspend fun deleteAll()

}

@Dao
interface LiteraturePointsDao {
    @Query("SELECT * FROM literaturepoints")
    fun getAll(): Flow<List<LiteraturePoints>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(literaturepoints: LiteraturePoints)

    @Update
    suspend fun update(literaturepoints: LiteraturePoints)

    @Delete
    suspend fun delete(literaturepoints: LiteraturePoints)

    @Query("DELETE FROM literaturepoints")
    suspend fun deleteAll()

}

@Dao
interface PlannedEventsDao {
    @Query("SELECT * FROM plannedevents")
    fun getAll(): Flow<List<PlannedEvents>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plannedevents: PlannedEvents)

    @Update
    suspend fun update(plannedevents: PlannedEvents)

    @Delete
    suspend fun delete(plannedevents: PlannedEvents)

    @Query("DELETE FROM plannedevents")
    suspend fun deleteAll()

}

@Dao
interface SheduleOfLessonsDao {
    @Query("SELECT * FROM sheduleoflessons")
    fun getAll(): Flow<List<SheduleOfLessons>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sheduleoflessons: SheduleOfLessons)

    @Update
    suspend fun update(sheduleoflessons: SheduleOfLessons)

    @Delete
    suspend fun delete(sheduleoflessons: SheduleOfLessons)

    @Query("DELETE FROM sheduleoflessons")
    suspend fun deleteAll()

}

@Dao
interface ClassStudentsDao {
    @Query("SELECT * FROM classstudents")
    fun getAll(): Flow<List<ClassStudents>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(classstudents: ClassStudents)

    @Update
    suspend fun update(classstudents: ClassStudents)

    @Delete
    suspend fun delete(classstudents: ClassStudents)

    @Query("DELETE FROM classstudents")
    suspend fun deleteAll()

}