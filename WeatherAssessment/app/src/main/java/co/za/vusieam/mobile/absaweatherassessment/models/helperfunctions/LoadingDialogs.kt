package co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions

import android.content.Context
import co.za.vusieam.mobile.absaweatherassessment.views.BaseDialogLoader

open class LoadingDialogs {

    companion object {
        private var loader : BaseDialogLoader? = null
        fun showDialog(context: Context?, isCancellable: Boolean) {
            hideDialog()
            if(context != null){
                try {
                    loader = BaseDialogLoader(context)
                    loader?.let { loader ->
                        loader.setCanceledOnTouchOutside(true)
                        loader.setCancelable(isCancellable)
                        loader.show()
                    }
                }
                catch(exc: Exception){
                    exc.printStackTrace()
                }
            }
        }

        fun hideDialog() {
            if (loader !=null && loader?.isShowing!!) {
                loader = try {
                    loader?.dismiss()
                    null
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

}