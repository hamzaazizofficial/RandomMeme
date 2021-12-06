package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var currentImgUrl: String = ""
    lateinit var apiViewModel: ApiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiViewModel = ViewModelProvider(this)[ApiModel::class.java]
        showMeme()

        binding.floatBtn.setOnClickListener {
            showMeme()
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.shareBtn -> {
                    shareMeme()
                    // Handle search icon press
                    true
                }
                else -> false
            }
        }
    }

    private fun shareMeme() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this meme! $currentImgUrl")
        intent.type = "text/plain"
        val shareIntent = Intent.createChooser(intent, "Share this Meme")
        startActivity(shareIntent)
    }

    private fun showMeme() {
        binding.progressBar.visibility = View.VISIBLE
        apiViewModel.loadMeme(onSuccess = { response ->
            currentImgUrl = response.getString("url")
            Glide.with(this).load(currentImgUrl).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            }).into(binding.imageView)
        })
    }
}


