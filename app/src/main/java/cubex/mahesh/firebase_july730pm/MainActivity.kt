package cubex.mahesh.firebase_july730pm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        b2.setOnClickListener({
            var fauth = FirebaseAuth.getInstance()
            fauth.createUserWithEmailAndPassword(
                    et1.text.toString(),et2.text.toString()).
                    addOnCompleteListener({
                        if (it.isSuccessful){
                            startActivity(Intent(this@MainActivity,
                                    RegistrationActivity::class.java))
                        }else{
               Toast.makeText(this@MainActivity,
                       "Failed to Register, May be Email Already Exist",
                       Toast.LENGTH_LONG).show()
                        }
                    })
        })

        b1.setOnClickListener({

            var fauth = FirebaseAuth.getInstance()
            fauth.signInWithEmailAndPassword(
                    et1.text.toString(),et2.text.toString()).
                    addOnCompleteListener({
                        if (it.isSuccessful){
                            startActivity(Intent(this@MainActivity,
                                    UsersActivity::class.java))

                        }else{
                            Toast.makeText(this@MainActivity,
                                    "Failed to Login, May be Email Not Exist",
                                    Toast.LENGTH_LONG).show()
                        }
                    })

        })
    }
}
