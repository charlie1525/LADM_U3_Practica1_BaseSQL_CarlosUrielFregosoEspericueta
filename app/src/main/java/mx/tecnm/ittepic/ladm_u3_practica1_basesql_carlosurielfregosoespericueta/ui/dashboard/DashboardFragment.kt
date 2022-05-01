package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.BaseDatosEmpresa
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.R
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.SubDepartamento
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
            if(binding.rbtnDivisonInsertFD.isChecked){
                var descripcionSub = ""
                val subdepa = SubDepartamento(requireContext())
                subdepa.idEdificio = binding.txtEdificioFD.text.toString()
                subdepa.piso = binding.txtPisoFD.text.toString()
                descripcionSub = binding.spDivisionFD.selectedItem.toString()
                val res = subdepa.insertarPorDivision(descripcionSub)
                if(res){
                    Toast.makeText(context, "Subdepartamento insertado correctamente", Toast.LENGTH_SHORT).show()
                    binding.txtEdificioFD.setText("")
                    binding.txtPisoFD.setText("")
                    binding.RGBotonesInsertarFD.clearCheck()
                    binding.spDivisionFD.id=0
                }
            }
            mostrarDatosSubDep()
        }

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
                AlertDialog.Builder(requireContext()).setMessage("${subdepas.idSubdep},${subdepas.idEdificio},${subdepas.piso},${subdepas.idArea}").show()

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