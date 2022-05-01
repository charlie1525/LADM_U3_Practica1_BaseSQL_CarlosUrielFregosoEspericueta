package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.ui.dashboard

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.BaseDatosEmpresa
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.SubDepartamento
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.UpdateArea
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.UpdateSubDep
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var spDiv: Spinner
    private lateinit var spDes: Spinner
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val listaIdsSubDep = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //codificar desde aqui
        //mostrarDatosSubDep()

        spDiv = binding.spDivisionFD
        val listaDivisiones = llenarPorDivisiones()
        spDiv.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listaDivisiones)

        spDes = binding.spDescripcionD
        val listaDescripcion = llenarPorDescripcion()
        spDes.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listaDescripcion)

        //Toast.makeText(context, "${spDiv.selectedItem}", Toast.LENGTH_SHORT).show()
        binding.btnInsertarFD.setOnClickListener {
            if(binding.txtEdificioFD.text.isEmpty() || binding.txtEdificioFD.text.isBlank() && binding.txtPisoFD.text.isEmpty() ||binding.txtPisoFD.text.isBlank()){
                AlertDialog.Builder(context).setTitle("Error...").setMessage("Revise que no tenga campos vacios o solo con espacios").show()
                return@setOnClickListener
            }
            if (!binding.rbtnDivisonInsertFD.isChecked || !binding.rbtnDescripcionInsertFD.isChecked) {
                AlertDialog.Builder(context).setTitle("Aviso!!")
                    .setMessage("No has seleccionado el campo porel cual quieres insertar...")
                    .setPositiveButton("ok"){_,_ ->}
                    .show()
                return@setOnClickListener
            }
            if(binding.rbtnDivisonInsertFD.isChecked){
                var divisionSub = ""
                val subdepa = SubDepartamento(requireContext())
                subdepa.idEdificio = binding.txtEdificioFD.text.toString()
                subdepa.piso = binding.txtPisoFD.text.toString()
                divisionSub = binding.spDivisionFD.selectedItem.toString()
                val res = subdepa.insertarPorDivision(divisionSub)
                if(res){
                    Toast.makeText(context, "Subdepartamento insertado correctamente", Toast.LENGTH_SHORT).show()
                    binding.txtEdificioFD.setText("")
                    binding.txtPisoFD.setText("")
                    binding.RGBotonesInsertarFD.clearCheck()
                    binding.spDivisionFD.id=0
                    binding.spDescripcionD.id=0

                }
            }
            if(binding.rbtnDescripcionInsertFD.isChecked){
                var descripcionSub = ""
                val subdepa = SubDepartamento(requireContext())
                subdepa.idEdificio = binding.txtEdificioFD.text.toString()
                subdepa.piso = binding.txtPisoFD.text.toString()
                descripcionSub = binding.spDescripcionD.selectedItem.toString()
                val res = subdepa.insertarPorDescripcion(descripcionSub)
                if(res){
                    Toast.makeText(context, "Subdepartamento insertado correctamente", Toast.LENGTH_SHORT).show()
                    binding.txtEdificioFD.setText("")
                    binding.txtPisoFD.setText("")
                    binding.RGBotonesInsertarFD.clearCheck()
                    binding.spDivisionFD.id=0
                    binding.spDescripcionD.id=0
                }
            }
            mostrarDatosSubDep()
        } // fin del boton para insertar

        return root
    }

    private fun llenarPorDivisiones(): ArrayList<String>{
        val listaDiv = ArrayList<String>()
        var division = ""
        val baseArea = BaseDatosEmpresa(context,"Area",null,1)
        val consultaSQL = "select Division from Area"
        val areaTabla = baseArea.readableDatabase
        val cursor = areaTabla.rawQuery(consultaSQL,null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    division = cursor.getString(0)
                    listaDiv.add(division)
                }while (cursor.moveToNext())
            }
        }
        areaTabla.close()
        return listaDiv
    }
    private fun llenarPorDescripcion(): ArrayList<String>{
        val listaDiv = ArrayList<String>()
        var division = ""
        val baseArea = BaseDatosEmpresa(context,"Area",null,1)
        val consultaSQL = "select Descripcion from Area"
        val areaTabla = baseArea.readableDatabase
        val cursor = areaTabla.rawQuery(consultaSQL,null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    division = cursor.getString(0)
                    listaDiv.add(division)
                }while (cursor.moveToNext())
            }
        }
        areaTabla.close()
        return listaDiv
    }

    private fun mostrarDatosSubDep(){
        val subdepa = SubDepartamento(context).mostrarSubDepas()
        val subdepasInfo = ArrayList<String>()
        listaIdsSubDep.clear()
        repeat((0 until subdepa.size).count()){
            val sd = subdepa[it]
            subdepasInfo.add("${sd.idEdificio}, ${sd.piso}")
            listaIdsSubDep.add(sd.idSubdep)

            binding.lvSubDepasFD.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,subdepasInfo)
            binding.lvSubDepasFD.setOnItemClickListener{_,_,i,_ ->
                val subDepId = listaIdsSubDep[i]
                val subdepas = SubDepartamento(requireContext()).mostrarSubDepasPorId(subDepId.toString())
                AlertDialog.Builder(requireContext()).setMessage("¿Que quieres hacer con ${subdepas.idEdificio} en el piso ${subdepas.piso}")
                    .setNegativeButton("Eliminar"){_,_ ->
                        if(subdepas.eliminar()){
                            Toast.makeText(requireContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show()
                            mostrarDatosSubDep()
                        }
                    }// fin del negative button
                    .setPositiveButton("Modificar"){_,_ ->
                        val modificarSub = Intent(requireContext(),UpdateSubDep::class.java)
                        modificarSub.putExtra("IdSubDeExtra",subdepas.idSubdep)
                        modificarSub.putExtra("IdEdificioExtra",subdepas.idEdificio)
                        modificarSub.putExtra("PisoExtra",subdepas.piso)
                        startActivity(modificarSub)
                    }// fin del positive button
                    .setNeutralButton("Nada"){_,_ ->}.show()

            }
        }


    }

    override fun onResume() {
        //Metodo para mostrar los subdepartamentos
        mostrarDatosSubDep()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}