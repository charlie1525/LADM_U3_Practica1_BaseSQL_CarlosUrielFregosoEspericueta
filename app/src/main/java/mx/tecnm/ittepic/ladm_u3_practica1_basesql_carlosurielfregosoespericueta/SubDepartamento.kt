package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import java.lang.reflect.Array
import java.sql.SQLException

class SubDepartamento(main: Context?) {
    var idSubdep = 0
    var idEdificio = ""
    var piso = ""
    var idArea = 0
    var Sdescripcion = ""
    var Sdivision = ""
    private var error = ""
    private val local = main

    // ------------------------ inicio de los métodos -----------------------------------
    fun insertarPorDivision(divSub: String): Boolean {
        val departamentos = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
        error = ""
        val areas = BaseDatosEmpresa(local, "Area", null, 1)
        val areasTabla = areas.readableDatabase
        val selectQuery = "select a.IdArea from Area as a where Division like ?"
        val cursor = areasTabla.rawQuery(selectQuery, arrayOf("%${divSub}%"))
        var areaId = 0


        try {
            if (cursor.moveToFirst()) {
                areaId = cursor.getInt(0)
            }
            val tabla = departamentos.writableDatabase
            val datos = ContentValues()
            datos.put("IdEdificio", idEdificio)
            datos.put("Piso", piso)
            datos.put("IdArea", areaId)

            val res = tabla.insert("Subdepartamento", "IdSubdepto", datos)
            if (res == -1L) return false

        } catch (err: SQLiteException) {
            this.error = err.message!!
            return false
        } finally {
            departamentos.close()
        }

        return true
    }
    fun insertarPorDescripcion(descSub: String): Boolean {
        val departamentos = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
        error = ""
        val areas = BaseDatosEmpresa(local,"Area",null,1)
        val areasTabla = areas.readableDatabase
        val selectQuery = "select a.IdArea from Area as a where Descripcion like ?"
        val cursor = areasTabla.rawQuery(selectQuery, arrayOf("%${descSub}%"))
        //val areaId = cursor.getInt(0)
        try {
            val tabla = departamentos.writableDatabase
            val datos = ContentValues()
            datos.put("IdEdificio", idEdificio)
            datos.put("Piso", piso)
            datos.put("IdArea",cursor.getInt(0))

            val res = tabla.insert("Subdepartamento", "IdSubdepto", datos)
            if (res == -1L) return false

        } catch (err: SQLiteException) {
            this.error = err.message!!
            return false
        } finally {
            departamentos.close()
        }

        return true
    }

    fun mostrarSubDepasPorEdificio(Edificio: String): ArrayList<SubDepartamento> {
        error = ""
        val departamentos = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
        val departamentoArr = ArrayList<SubDepartamento>()
        try {
            val tabla = departamentos.readableDatabase
            val sqlSelect = "select IdEdificio,Piso,IdArea from Subdepartamento where IdEdificio=?"
            val cursor = tabla.rawQuery(sqlSelect, arrayOf(Edificio))
            if (cursor.moveToFirst()) {
                do {
                    val subdepaIn = SubDepartamento(local)
                    subdepaIn.idEdificio = cursor.getString(1)
                    subdepaIn.piso = cursor.getString(2)
                    subdepaIn.idArea = cursor.getString(3).toInt()
                    departamentoArr.add(subdepaIn)
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            departamentos.close()
        }
        return departamentoArr
    }

    fun mostrarSubdepaPorDivision(division: String): ArrayList<SubDepartamento> {
        error = ""
        val departamento = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
        val departamentoArr = ArrayList<SubDepartamento>()
        try {
            val tabla = departamento.readableDatabase
            val sqlSelect =
                "select sd.IdEdificio, sd.Piso, sd.IdArea from Subdepartamento as sd INNER JOIN Area as a on sd.IdArea = a.IdArea where a.Division like ?"
            val cursor = tabla.rawQuery(sqlSelect, arrayOf("%${division}%"))
            if (cursor.moveToFirst()) {
                do {
                    val subdepaIn = SubDepartamento(local)
                    subdepaIn.idEdificio = cursor.getString(0)
                    subdepaIn.piso = cursor.getString(1)
                    subdepaIn.idArea = cursor.getString(2).toInt()
                    departamentoArr.add(subdepaIn)

                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            departamento.close()
        }
        return departamentoArr
    }

    fun mostrarSubDepaPorDescripcion(descripcion: String): ArrayList<SubDepartamento> {
        error = ""
        val subDepa = ArrayList<SubDepartamento>()
        val departamento = BaseDatosEmpresa(local, "Subdepartamento", null, 1)

        try {
            val tabla = departamento.readableDatabase
            //val
        } catch (err: SQLException) {
            this.error = err.message!!
        } finally {
            departamento.close()
        }

        return subDepa
    }

    fun mostrarSubDepas(): ArrayList<SubDepartamento> {
        error = ""
        val subdepas = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
        val subdepa = ArrayList<SubDepartamento>()

        try {
            val sTablas = subdepas.readableDatabase
            val query = "select * from Subdepartamento"
            val cursor = sTablas.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val depaIn = SubDepartamento(local)
                    depaIn.idSubdep = cursor.getInt(0)
                    depaIn.idEdificio = cursor.getString(1)
                    depaIn.piso = cursor.getString(2)
                    depaIn.idArea = cursor.getInt(3)
                    subdepa.add(depaIn)

                } while (cursor.moveToNext())
            }// fin del if
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            subdepas.close()
        }
        return subdepa
    }

    fun mostrarSubDepasPorId(identificador: String):SubDepartamento{
        error = ""
        val subdepa= SubDepartamento(local)
        val subdepas = BaseDatosEmpresa(local,"Subdepartamento",null,1)
        try {
            val tabla = subdepas.readableDatabase
            val query = "select * from Subdepartamento where IdSubdepto=?"
            val cursor = tabla.rawQuery(query,arrayOf(identificador))
            if(cursor.moveToFirst()){
                subdepa.idSubdep = cursor.getInt(0)
                subdepa.idEdificio = cursor.getString(1)
                subdepa.piso = cursor.getString(2)
                subdepa.idArea = cursor.getInt(3)
            }
        }
        catch (err: SQLiteException){ this.error = err.message!! }
        finally {subdepas.close()}
        return subdepa
    }

    fun actualizar(): Boolean {
        error = ""
        val areas = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
        try {
            val tabla = areas.writableDatabase
            val update = ContentValues()
            update.put("IdEdificio", idEdificio)
            update.put("Piso", piso)
            update.put("IdArea", idArea)
            val res = tabla.update(
                "Subdepartamento",
                update,
                "IdSubdepto=?",
                arrayOf(idSubdep.toString())
            )

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
        } catch (err: SQLException) {
            this.error = err.message!!
            return false
        } finally {
            areas.close()
        }
        return true
    }


}