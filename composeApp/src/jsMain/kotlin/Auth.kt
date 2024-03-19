@file:JsExport

import tim.huang.genlayout.auth.firebaseAuth
fun handleCredentialResponse(response: dynamic){
    val clientId: String = response.clientId as String
    val credential: String = response.credential as String

    firebaseAuth(clientId, credential)
}
