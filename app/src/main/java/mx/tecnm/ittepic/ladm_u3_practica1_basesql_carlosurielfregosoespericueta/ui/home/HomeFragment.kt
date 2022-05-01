package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.Area
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.ShowAreas
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.UpdateArea
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val listaIDsArea = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // desde aqui podemos codificar sin problema
        mostrarDatosArea()
        // ----------------- Inicio de los botones --------------------------------------------->
        binding.btnBuscarFH.setOnClickListener {
            if (binding.rbtnDescripcionFH.isChecked && binding.txtDesAreaFH.text.isNotEmpty()) {
                val area =
                    Area(context).mostrarAreaPorDescripcion(binding.txtDesAreaFH.text.toString())
                AlertDialog.Builder(context).setTitle("Busqueda por ${area.division}")
                    .setMessage("Descripcion del área:\n${area.descripcion}\nEmpleados del area: ${area.numEmpleados}")
                    .show()
            }
//            if (binding.rbtnDivisionFH.isChecked && binding.txtDivAreaFH.text.isNotEmpty()) {
//            }

        } // fin del botón de consulta

        binding.btnInsertarFH.setOnClickListener {
            val area = Area(requireContext())
            area.descripcion = binding.txtDesAreaFH.text.toString()
            area.division = binding.txtDivAreaFH.text.toString()
            area.numEmpleados = binding.txtCantEmpFH.text.toString().toInt()
            val res = area.insertar()
            if (res) {
                Toast.makeText(context, "Área insertada correctamente", Toast.LENGTH_LONG)
                    .show()
                binding.txtCantEmpFH.setText("")
                binding.txtDesAreaFH.setText("")
                binding.txtDivAreaFH.setText("")
            } else AlertDialog.Builder(context).setMessage(area.error).show()
            mostrarDatosArea()
        }

        // fin de la edicion paar los botones

        return root
    }

    private fun mostrarDatosArea() {
        val vistaArea = Area(context).mostarAreas()
        val nombreAreas = ArrayList<String>()
        listaIDsArea.clear()
        repeat((0 until vistaArea.size).count()) {
            val ar = vistaArea[it]
            nombreAreas.add(ar.descripcion)
            listaIDsArea.add(ar.idArea)
        }
        binding.lvAreasFH.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nombreAreas)

        binding.lvAreasFH.setOnItemClickListener { _, _, i, _ ->
            val areaIdList = listaIDsArea[i]
            val areas = Area(context).mostrarAreaPorId(areaIdList.toString())
            AlertDialog.Builder(requireContext())
                .setMessage("¿Que quieres hacer con el Área de ${areas.descripcion} ?")
                .setPositiveButton("Eliminar") { _, _ ->
                    if (areas.eliminar()) {
                        Toast.makeText(
                            requireContext(),
                            "Eliminado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        mostrarDatosArea()
                    } else
                        AlertDialog.Builder(requireContext()).setTitle("Aviso!!")
                            .setMessage(areas.error).show()
                    mostrarDatosArea()

                }
                .setNegativeButton("Modificar") { _, _ ->
                    val otraVentana = Intent(requireContext(), UpdateArea::class.java)
                    otraVentana.putExtra("IdExtra", areas.idArea)
                    otraVentana.putExtra("descripcionExtra", areas.descripcion)
                    otraVentana.putExtra("divisonExtra", areas.division)
                    otraVentana.putExtra("empleadosExtra", areas.numEmpleados)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Nada") { _, _ -> }
                .show()
        }
    } // fin del método mostrar datos


    override fun onResume() {
        mostrarDatosArea()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}