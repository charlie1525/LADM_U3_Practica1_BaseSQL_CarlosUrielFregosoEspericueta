package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.databinding.ActivityUpdateAreaBinding
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.ui.home.HomeFragment

class UpdateArea : AppCompatActivity() {
    lateinit var binding: ActivityUpdateAreaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Actualizacion del área"
        val extraDivision = this.intent.extras!!.getString("divisonExtra")
        val extraDescripcion = this.intent.extras!!.getString("descripcionExtra")
        val extraEmpleados = this.intent.extras!!.getInt("empleadosExtra")
        val extraId = this.intent.extras!!.getInt("IdExtra")

        binding.txtDescUpA.setText(extraDescripcion)
        binding.txtDivUpA.setText(extraDivision)
        binding.txtEmpleadosUpA.setText(extraEmpleados.toString())


        binding.btnUpdateUpA.setOnClickListener {
            val areaIn = Area(this)
            areaIn.descripcion = binding.txtDescUpA.text.toString()
            areaIn.division = binding.txtDivUpA.text.toString()
            areaIn.numEmpleados = binding.txtEmpleadosUpA.text.toString().toInt()
            //AlertDialog.Builder(this).setMessage("${areaIn.idArea},${areaIn.numEmpleados},${areaIn.division},${areaIn.descripcion}").show()

            if(areaIn.actualizar(extraId.toString())){
                Toast.makeText(this, "Se actualizó correctamente", Toast.LENGTH_SHORT).show()
                binding.txtDivUpA.setText("")
                binding.txtDescUpA.setText("")
                binding.txtEmpleadosUpA.setText("")
            } else
                AlertDialog.Builder(this).setMessage("Hubo un error en la actualización de los datos...\n${areaIn.error}").show()
        }

        binding.btnRegresar.setOnClickListener {
            finish()
        }

    }
}