package cubex.mahesh.firebase_july730pm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_users.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        var dBase = FirebaseDatabase.getInstance()
        var fref = dBase.getReference("users")
        fref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                  var it =   p0!!.children
                var uids = mutableListOf<String>()
                var names = mutableListOf<String>()
                var fcm_tokens = mutableListOf<String>()
                it.forEach {
                    uids.add(it.key)
                  var childs_it =   it.children
                    childs_it.forEach{
                        when(it.key){
                            "name"-> names.add(it.value.toString())
                            "fcm_token" -> fcm_tokens.add(it.value.toString())
                        }
                    }
                }

                var adapter = ArrayAdapter<String>(this@UsersActivity,
                        android.R.layout.simple_list_item_single_choice,names)
                lview.adapter = adapter

                lview.setOnItemClickListener { adapterView, view, i, l ->
                    var intent = Intent(this@UsersActivity,
                            UserInfoActivity::class.java)
                    intent.putExtra("uid",uids.get(i))
                    startActivity(intent)
                }

                nall.setOnClickListener({


                    runOnUiThread({

                        var jsonObjec: JSONObject? = null
                        var bodydata:String = et1.text.toString()

                        jsonObjec =  JSONObject()

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
        })

    }
}
