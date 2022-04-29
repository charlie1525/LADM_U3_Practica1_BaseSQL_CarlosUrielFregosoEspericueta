package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.sql.SQLException

class Area(main: Context) {
    var idArea = 0;
    var descripcion = ""
    var division = ""
    var numEmpleados = 0
    private var local = main
    private var error = ""

    // ------------------------ inicio de los métodos -----------------------------------
    fun insertar(): Boolean {
        val areas = BaseDatosAreas(local, "Area", null, 1)
        error = ""
        try {
            val tabla = areas.writableDatabase
            val datos = ContentValues()
            datos.put("IdArea", idArea)
            datos.put("Descripcion", descripcion)
            datos.put("Division", division)
            datos.put("CantidadEmpleados", numEmpleados)

            val res = tabla.insert("Area", null, datos)
            if (res == -1L) return false

        } catch (err: SQLiteException) {
            this.error = err.message!!
            return false
        } finally {
            areas.close()
        }

        return true
    }

    fun mostarAreas(): ArrayList<Area> {
        error = ""
        val areas = BaseDatosAreas(local, "Area", null, 1)
        val area = ArrayList<Area>()
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Area"
            val cursor = tabla.rawQuery(sqlSelect, null)
            if (cursor.moveToFirst()) {
                do {
                    val areaIn = Area(local)
                    areaIn.descripcion = cursor.getString(1)
                    areaIn.division = cursor.getString(2)
                    areaIn.numEmpleados = cursor.getString(3).toInt()
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

    fun mostrarAreaPorDivsion(division: String): Area {
        error = ""
        val areas = BaseDatosAreas(local, "Area", null, 1)
        val areaIn = Area(local)
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Area where IdArea=?"
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

    fun mostrarAreaPordescripcion(descripcion: String): Area {
        error = ""
        val areas = BaseDatosAreas(local, "Area", null, 1)
        val areaIn = Area(local)
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Area where IdArea=?"
            val cursor = tabla.rawQuery(sqlSelect, arrayOf(descripcion))
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
        val areas = BaseDatosAreas(local, "Area", null, 1)
        try {
            val tabla = areas.writableDatabase
            val update = ContentValues()
            update.put("Division", division)
            update.put("CantidadEmpleados", numEmpleados)
            val res = tabla.update("Area", update, "IdArea=?", arrayOf(idArea.toString()))

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