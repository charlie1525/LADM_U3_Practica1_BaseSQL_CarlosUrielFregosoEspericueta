package mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.tecnm.ittepic.ladm_u3_practica1_basesql_carlosurielfregosoespericueta.databinding.ActivityUpdateSubDepBinding

class UpdateSubDep : AppCompatActivity() {
    lateinit var binding: ActivityUpdateSubDepBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateSubDepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Actualizacion del Subdepartamento"
        val extraIdSub = this.intent.extras!!.getInt("IdSubDeExtra")
        val extraIdEdificio = this.intent.extras!!.getString("IdEdificioExtra")
        val extraPiso = this.intent.extras!!.getString("PisoExtra")

        binding.txtEdificioUpSFD.setText(extraIdEdificio)
        binding.txtPisoUpSFD.setText(extraPiso)

        binding.btnUpdateUpSFD.setOnClickListener {
            val subdepas = SubDepartamento(this)
            subdepas.idEdificio = binding.txtEdificioUpSFD.text.toString()
            subdepas.piso = binding.txtPisoUpSFD.text.toString()
            AlertDialog.Builder(this).setMessage("datos actualizados:\n ${subdepas.idEdificio},${subdepas.piso}").show()
            val res = subdepas.actualizar(extraIdSub.toString())

            if(res){
                Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show()
                binding.txtEdificioUpSFD.setText("")
                binding.txtPisoUpSFD.setText("")
            }else
                AlertDialog.Builder(this).setMessage("Error\n${subdepas.error}").show()
        }// fin de la modificacion

        binding.btnRegresarUpSFD.setOnClickListener { finish() }

    }
}