package com.example.MySpy.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.MySpy.BuildConfig
import com.example.MySpy.R
import com.example.MySpy.data.AppManager
import com.example.MySpy.model.settings.BaseMenuItem
import com.example.MySpy.model.settings.SettingsMenuItem
import com.example.MySpy.ui.settings.appmanager.AppManagerFragment
import com.example.MySpy.ui.settings.pin.PinFragment
import com.example.MySpy.ui.settings.stats.StatsFragment
import com.example.MySpy.ui.settings.videomanager.VideoManagerFragment
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject


class SettingsActivity : AppCompatActivity() {

    private val settingsItems = mutableListOf<BaseMenuItem>()

    val appManager: AppManager by inject()

    fun initMenu() {

        settingsItems.add(BaseMenuItem("App Settings"))
        settingsItems.add(
            SettingsMenuItem(
                "Video Manager", false,
                ContextCompat.getDrawable(this, R.drawable.ic_subscriptions_black_24dp),
                VideoManagerFragment()
            )
        )
        settingsItems.add(
            SettingsMenuItem(
                "App Manager", false,
                ContextCompat.getDrawable(this, R.drawable.ic_view_comfy_black_24dp),
                AppManagerFragment()
            )
        )
        settingsItems.add(
            SettingsMenuItem(
                "Stats", false,
                ContextCompat.getDrawable(this, R.drawable.ic_graphic_eq_black_24dp),
                StatsFragment()
            )
        )
        settingsItems.add(
            SettingsMenuItem(
                "Pin code", false,
                ContextCompat.getDrawable(this, R.drawable.ic_security_black_24dp),
                PinFragment()
            )
        )
        settingsItems.add(BaseMenuItem("Plugins/Download videos"))

        settingsItems.addAll(appManager.getPlugins().map { appManager.toSettingItem(it) })

        settingsItems.add(
            SettingsMenuItem(
                "Get plugins", false,
                ContextCompat.getDrawable(this, R.drawable.ic_get_app_black_24dp),
                WebFragment.getFragment("Plugins", "file:///android_asset/plugins.html")
            )
        )

        settingsItems.add(BaseMenuItem("Other"))
        settingsItems.add(
            SettingsMenuItem(
                "System settings", true,
                ContextCompat.getDrawable(this, R.drawable.ic_settings_24dp),
                null, Intent(android.provider.Settings.ACTION_SETTINGS)
            )
        )
        settingsItems.add(
            SettingsMenuItem(
                "About (v. ${BuildConfig.VERSION_NAME})", false,
                ContextCompat.getDrawable(this, R.drawable.ic_info_outline_black_24dp),
                WebFragment.getFragment("About", "file:///android_asset/about.html")
            )
        )


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initMenu()
        item_list.adapter = MenuAdapter(settingsItems,
            View.OnClickListener { v ->
                openFragment(v.tag as SettingsMenuItem)
            })
        openFragment(settingsItems[1] as SettingsMenuItem)
    }

    private fun openFragment(item: SettingsMenuItem) {
        item.fragment?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, it)
                .commit()
        }

        item.intent?.let {
            startActivity(it)
        }

    }


}
