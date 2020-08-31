package id.fxlib.crawler.febriarianto_axa

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.fxlib.crawler.febriarianto_axa.model.UserContent
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.text.DecimalFormat
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MainViewModel : ViewModel() {

    val TAG = "MainViewModel"
    val tabCounter = MutableLiveData<Int>()
    val data       = MutableLiveData<ArrayList<UserContent>>()
    val dataList   = ArrayList<UserContent>()

    private fun initClient() : OkHttpClient {
        val builder = OkHttpClient.Builder()
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            }
        )
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        builder.sslSocketFactory(sslContext.socketFactory)
        return builder.build()

    }

    fun fetch() {
        var client = initClient()
        client.newCall(
            Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .get().build())
            .enqueue(object : Callback {

                override fun onFailure(call: Call?, e: IOException?) {
                    Log.e(TAG, e?.message, e)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    response?.body()?.string()?.let {
                        Log.d(TAG, "onResponse>> $it")
                        var array = JSONArray(it)
                        for (i in 0 until array.length()) {
                            val item   = array.getJSONObject(i)
                            var uc = UserContent()
                            uc.id = item.getString("id")
                            uc.userId = item.getString("userId")
                            uc.title = item.getString("title")
                            uc.body = item.getString("body")
                            dataList.add(uc)
                        }
                        fetchTab(0)
                    }
                }
            })

    }

    fun fetchTab(index:Int) {
        var list = ArrayList<UserContent>()
        for (i in index * 10 until (index * 10) + 10) {
            list.add(dataList[i])
        }
        data.postValue(list)
    }
}