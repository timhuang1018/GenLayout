package tim.huang.genlayout.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.UserInfo
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


val coroutineScope = CoroutineScope(Job())
fun firebaseAuth(clientId: String, credential: String) {

    val options = FirebaseOptions(
        apiKey =  "AIzaSyAaYVjqeeWnyZR4E8trh8K9U7rwCFDLkIk",
        authDomain =  "table-order-gpt.firebaseapp.com",
        projectId =  "table-order-gpt",
        storageBucket =  "table-order-gpt.appspot.com",
        gcmSenderId =  "651817867687",
        applicationId =  "1:651817867687:web:3a5f79fe99b29f4b31be82",
        gaTrackingId =  "G-PELN18SW9F",
    )


    val app = Firebase.initialize(options = options, name = "table-order-gpt")
    val db = Firebase.firestore(app)
    val auth = Firebase.auth(app)


    coroutineScope.launch {

        val authCredential = GoogleAuthProvider.credential(idToken = credential, accessToken = null)
        val result = auth.signInWithCredential(authCredential)
    }

}

fun getGoogleId(userInfos: List<UserInfo>): String?{
    return userInfos.find { it.providerId == "google.com" }?.uid
}