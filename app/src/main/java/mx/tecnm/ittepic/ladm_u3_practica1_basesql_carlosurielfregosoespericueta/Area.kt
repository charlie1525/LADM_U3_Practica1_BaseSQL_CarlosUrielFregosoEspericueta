package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.sql.SQLException

class Area(main: Context?) {
    var idArea = 0;
    var descripcion = ""
    var division = ""
    var numEmpleados = 0
    private var local = main
    var error = ""

    // ------------------------ inicio de los métodos -----------------------------------
    fun insertar(): Boolean {
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        error = ""
        try {
            val tabla = areas.writableDatabase
            val datos = ContentValues()
            datos.put("Descripcion", descripcion)
            datos.put("Division", division)
            datos.put("CantidadEmpleados", numEmpleados)

            val res = tabla.insert("Area", "IdArea", datos)
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
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        val area = ArrayList<Area>()
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Area"
            val cursor = tabla.rawQuery(sqlSelect, null)
            if (cursor.moveToFirst()) {
                do {
                    val areaIn = Area(local)
                    areaIn.idArea = cursor.getInt(0)
                    areaIn.descripcion = cursor.getString(1)
                    areaIn.division = cursor.getString(2)
                    areaIn.numEmpleados = cursor.getInt(3)
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

    fun mostrarAreaPorDivsion(division: String): ArrayList<Area> {
        error = ""
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        val areaArr = ArrayList<Area>()
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Area where Division like?"
            val cursor = tabla.rawQuery(sqlSelect, arrayOf("%${division}%"))
            if (cursor.moveToFirst()) {
                do {
                    val areaIn = Area(local)
                    areaIn.idArea = cursor.getInt(0)
                    areaIn.descripcion = cursor.getString(1)
                    areaIn.division = cursor.getString(2)
                    areaIn.numEmpleados = cursor.getInt(3)
                    areaArr.add(areaIn)
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            areas.close()
        }
        return areaArr
    }

    fun mostrarAreaPorDescripcion(Descripcion: String): Area {
        error = ""
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        val areaIn = Area(local)
        try {
            val tabla = areas.readableDatabase
            val sqlSelect = "select * from Area where Descripcion like?"
            val cursor = tabla.rawQuery(sqlSelect, arrayOf("%$Descripcion%"))
            if (cursor.moveToFirst()) {
                areaIn.idArea = cursor.getLong(0).toInt()
                areaIn.descripcion = cursor.getString(1)
                areaIn.division = cursor.getString(2)
                areaIn.numEmpleados = cursor.getInt(3)
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            areas.close()
        }
        return areaIn
    }


    fun mostrarAreaPorId(IdArea: String): Area {
        error = ""
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        val areaIn = Area(local)
        try {
            val tabla = areas.readableDatabase
            val consulta = "select * from Area where IdArea=?"
            val cursor = tabla.rawQuery(consulta, arrayOf(IdArea))
            if (cursor.moveToFirst()) {
                areaIn.idArea = cursor.getLong(0).toInt()
                areaIn.descripcion = cursor.getString(1)
                areaIn.division = cursor.getString(2)
                areaIn.numEmpleados = cursor.getLong(3).toInt()
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            areas.close()
        }
        return areaIn
    }

    fun actualizar(idAreaAc: String): Boolean {
        error = ""
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        try {
            val tabla = areas.writableDatabase
            val update = ContentValues()
            update.put("Descripcion", descripcion)
            update.put("Division", division)
            update.put("CantidadEmpleados", numEmpleados)
            val res = tabla.update("Area", update, "IdArea=?", arrayOf(idAreaAc))
            if (res == 0) return false

        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            areas.close()
        }
        return true
    }


    fun eliminar(): Boolean {
        error = ""
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        try {
            val tabla = areas.writableDatabase
            val delete = tabla.delete("Area", "IdArea=?", arrayOf(idArea.toString()))
            if (delete == 0) return false
        } catch (err: SQLException) {
            this.error = err.message!!
            return false
        } finally {
            areas.close()
        }
        return true
    }

}