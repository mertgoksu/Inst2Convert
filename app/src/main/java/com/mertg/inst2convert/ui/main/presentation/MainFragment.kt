package com.mertg.inst2convert.ui.main.presentation

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mertg.inst2convert.R
import com.mertg.inst2convert.base.BaseFragment
import com.mertg.inst2convert.databinding.FragmentMainBinding
import com.mertg.inst2convert.service.DownloadResponse
import com.mertg.inst2convert.service.InstApiService
import com.mertg.inst2convert.service.VideoDownloadRequest
import com.mertg.inst2convert.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import org.chromium.base.ThreadUtils.runOnUiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.Callback
import retrofit2.Response


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initUI() {
        initButtons()
        binding.checkServerText.text = "Checking..."
        binding.checkServerIcon.setBackgroundResource(R.drawable.ic_dot_gray)

        wakeServer()

        binding.wakeButton.setOnClickListener {
            wakeServer()
            binding.wakeButton.disableButton()
            binding.convertButton.disableButton()

            binding.urlInput.isEnabled = false
            binding.checkServerText.text = "Checking..."
            binding.checkServerIcon.setBackgroundResource(R.drawable.ic_dot_gray)
        }

        binding.convertButton.setOnClickListener {
            val youtubeUrl = binding.urlInput.text.toString()

            if (youtubeUrl.isNotEmpty()) {
                disableButtons() // Disables the buttons while the request is in progress
                viewProgress() // Shows a loading spinner or progress bar


                val videoRequest = VideoDownloadRequest(youtubeUrl)

                RetrofitClient.instance.downloadVideo("720p", videoRequest).enqueue(object : Callback<DownloadResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<DownloadResponse>,
                        response: Response<DownloadResponse>
                    )
                    {
                    if (response.isSuccessful) {
                            response.body()?.let {
                                Toast.makeText(context, "Download started: ${it.message}", Toast.LENGTH_LONG).show()
                                Log.d("Mertos","succes")
                            }
                        } else {
                            Toast.makeText(context, "Failed to download video", Toast.LENGTH_LONG).show()
                            Log.d("Mertos","else")

                        }
                    }

                    override fun onFailure(call: retrofit2.Call<DownloadResponse>, t: Throwable) {
                        Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.d("Mertos","onfail  ${t.message}")
                    }
                })

            } else {
                Toast.makeText(context, "Please enter a valid URL", Toast.LENGTH_SHORT).show()
            }
        }



        binding.clearButton.setOnClickListener {
            binding.urlInput.text = null
        }

        observeViewModel()
    }


    object RetrofitClient {
        private const val BASE_URL = "http://176.220.113.16:5000" // Use Flask server IP

        val instance: InstApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(InstApiService::class.java)
        }
    }


    private fun initButtons(){
        binding.convertButton.quickBaseButtonBlack().setTextValue("Convert")
        binding.wakeButton.quickBaseButtonBlack().setTextValue("Check Server")
        binding.clearButton.quickBaseButtonBlack().setTextValue("Clear Text")

        binding.checkServerItemsContainer.quickBaseButtonWhite().setCustomButtonBackground(R.drawable.bg_general_buttons_white_with_stroke)
    }

    private fun observeViewModel() {
        viewModel.videoUrl.observe(viewLifecycleOwner) { videoUrl ->
            videoUrl?.let { showPopup(it) }
        }
    }

    private fun disableButtons(){
        binding.wakeButton.disableButton()
        binding.convertButton.disableButton()
        binding.clearButton.disableButton()
    }

    private fun enableButtons(){
        binding.wakeButton.enableButton()
        binding.convertButton.enableButton()
        binding.clearButton.enableButton()
    }

    private fun serverDisabled(){
        binding.checkServerText.text = "Offline, try again."
        binding.checkServerIcon.setBackgroundResource(R.drawable.ic_dot_red)
    }

    private fun serverEnabled(){
        binding.checkServerText.text = "Online"
        binding.checkServerIcon.setBackgroundResource(R.drawable.ic_dot_green)
    }

    private fun viewProgress(){
        binding.progressBar.visibility = View.VISIBLE
        binding.progressText.visibility = View.VISIBLE
        binding.blurLayout.visibility = View.VISIBLE
    }

    private fun goneProgress(){
        binding.progressBar.visibility = View.GONE
        binding.progressText.visibility = View.GONE
        binding.blurLayout.visibility = View.GONE
    }


    private fun wakeServer() {
        val url = "https://charm-chemical-banjo.glitch.me/"
        val request = Request.Builder().url(url).build()

        viewProgress()
        disableButtons()

        OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    /*goneProgress()
                    serverDisabled()
                    enableButtons()
                    binding.urlInput.isEnabled = true*/

                    wakeServer()
                }
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        goneProgress()
                        serverEnabled()
                        enableButtons()
                        binding.urlInput.isEnabled = true
                    }
                }
            }
        })

    }


    private fun showPopup(videoUrl: String) {
        val options = arrayOf("Watch", "Download")
        AlertDialog.Builder(requireContext())
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
                    1 -> downloadVideo(videoUrl)
                }
            }
            .create()
            .show()
    }

    private fun downloadVideo(videoUrl: String) {
        val randomNumber = (1000..9999).random()
        val fileName = "inst_download_$randomNumber"

        val request = DownloadManager.Request(Uri.parse(videoUrl))
        request.setTitle("Video Downloading...")
        request.setDescription("Downloading $fileName.mp4...")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName.mp4")

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(context, "Download Started", Toast.LENGTH_SHORT).show()
    }


}
