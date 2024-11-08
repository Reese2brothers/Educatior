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