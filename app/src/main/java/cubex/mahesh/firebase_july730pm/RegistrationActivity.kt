package cubex.mahesh.firebase_july730pm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        reg.setOnClickListener({

            var dBase = FirebaseDatabase.getInstance()
            var ref = dBase.getReference("users/" +
                    "${FirebaseAuth.getInstance().uid}")
            ref.child("name").setValue(et1.text.toString())
            ref.child("mno").setValue(et2.text.toString())
            ref.child("gender").setValue(et3.text.toString())
            ref.child("dob").setValue(et4.text.toString())
            ref.child("fcm_token").setValue(
                    FirebaseInstanceId.getInstance().token)
            startActivity(Intent(this@RegistrationActivity,
                    ProfilePicActivity::class.java))

        })
    }
}
