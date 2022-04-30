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
        requireContext().hideKeyboard(root)
        mostrarDatosArea()
        val buttonGroup = binding.RGbotonesBusquedaFH
        buttonGroup.visibility = View.GONE

        var contador = 0
        // ----------------- Inicio de los botones --------------------------------------------->
        binding.btnBuscarFH.setOnClickListener {
            //val areas = Area(context)
            if (binding.txtDesAreaFH.text.isEmpty() || binding.txtDivAreaFH.text.isEmpty()) {
                AlertDialog.Builder(context).setTitle("Aviso!!")
                    .setMessage("Tienes vcampos vacios...")
                    .setPositiveButton("Ok") { _, _ -> }
                    .show()
                binding.txtDesAreaFH.requestFocus()
                requireContext().hideKeyboard(it)
                return@setOnClickListener
            }

            if (contador == 0) {
                AlertDialog.Builder(context)
                    .setMessage("Vuelva a presionar para elegir porque campo hacer la busqueda")
                    .show()
                contador += 1
                buttonGroup.visibility = View.VISIBLE
                return@setOnClickListener
            }


        } // fin del botón de consulta
        binding.btnInsertarFH.setOnClickListener {
            val area = Area(requireContext())
            area.descripcion = binding.txtDesAreaFH.text.toString()
            area.division = binding.txtDivAreaFH.text.toString()
            area.numEmpleados = binding.txtCantEmpFH.text.toString().toInt()
            val res = area.insertar()
            if (res) Toast.makeText(context, "Área incertada correctamente", Toast.LENGTH_LONG)
                .show()
            else AlertDialog.Builder(context).setMessage(area.error).show()
            mostrarDatosArea()
        }

        // fin de la edicion paar los botones

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        mostrarDatosArea()
        super.onResume()
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun mostrarDatosArea() {
        val vistaArea = Area(context).mostarAreas()
        val nombreAreas = ArrayList<String>()
        listaIDsArea.clear()
        repeat((0 until vistaArea.size).count()) {
            val ar = vistaArea[it]
            listaIDsArea.add(ar.idArea)
            nombreAreas.add(ar.division)
            nombreAreas.add(ar.descripcion)
        }
        binding.lvAreasFH.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nombreAreas)

        binding.lvAreasFH.setOnItemClickListener { _, _, i, _ ->
            val areaIdList = listaIDsArea[i]
            val areas = Area(context)
            AlertDialog.Builder(context)
                .setMessage("Que quieres hacer con el Área ${areas.division} ?")
                .setPositiveButton("Eliminar") { _, _ ->
                    areas.eliminar()
                    mostrarDatosArea()
                }
                .setNegativeButton("Modificar") { _, _ ->
                    val otraVentana = Intent(context,UpdateArea::class.java)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Nada"){_,_, ->}
                .show()
        }
    }
}