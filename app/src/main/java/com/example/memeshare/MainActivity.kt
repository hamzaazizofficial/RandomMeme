package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var apiViewModel: MemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiViewModel = ViewModelProvider(this)[MemeViewModel::class.java]
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
        observeViewModel()
    }

    private fun observeViewModel() {
        apiViewModel.memeUrl.observe(this) { imageUrl ->
            currentImgUrl = imageUrl
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
        apiViewModel.loadMeme(subredditName = "")
    }
}


