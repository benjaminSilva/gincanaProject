package com.bsoftwares.benjamin.ideia01

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application(){

    override fun onCreate() {
        super.onCreate()

        // Prepare Realm
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
    }
}
