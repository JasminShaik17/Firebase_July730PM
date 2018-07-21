package cubex.mahesh.firebase_july730pm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile_pic.*

class ProfilePicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_pic)

        cam.setOnClickListener({
                var i = Intent("android.media.action.IMAGE_CAPTURE")
                startActivityForResult(i,123)
        })

        gal.setOnClickListener({

            var i = Intent( )
            i.setAction(Intent.ACTION_GET_CONTENT)
            i.setType("image/*")
            startActivityForResult(i,124)

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==123 && resultCode==Activity.RESULT_OK)
        {
           var bmp =      data!!.extras.get("data") as Bitmap
            iview.setImageBitmap(bmp)

            val fos = openFileOutput("profile_pic.png",
                    Context.MODE_PRIVATE)
            bmp.compress(Bitmap.CompressFormat.PNG,
                    100, fos)
            fos.flush()
            fos.close()

        var fis =  openFileInput("profile_pic.png")

            var fref = FirebaseStorage.getInstance().
                    getReference("/users/${FirebaseAuth.getInstance().uid}")
            var cref = fref.child("profile_pic.png")
            cref.putStream(fis).addOnSuccessListener {
                var dBase = FirebaseDatabase.getInstance()
                var ref = dBase.getReference("users/" +
                        "${FirebaseAuth.getInstance().uid}")
                ref.child("profile_pic").setValue(cref.downloadUrl.toString())
            }

        }else if(requestCode==124 && resultCode==Activity.RESULT_OK){
            var uri = data!!.data
            iview.setImageURI(uri)

            var fref = FirebaseStorage.getInstance().
   getReference("/users/${FirebaseAuth.getInstance().uid}")
            var cref = fref.child("profile_pic.png")
            cref.putFile(uri).addOnSuccessListener {
                var dBase = FirebaseDatabase.getInstance()
                var ref = dBase.getReference("users/" +
                        "${FirebaseAuth.getInstance().uid}")
                ref.child("profile_pic").setValue(cref.downloadUrl.toString())
            }
        }
    }



}
