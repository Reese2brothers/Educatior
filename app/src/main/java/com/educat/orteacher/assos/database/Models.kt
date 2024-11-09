package com.educat.orteacher.assos.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mathematics(
    val day : String,
    var typeofwork : String,
    var points : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Language(
    val day : String,
    var typeofwork : String,
    var points : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Literature(
    val day : String,
    var typeofwork : String,
    var points : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Students(
    val name : String,
    var data : String,
    var office : String,
    var performance : String,
    var behaviour : String,
    var hobbies : String,
    var pet : String,
    var image : String,
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Mums(
    val name : String,
    var yers : String,
    var homeaddress : String,
    var workaddress : String,
    var position : String,
    val titlekey : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class Dads(
    val name : String,
    var yers : String,
    var homeaddress : String,
    var workaddress : String,
    var position : String,
    val titlekey : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class MeetingHistory(
    val data : String,
    var label : String,
    var coments : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class MathematicsItem(
    val topic : String,
    var homework : String,
    var classnumber : String,
    var classroom : String,
    var comment : String,
    val numberkey : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class LanguageItem(
    val topic : String,
    var homework : String,
    var classnumber : String,
    var classroom : String,
    var comment : String,
    val numberkey : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class LiteratureItem(
    val topic : String,
    var homework : String,
    var classnumber : String,
    var classroom : String,
    var comment : String,
    val numberkey : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}