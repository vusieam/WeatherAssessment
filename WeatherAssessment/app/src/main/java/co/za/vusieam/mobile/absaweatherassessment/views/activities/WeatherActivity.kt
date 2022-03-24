package co.za.vusieam.mobile.absaweatherassessment.views.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.za.vusieam.mobile.absaweatherassessment.R
import co.za.vusieam.mobile.absaweatherassessment.databinding.ActivityWeatherBinding
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.BasicHelperFunctions
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.MessageDialog
import co.za.vusieam.mobile.absaweatherassessment.models.internal.Countries
import co.za.vusieam.mobile.absaweatherassessment.views.fragments.LandingFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.OnDialogClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ActivityWeatherBinding
    private val landingFragment = LandingFragment()



    //region Injected properties
    @Inject
    lateinit var analytics: FirebaseAnalytics
    @Inject
    lateinit var crashlytics: FirebaseCrashlytics
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeUIComponents()
    }


    //region Private functions

    /** Initialise the ui components **/
    private fun initializeUIComponents() {
        try{
            binding.contentContainer.buttonExit.setOnClickListener(this)
            changeFragment(landingFragment)

        }
        catch (ex:Exception){
            crashlytics.recordException(ex)
        }
    }


    /** Changing fragment and displaying them on the main activity frameLayout(container) component **/
    private fun changeFragment(fragment:Fragment):Unit{
        if(fragment != null){
            supportFragmentManager.beginTransaction().apply {
                replace(binding.contentContainer.container.id, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }


    //endregion


    //region Override functions

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) {
            BasicHelperFunctions.hideSystemControlButton(this)
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            binding.contentContainer.buttonExit.id ->{
                val dialog = MessageDialog.customWarningToasterDialog(
                    context = this,
                    title = BasicHelperFunctions.getAppName(),
                    message = "You are about to close the application."
                )
                dialog.setCancelable(false)
                dialog.setOnClickListener(object: OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                        finishAffinity()
                    }
                })
                dialog.show()
            }
        }
    }

    //endregion


}