package cielo.lio.services

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MainActivity : AppCompatActivity(), MqttCallback, IMqttActionListener {

    private val CHANNEL_ID = "LIO"
    private val tag = "MQTTClient"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupClient(applicationContext)
    }

    private fun setupClient(context: Context): MqttAndroidClient {
        val client = MqttAndroidClient(context, "tcp://192.168.0.12:1883", "lio-v2-do-felipe")
        client.setCallback(this)
        val options = MqttConnectOptions().apply {
            userName = "guest"
            password = "guest".toCharArray()
        }
        client.connect(options, null, this)
        return client
    }

    override fun connectionLost(cause: Throwable?) {
        Log.w("Ih a conexao de ruim", cause)
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        Log.i(tag, "ih chegou mensagem: topico: $topic, texto: $message")
        Toast.makeText(this, "Ih, chegou mensagem: topico: $topic, texto: $message", Toast.LENGTH_SHORT).show()
        notification(topic,"$message")
    }


    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        Log.i(tag, "Delivery complete")
    }

    override fun onSuccess(token: IMqttToken?) {
        Log.i(tag, "Connection succeeded")
        token?.client?.subscribe("lio/inbox", 1)
    }

    override fun onFailure(token: IMqttToken?, exception: Throwable?) {
        Log.w(tag, "Connection failed", exception)
    }



    fun notification(topic:String?, message:String?) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(topic)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(0, builder.build())
        }
    }
}