@file:JsModule("firebase/app")

package tim.huang.genlayout.config


external val firebaseModule: JsAny
external interface FirebaseOptions : JsAny {
    val apiKey: String
    val authDomain: String
    val projectId: String
    val storageBucket: String
    val messagingSenderId: String
    val appId: String
    val measurementId: String
    val databaseURL: String?
}

external interface FirebaseAppSettings{
//    var setting: String  // needed settings
}

external interface FirebaseApp{
}



//val initializeApp: (options: FirebaseOptions, config: FirebaseAppSettings) -> FirebaseApp = firebaseModule.toJsReference().initializeApp
external fun initializeApp(options: FirebaseOptions, config: FirebaseAppSettings?): FirebaseApp

external fun initializeApp(options: FirebaseOptions): FirebaseApp

external fun initializeApp(): FirebaseApp



