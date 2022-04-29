package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.ui.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.Area
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // desde aqui podemos codificar sin problema



        val buttonGroup = binding.RGbotonesBusquedaFH
        buttonGroup.visibility = View.GONE


        var contador = 0
        // ----------------- Inicio de los botones --------------------------------------------->
        binding.btnConsultaFH.setOnClickListener {
            val areas = Area(context)
            if (binding.txtCantEmpFH.text.isEmpty() || binding.txtDesAreaFH.text.isEmpty() || binding.txtDivAreaFH.text.isEmpty()) {
                AlertDialog.Builder(context).setTitle("Aviso!!")
                    .setMessage("Tienes vcampos vacios...")
                    .setPositiveButton("Ok"){_,_ ->}
                    .show()
                binding.txtDesAreaFH.requestFocus()
                it.hideKeyboard()
                return@setOnClickListener
            }

            if (contador == 0) {
                Toast.makeText(
                    context,
                    "Vuelva a presionar para elegir porque campo haacer la busqueda",
                    Toast.LENGTH_SHORT
                ).show()
                contador += 1
                buttonGroup.visibility = View.VISIBLE
                return@setOnClickListener
            }

        }


        // fin de la edicion paar los botones

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.hideKeyboard() {
        val activity = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.hideSoftInputFromWindow(windowToken,0)
    }
}