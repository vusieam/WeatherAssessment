package co.za.vusieam.mobile.absaweatherassessment.models.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GoogleServicesModule {

    @Singleton
    @Provides
    fun firebaseAnalyticsInstance(): FirebaseAnalytics {
        return Firebase.analytics
    }

    @Singleton
    @Provides
    fun firebaseCrashlyticsInstance(): FirebaseCrashlytics {
        return Firebase.crashlytics
    }

}