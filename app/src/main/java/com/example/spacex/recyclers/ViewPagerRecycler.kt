package com.example.spacex.recyclers

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.dataclasses.Launches
import com.example.spacex.dataclasses.Rocket
import com.example.spacex.dataclasses.RocketValues
import com.example.spacex.fragments.LaunchesFragment
import com.example.spacex.fragments.MainFragment
import com.example.spacex.retrofit.MyRetrofit
import com.example.spacex.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPagerRecycler(val context : Context, val data : ArrayList<Rocket>, var values : RocketValues, val activity : FragmentActivity) : RecyclerView.Adapter<ViewPagerRecycler.VH>() {
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image : ImageView = itemView.findViewById(R.id.rocketImage)
        var rocketName : TextView = itemView.findViewById(R.id.rocketName)
        var firstLaunch : TextView = itemView.findViewById(R.id.firstLaunchText)
        var country : TextView = itemView.findViewById(R.id.countryText)
        var launchCost : TextView = itemView.findViewById(R.id.launchCostText)
        var enginesFirst : TextView = itemView.findViewById(R.id.enginesFirstText)
        var fuelFirst : TextView = itemView.findViewById(R.id.fuelFirst)
        var firstTime : TextView = itemView.findViewById(R.id.firstTimeText)
        var enginesSecond : TextView = itemView.findViewById(R.id.enginesSecondText)
        var fuelSecond : TextView = itemView.findViewById(R.id.fuelSecond)
        var secondTime : TextView = itemView.findViewById(R.id.secondTimeText)
        var firstCount : TextView = itemView.findViewById(R.id.countFirst)
        var secondCount : TextView = itemView.findViewById(R.id.countSecond)
        var thirdCount : TextView = itemView.findViewById(R.id.countThird)
        var fourthCount : TextView = itemView.findViewById(R.id.countFourth)
        var firstName : TextView = itemView.findViewById(R.id.nameFirst)
        var secondName : TextView = itemView.findViewById(R.id.nameSecond)
        var thirdName : TextView = itemView.findViewById(R.id.nameThird)
        var fourthName : TextView = itemView.findViewById(R.id.nameFourth)
        var settingsButton : Button = itemView.findViewById(R.id.settingsButton)
        var aboutButton : Button = itemView.findViewById(R.id.aboutButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.one_rocket, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.settingsButton.setOnClickListener {
            var dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.settings_modal)
            dialog.show()
            dialog.window!!.setGravity(Gravity.BOTTOM)
            dialog.window!!.setBackgroundDrawableResource(R.drawable.background_dialog)
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            var switchHeight : SwitchCompat = dialog.findViewById(R.id.switchHeight)
            var switchDiameter : SwitchCompat = dialog.findViewById(R.id.switchDiameter)
            var switchMass : SwitchCompat = dialog.findViewById(R.id.switchMass)
            var switchWeight : SwitchCompat = dialog.findViewById(R.id.switchWeight)

            switchFun(switchHeight, values.height, "Height")
            switchFun(switchDiameter, values.diameter, "Diameter")
            switchFun(switchMass, values.mass, "Mass")
            switchFun(switchWeight, values.payloadWeights, "Weight")

            var dialogCloseButton : AppCompatButton = dialog.findViewById(R.id.closeButton)
            dialogCloseButton.setOnClickListener {
                dialog.cancel()
            }
            dialog.setOnDismissListener {
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commit()
            }


        }
        Glide.with(context).load(data[position].flickr_images[0]).into(holder.image)
        holder.rocketName.text = data[position].name
        holder.firstLaunch.text = data[position].first_flight
        holder.country.text = data[position].country
        holder.launchCost.text = data[position].cost_per_launch.toString()
        holder.enginesFirst.text = data[position].first_stage.engines.toString()
        holder.fuelFirst.text = data[position].first_stage.fuel_amount_tons.toString()
        holder.firstTime.text = data[position].first_stage.burn_time_sec.toString()
        holder.enginesSecond.text = data[position].second_stage.engines.toString()
        holder.fuelSecond.text = data[position].second_stage.fuel_amount_tons.toString()
        holder.secondTime.text = data[position].second_stage.burn_time_sec.toString()

        when(values.height){
            0 -> {
                holder.firstCount.text = data[position].height.meters.toString()
                holder.firstName.text = "Высота, m"
            }
            1 -> {
                holder.firstCount.text = data[position].height.feet.toString()
                holder.firstName.text = "Высота, ft"
            }
        }
        when(values.diameter){
            0 -> {
                holder.secondCount.text = data[position].diameter.meters.toString()
                holder.secondName.text = "Диаметр, m"
            }
            1 -> {
                holder.secondCount.text = data[position].diameter.feet.toString()
                holder.secondName.text = "Диаметр, ft"
            }
        }
        when(values.mass){
            0 -> {
                holder.thirdCount.text = data[position].mass.kg.toString()
                holder.thirdName.text = "Масса, kg"
            }
            1 -> {
                holder.thirdCount.text = data[position].mass.lb.toString()
                holder.thirdName.text = "Масса, lb"
            }
        }
        when(values.payloadWeights){
            0 -> {
                holder.fourthCount.text = data[position].payload_weights[0].kg.toString()
                holder.fourthName.text = "Нагрузка, kg"
            }
            1 -> {
                holder.fourthCount.text = data[position].payload_weights[0].lb.toString()
                holder.fourthName.text = "Нагрузка, lb"
            }
        }
        var newPos = position
        holder.aboutButton.setOnClickListener {
            MyRetrofit().getRetrofit()
                .create(RetrofitApi::class.java)
                .getLaunches()
                .enqueue(object : Callback<ArrayList<Launches>> {
                    override fun onResponse(
                        call: Call<ArrayList<Launches>>,
                        response: Response<ArrayList<Launches>>
                    ) {
                        if (response.isSuccessful){
                            response.body()!!.let {
                                var newList = ArrayList<Launches>()
                                for (i in it){
                                    if (i.rocket == data[newPos].id){
                                        newList.add(i)
                                    }
                                }
                                var fragment = LaunchesFragment()
                                var bundle = Bundle()
                                bundle.putSerializable("array", newList)
                                fragment.arguments = bundle
                                activity.supportFragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .commit()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<Launches>>, t: Throwable) {

                    }

                })
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun switchFun(switch : SwitchCompat, value : Int, prefValue : String){
        if (value == 0){
            switch.isChecked = false
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (b){
                    val prefs = activity.getSharedPreferences("Values", Context.MODE_PRIVATE)
                    prefs.edit().putInt(prefValue, 1).apply()
                }
            }
        }
        else{
            switch.isChecked = true
            switch.setOnCheckedChangeListener { compoundButton, b ->
                if (!b){
                    val prefs = activity.getSharedPreferences("Values", Context.MODE_PRIVATE)
                    prefs.edit().putInt(prefValue, 0).apply()
                }
            }
        }
    }
}

