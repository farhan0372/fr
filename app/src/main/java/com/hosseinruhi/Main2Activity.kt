package com.hosseinruhi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hosseinruhi.databinding.ActivityMain2Binding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class Main2Activity : AppCompatActivity(), View.OnClickListener {


    lateinit var myCalendar: Calendar

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    var finalDate = 0L
    var finalTime = 0L

    private val labels= mutableListOf<String>()

    lateinit var binding:ActivityMain2Binding

    val db by lazy {
        AppDatabase.getDatabase(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)
        setContentView(binding.root)

        binding.dateEdt.setOnClickListener(this)
        binding.timeEdt.setOnClickListener(this)
        binding.btnsave.setOnClickListener(this)
        labels.addAll(arrayListOf(getString(R.string.Personal), getString(R.string.Business), getString(
            R.string.Shopping), getString(R.string.BirthDay), getString(R.string.Reminder)))
        setUpSpinner()

    }

    private fun setUpSpinner() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, labels)
        labels.sort()
        binding.spinnerCategory.adapter = adapter

    }

    override fun onClick(v: View) {

        when (v) {
            binding.dateEdt -> {
                setListener()
            }
            binding.timeEdt -> {
                setTimeListener()
            }
            binding.btnsave -> {
                saveTodo()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveTodo() {


        var month = 0
        var dayofmonth = 0
        var year = 0
        var hourOfDay = 0
        var min = 0

        val category = binding.spinnerCategory.selectedItem.toString()
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextNote.text.toString()
        val date = binding.dateEdt.text.toString()
        val time = binding.timeEdt.text.toString()

        if (title.isEmpty()) {
            binding.editTextTitle.error = "Title required"
        }
        if (description.isEmpty()) {
            binding.editTextNote.error = "Note required"
        }
        if (date.isEmpty()){
            binding.dateEdt.error = "Date required"
        }
        if (time.isEmpty()) {
            binding.timeEdt.error = "Time required"
        }


        else {


        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {

                return@withContext db.workDao().insertTask(
                    Works(title,description,category,finalDate,finalTime)
//                        category,
//                        title,
//                        description,
//                        finalDate,
//                        finalTime
                )
            }

            Log.d("Alarm Title", "$month , $finalDate : ${myCalendar.time}")
        }
            finish()

        }
    }

     private fun setTimeListener() {

        myCalendar = Calendar.getInstance()

        timeSetListener =
            TimePickerDialog.OnTimeSetListener() { _: TimePicker, hourOfDay: Int, min: Int ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, min)
                updateTime()

            }

        val timePickerDialog = TimePickerDialog(
            this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE), false
        )

        timePickerDialog.show()

    }

    fun updateTime() { //private fun
        val myFormat = "hh:mm a"
        val sdf = SimpleDateFormat(myFormat)
        finalTime = myCalendar.time.time
        binding.timeEdt.setText(sdf.format(myCalendar.time))

    }

    private fun setListener() {
        myCalendar = Calendar.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            updateDate()

        }

        val datePickerDialog = DatePickerDialog(
            this, dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        val myFormat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myFormat)
        binding.dateEdt.setText(sdf.format(myCalendar.time))
        finalDate = myCalendar.time.time
        binding.timeInptLay.visibility = View.VISIBLE
    }
}












