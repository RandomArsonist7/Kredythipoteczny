package com.example.kredythipoteczny
import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.UniversalTimeScale.toBigDecimal
import android.os.Bundle
import android.util.Log
import android.view.View.inflate
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.kredythipoteczny.databinding.ActivityGraphBinding
import com.example.kredythipoteczny.databinding.ActivityGraphBinding.inflate
import com.example.kredythipoteczny.databinding.ActivityMainBinding
import com.example.kredythipoteczny.databinding.ActivityMainBinding.inflate
import com.google.android.material.slider.Slider
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.roundToInt
import android.text.Editable


class MainActivity : AppCompatActivity() {


    private var defaultSliderPosition = 0
    private var totalpayments = 0
    private lateinit var binding : ActivityMainBinding
    private lateinit var otherLayoutBinding: ActivityGraphBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        otherLayoutBinding = ActivityGraphBinding.inflate(layoutInflater)
        binding.length.value = defaultSliderPosition.toFloat()




        val sliderValues = intArrayOf(5,10,15,20,25,30,35)
        val lengthValue = sliderValues[0]
        totalpayments = lengthValue * 12
        val amnt = binding.amount
        val lngth = binding.length
        val intrst = binding.interestrate
        val dwnpmnt = binding.downpayment
        val eamnt = binding.editamount
        val elngth = binding.editlenght
        val eintrst = binding.editinterest
        val edwnpmnt = binding.editdownpayment
        val edwnpmntpc = binding.editdownpaymentpercent


        var interestVIEW = intrst.value
        var dwnprc = dwnpmnt.value*100
        var dwnpmntval = 0
        val lefttopay = amnt.value - (amnt.value * dwnpmnt.value)



        binding.amount.addOnChangeListener { slider, value, fromUser ->  eamnt.setText(amnt.value.toString())}


        binding.length.valueFrom = 0f
        binding.length.valueTo = (sliderValues.size -1).toFloat()
        binding.length.stepSize = 1f
        binding.length.addOnChangeListener { slider, value, fromUser ->
            val index = value.toInt()
            if (index >=0 && index < sliderValues.size) {
                val select = sliderValues[index]
                elngth.setText(select.toString())
                totalpayments = select *12
                    } else {
                        elngth.setText("error")
            }
        }
        binding.interestrate.addOnChangeListener { slider, value, fromUser ->
            interestVIEW = value*100
            eintrst.setText(interestVIEW.toString()) }
        binding.downpayment.addOnChangeListener { slider, value, fromUser ->  dwnprc = dwnpmnt.value*100
            val roundedUp = dwnprc.toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
            edwnpmntpc.setText(roundedUp.toString())
            dwnpmntval = (dwnpmnt.value * amnt.value).toInt()
            edwnpmnt.setText(dwnpmntval.toString())
            }



        binding.calculate.setOnClickListener {
            //val intent = Intent(this, Graph::class.java)


            val amountValue = amnt.value
            val lengthValue = lngth.value
            val downPaymentValue = dwnpmnt.value * amountValue
            val amountLeftToPay = amountValue - downPaymentValue
            val monthly = intrst.value/12/100
            val monthlyPayment = (amountLeftToPay * monthly * Math.pow((1+monthly).toDouble(), totalpayments.toDouble())) /
                    (Math.pow((1 + monthly).toDouble(), totalpayments.toDouble()) -1)
            Log.d("MonthlyDebug", "Downpayment: $dwnpmnt")
            Log.d("MonthlyDebug", "left: $lefttopay")

            val send = Intent(this, Graph::class.java)
            send.putExtra("payment", monthlyPayment.roundToInt())
            //Log.d("SendingActivity", "Intent extras before starting activity: ${intent.extras}")
            startActivity(send)






        }


    }
}