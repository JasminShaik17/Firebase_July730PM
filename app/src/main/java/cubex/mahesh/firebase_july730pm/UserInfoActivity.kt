package cubex.mahesh.firebase_july730pm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_info.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class UserInfoActivity : AppCompatActivity() {
        var fcm_token :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

     var uid =    intent.getStringExtra("uid")
     var dBase = FirebaseDatabase.getInstance()
     var ref = dBase.getReference("users/{$uid}")
     ref.addValueEventListener(object : ValueEventListener {
         override fun onDataChange(p0: DataSnapshot?) {
                var childs = p0!!.children
             childs.forEach {
                 when(it.key.toString()){
                     "name"-> tv1.text="Name :${it.value.toString()}"
                     "mno"->tv2.text="Mno :${it.value.toString()}"
                     "dob"->tv3.text="DOB :${it.value.toString()}"
                     "gender"->tv4.text="Gender :${it.value.toString()}"
                    "fcm_token"-> fcm_token = it.value.toString()
                 }
             }

             notify.setOnClickListener({
                 runOnUiThread({

                     var jsonObjec: JSONObject? = null
                     var bodydata:String = et1.text.toString()

                     jsonObjec =  JSONObject()
                     var fcm_tokens = mutableListOf<String>()
                     fcm_tokens.add(fcm_token!!)

                     var   jsonArray: JSONArray = JSONArray(fcm_tokens)
                     jsonObjec.put("registration_ids", jsonArray);
                     var jsonObjec2: JSONObject = JSONObject()
                     jsonObjec2.put("body", bodydata);
                     jsonObjec2.put("title", "July730PM")
                     jsonObjec.put("notification", jsonObjec2);

                     jsonObjec.put("time_to_live", 172800);
                     jsonObjec.put("priority", "HIGH");

                     println("*************")
                     print(jsonObjec)
                     println("*************")


                     val client = OkHttpClient()
                     val JSON = MediaType.parse("application/json; charset=utf-8")
                     val body = RequestBody.create(JSON, jsonObjec.toString())
                     val request = Request.Builder()
                             .addHeader("Content-Type", "application/json")
                             .addHeader("Authorization", "key=AAAAVZZ82ow:APA91bHsxqMabAKgNZYl5MkuWuQ9EiTAzp9R3ALxm8EA_XdH24YifrvtTgzIDwCa2LhvsAmjFJgeCGNxcMaPBOH5Hu20YN0uE2QrWhbkadvcpyGHOsTOoCTwzpUwEA1qgTWIb2kue3KfHRCJKaw8isNB4moNR-SNGw")
                             .url("https://fcm.googleapis.com/fcm/send")
                             .post(body)
                             .build()
                     val call = client.newCall(request)
                     call.enqueue(object : Callback {
                         override fun onResponse(call: Call?, response: Response?) {

//                            Toast.makeText(this@MessageActivity,
//                                    "Message Sending Success",
//                                    Toast.LENGTH_LONG).show()

                         }
                         override fun onFailure(call: Call?, e: IOException?) {
//                            Toast.makeText(this@MessageActivity,
//                                    "Message Sending Failure",
//                                    Toast.LENGTH_LONG).show()
                         }
                     })

                 })

             })
         }

         override fun onCancelled(p0: DatabaseError?) {

         }
     })
    }
}
