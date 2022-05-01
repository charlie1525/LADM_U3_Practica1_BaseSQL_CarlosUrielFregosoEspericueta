package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatosEmpresa(context: Context?,
                       name: String?,
                       factory: SQLiteDatabase.CursorFactory?,
                       version: Int)
    : SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table Area(" +
                    "IdArea INTEGER  PRIMARY KEY AUTOINCREMENT," +
                    "Descripcion varchar(200)," +
                    "Division varchar(50)," +
                    "CantidadEmpleados INTEGER" +
                    ");"
        ) // fin de la creacion de la primer tabla

        db.execSQL(
            "create table Subdepartamento(" +
                    "IdSubdepto INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "IdEdificio varchar(20)," +
                    "Piso varchar(20)," +
                    "IdArea INTEGER REFERENCES Area(IdArea)"+
                    ");"
        )// fin de la segunda consulta

    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {

    }

}