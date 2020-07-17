package com.nikolam.nsdkelper

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nikolam.nsdkelper.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var nsdKelper: NsdKelper

    private lateinit var viewDataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        nsdKelper = NsdKelper(this)

        nsdKelper.loggingEnabled = true

        viewDataBinding.buttonRegister.setOnClickListener {
            val serviceName: String = text_servicename.text.toString()
            val serviceType : String = text_servicetype.text.toString()

            nsdKelper.registerService(serviceName,
                serviceType,
                80,
                success = {
                    Log.d("TAG", "Service registered---> " + it.toString())
                },
                failure =
                {
                    Log.d("TAG", "Exception is thrown register---> " + it.toString())
                }
            )
        }

        viewDataBinding.buttonDiscover.setOnClickListener {
            val serviceName: String = text_servicename.text.toString()
            val serviceType : String = text_servicename.text.toString()
            val lookfor : String = text_lookingforName.text.toString()
            nsdKelper.startServiceDiscovery("_http._tcp", "name",
                serviceFound =
                { service: NsdServiceInfo? ->
                    //if(service?.serviceName == lookfor) nsdKelper.resolveService(service)
                   // else Log.d("TAG", "Idk + $service")
                    nsdKelper.resolveService(service!!, {
                        Log.d("TAG", "Service resolved ---> $it")
                    },
                        {
                            Log.d("TAG", "Service resolve failed ---> $it")
                        })
                },
                serviceLost = {
                    Log.d("TAG", "Service lost ---> " + it.toString())
                },
                failure =
                { Log.d("TAG", "Discovery failure ---> $it") })
        }
    }


    override fun onDestroy() {




        nsdKelper.unRegisterService(success = {
            Log.d("TAG", "Service unregistered---> " + it.toString())
        },
            failure = {
                Log.d("TAG", "Exception is thrown unregister---> " + it.toString())
            })

        nsdKelper.stopServiceDiscovery()

        super.onDestroy()
    }
}
