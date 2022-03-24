package co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions

import android.app.Activity
import android.view.Gravity
import com.thecode.aestheticdialogs.*

object MessageDialog {

    /**
     * Message or popup dialog that can be used to display success messages.
     * The onclick event wil be implemented on the activity side to allow user to handle it.
     **/
    fun customSuccessToasterDialog(context: Activity, title:String, message:String): AestheticDialog.Builder{
        return AestheticDialog.Builder(context, DialogStyle.TOASTER, DialogType.SUCCESS)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setDarkMode(true)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.ZOOM)
    }


    /**
     * Message or popup dialog that can be used to display warning messages.
     * The onclick event wil be implemented on the activity side to allow user to handle it.
     **/
    fun customWarningToasterDialog(context: Activity, title:String, message:String): AestheticDialog.Builder{
        return AestheticDialog.Builder(context, DialogStyle.TOASTER, DialogType.WARNING)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setDarkMode(true)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.ZOOM)
    }



    /** Message or popup dialog that can be used to display success messages **/
    fun successToasterDialog(context: Activity, title:String, message:String){
        AestheticDialog.Builder(context, DialogStyle.TOASTER, DialogType.SUCCESS)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setDarkMode(true)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.ZOOM)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                }
            })
            .show()
    }



    /** Message or popup dialog that can be used to display error or failure messages **/
    fun errorToasterDialog(context: Activity, title:String, message:String){
        AestheticDialog.Builder(context, DialogStyle.TOASTER, DialogType.ERROR)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setDarkMode(true)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.ZOOM)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                }
            })
            .show()
    }



    /** Message or popup dialog that can be used to display warning messages **/
    fun warningToasterDialog(context: Activity, title:String, message:String){
        AestheticDialog.Builder(context, DialogStyle.TOASTER, DialogType.WARNING)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setDarkMode(true)
            .setGravity(Gravity.CENTER)
            .setAnimation(DialogAnimation.ZOOM)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                }
            })
            .show()
    }

}