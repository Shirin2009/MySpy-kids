package com.example.MySpy.ui.settings.videomanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MySpy.R
import kotlinx.android.synthetic.main.fragment_settings_video_manager.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoManagerFragment : Fragment() {

    private val vm: ViewManagerViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_settings_video_manager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setting_titles.text = "Video Manager"

        vm.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }
        })

        video_list.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        video_list.setHasFixedSize(true)

        video_list.adapter = EditVideoListAdapter(activity!!) { video ->
            vm.deleteFile(video)
        }

        vm.videos.observe(this, Observer { videos ->
            if (videos.isEmpty()) {
                no_videos.visibility = View.VISIBLE
            } else {
                no_videos.visibility = View.GONE
            }
            (video_list.adapter as EditVideoListAdapter).setVideoElements(videos)
        })

    }

    override fun onResume() {
        super.onResume()
        vm.reloadVideos()
    }
}
