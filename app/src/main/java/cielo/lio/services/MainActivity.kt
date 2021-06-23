package cielo.lio.services

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity(), MqttCallback, IMqttActionListener {

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
}