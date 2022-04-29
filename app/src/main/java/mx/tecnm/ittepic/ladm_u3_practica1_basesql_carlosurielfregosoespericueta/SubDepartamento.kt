package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.sql.SQLException

class SubDepartamento(main : Context?) {
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
        val areas = BaseDatosEmpresa(local,"Area",null,1)
        val areasTabla = areas.readableDatabase
        val selectQuery = "select a.IdArea from Area as a where Division like ?"
        val cursor = areasTabla.rawQuery(selectQuery, arrayOf("%${divSub}%"))
        val areaId = cursor.getInt(0)
        try {
            val tabla = departamentos.writableDatabase
            val datos = ContentValues()
            datos.put("IdEdificio", idEdificio)
            datos.put("Piso", piso)
            datos.put("IdArea",areaId)

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

    fun mostrarSubDepas(Edificio : String): ArrayList<SubDepartamento> {
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

    fun mostrarSubdepa(division: String): ArrayList<SubDepartamento> {
        error = ""
        val departamento = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
        val departamentoArr = ArrayList<SubDepartamento>()
        try {
            val tabla = departamento.readableDatabase
            val sqlSelect = "select sd.IdEdificio, sd.Piso, sd.IdArea from Subdepartamento as sd INNER JOIN Area as a on sd.IdArea = a.IdArea where a.Division like ?"
            val cursor = tabla.rawQuery(sqlSelect, arrayOf("%${division}%"))
            if (cursor.moveToFirst()) {
               do{
                   val subdepaIn = SubDepartamento(local)
                   subdepaIn.idEdificio = cursor.getString(0)
                   subdepaIn.piso = cursor.getString(1)
                   subdepaIn.idArea = cursor.getString(2).toInt()
                   departamentoArr.add(subdepaIn)
               }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.error = err.message!!
        } finally {
            departamento.close()
        }
        return departamentoArr
    }

    fun mostrarSubDepaPorDescripcion(descripcion: String): ArrayList<SubDepartamento>{
        error = ""
        val subDepa = ArrayList<SubDepartamento>()
        val departamento = BaseDatosEmpresa(local,"Subdepartamento",null,1)

        try{
            val tabla = departamento.readableDatabase
            //val
        }catch (err: SQLException){
            this.error = err.message!!
        }finally {
            departamento.close()
        }

        return subDepa
    }

    fun actualizar(): Boolean {
        error = ""
        val areas = BaseDatosEmpresa(local, "Subdepartamento", null, 1)
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
        val areas =BaseDatosEmpresa(local,"Area",null,1)
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