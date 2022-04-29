package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.sql.SQLException

class SubDepartamento(main : Context) {
    var idSubdep = 0
    var idEdificio = ""
    var piso = ""
    var idArea = 0
    private var error = ""
    private val local = main

    // ------------------------ inicio de los métodos -----------------------------------
    fun insertar(): Boolean {
        val areas = BaseDatosAreas(local, "Subdepartamento", null, 1)
        error = ""
        try {
            val tabla = areas.writableDatabase
            val datos = ContentValues()
            datos.put("IdSubdepto", idSubdep)
            datos.put("IdEdificio", idEdificio)
            datos.put("Piso", piso)
            datos.put("IdArea",idArea)

            val res = tabla.insert("Subdepartamento", null, datos)
            if (res == -1L) return false

        } catch (err: SQLiteException) {
            this.error = err.message!!
            return false
        } finally {
            areas.close()
        }

        return true
    }

    fun mostrarSubDepas(): ArrayList<SubDepartamento> {
        error = ""
        val areas = BaseDatosAreas(local, "Subdepartamento", null, 1)
        val area = ArrayList<SubDepartamento>()
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Subdepartamento"
            val cursor = tabla.rawQuery(sqlSelect, null)
            if (cursor.moveToFirst()) {
                do {
                    val areaIn = SubDepartamento(local)
                    areaIn.idEdificio = cursor.getString(1)
                    areaIn.piso = cursor.getString(2)
                    areaIn.idArea = cursor.getString(3).toInt()
                    area.add(areaIn)
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            areas.close()
        }
        return area
    }

    fun mostrarSubdepa(division: String): Area {
        error = ""
        val areas = BaseDatosAreas(local, "Area", null, 1)
        val areaIn = Area(local)
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Subdepartamento where IdSubdepto=?"
            val cursor = tabla.rawQuery(sqlSelect, arrayOf(division))
            if (cursor.moveToFirst()) {
                areaIn.descripcion = cursor.getString(1)
                areaIn.division = cursor.getString(2)
                areaIn.numEmpleados = cursor.getString(3).toInt()
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            areas.close()
        }
        return areaIn
    }

    fun actualizar(): Boolean {
        error = ""
        val areas = BaseDatosAreas(local, "Subdepartamento", null, 1)
        try {
            val tabla = areas.writableDatabase
            val update = ContentValues()
            update.put("IdEdificio",idEdificio)
            update.put("Piso",piso)
            update.put("IdArea",idArea)
            val res = tabla.update("Subdepartamento", update, "IdSubdepto=?", arrayOf(idSubdep.toString()))

        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            areas.close()
        }
        return true
    }

    fun eliminar(): Boolean {
        error = ""
        val areas =BaseDatosAreas(local,"Area",null,1)
        try {
            val tabla = areas.writableDatabase
            val delete = tabla.delete("Area","IdArea=?", arrayOf(idArea.toString()))
        }catch (err: SQLException){
            this.error = err.message!!
            return false
        }finally {
            areas.close()
        }
        return true
    }



}