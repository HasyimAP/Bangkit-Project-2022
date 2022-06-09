package com.fitverse.app.view.fitness.fitnessScan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import com.fitverse.app.databinding.ActivityScanFitnessBinding
import com.fitverse.app.ml.WorkoutModel
import com.fitverse.app.view.food.foodScan.ScanFoodResultActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min


class ScanFitnessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanFitnessBinding
    private var imageSize = 256

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonCamera.setOnClickListener {
                if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    } else {
                        TODO("VERSION.SDK_INT < M")
                    }
                ) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, 3)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
                    }
                }
            }
            buttonGallery.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, 4)
            }

        }
    }

    private fun classifyImage(image: Bitmap?) {
        try {
            val model: WorkoutModel = WorkoutModel.newInstance(applicationContext)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(imageSize * imageSize)
            image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            val outputs: WorkoutModel.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray

            var maxPos = 0
            var maxPos2 = 0
            var maxPos3 = 0

            var maxConfidence = 0f
            var max2 = 0f
            var max3 = 0f

            // 3 persentase teratas
            for (i in confidences.indices) {
                when {
                    confidences[i] > maxConfidence -> {
                        max3 = max2
                        max2 = maxConfidence
                        maxConfidence = confidences[i]
                        maxPos = i
                    }
                    confidences[i] > max2 -> {
                        max3 = max2
                        max2 = confidences[i]
                        maxPos2 = i
                    }
                    confidences[i] > max3 -> {
                        max3 = confidences[i]
                        maxPos3 = i
                    }
                }
            }

            val classes = application.assets.open("fitness_label.txt").bufferedReader().use { it.readText() }.split("\n")

            // Test pemindahan data sederhana (nama dan list akurasi)
            val hasilScan = classes[maxPos]
            val listAkurasi = String.format("%s: %.9f%%\n" + "%s: %.9f%%\n" + "%s: %.9f%%\n",
                classes[maxPos], confidences[maxPos] * 100,
                classes[maxPos2], confidences[maxPos2] * 100,
                classes[maxPos3], confidences[maxPos3] * 100)

            val intent = Intent(this@ScanFitnessActivity, ScanFitnessResultActivity::class.java)
            intent.putExtra("result", hasilScan)
            intent.putExtra("list_akurasi", listAkurasi)
            startActivity(intent)

            binding.result.text = classes[maxPos]

//            binding.confidence.text = String.format("%s: %.9f%%\n" + "%s: %.9f%%\n" + "%s: %.9f%%\n",
//                classes[maxPos], confidences[maxPos] * 100,
//                classes[maxPos2], confidences[maxPos2] * 100,
//                classes[maxPos3], confidences[maxPos3] * 100)

            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 3 && resultCode == RESULT_OK) {
            var image = data?.extras?.get("data") as Bitmap?
            val dimension = min(image!!.width, image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            binding.imageView.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            classifyImage(image)
        }
        else if(requestCode == 4 && resultCode == RESULT_OK) {
            val dat: Uri? = data?.data
            var image: Bitmap? = null
            try {
                image = MediaStore.Images.Media.getBitmap(this.contentResolver, dat)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var removeBg: Drawable? = binding.imageView.background
            removeBg = removeBg?.let { DrawableCompat.wrap(it) }
            removeBg?.let { DrawableCompat.setTint(it, Color.WHITE) }
            binding.imageView.background = removeBg
            binding.imageView.setImageBitmap(image)
            image = image?.let { Bitmap.createScaledBitmap(it, imageSize, imageSize, false) }
            classifyImage(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}