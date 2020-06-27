# NSDKelper
[Android Library] An Network Service Discovery Helper library written in Kotlin

[WIP] rewriting and modernizing the library from Rafakob https://github.com/rafakob/NsdHelper 


# Download

Using Gradle: 

1.Add this in your root build.gradle at the end of repositories

    allprojects {
        repositories {
          ...
          maven { url 'https://jitpack.io' }
        }
      }

2.Add the dependancy

    dependencies {
              implementation 'com.github.Nikola-Milovic:NSDKelper:Tag'
      }
      
    
Or using Maven:

1.Add the JitPack repository to your build file

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
      </repositories>
 2.Add the dependancy 
 
 	<dependency>
	    <groupId>com.github.Nikola-Milovic</groupId>
	    <artifactId>NSDKelper</artifactId>
	    <version>Tag</version>
	</dependency> 
  
  
 # Why use NSDKelper?
 
This:

	private val registrationListener = object : NsdManager.RegistrationListener {

	    override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
		// Save the service name. Android may have changed it in order to
		// resolve a conflict, so update the name you initially requested
		// with the name Android actually used.
		mServiceName = NsdServiceInfo.serviceName
	    }

	    override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
		// Registration failed! Put debugging code here to determine why.
	    }

	    override fun onServiceUnregistered(arg0: NsdServiceInfo) {
		// Service has been unregistered. This only happens when you call
		// NsdManager.unregisterService() and pass in this listener.
	    }

	    override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
		// Unregistration failed. Put debugging code here to determine why.
	    }
	}

 Becomes this 

	nsdKelper = NsdKelper(this)

And to register a service you just have to

    nsdKelper.registerService(serviceName,
                serviceType,
                80,
                success = { nsdServiceInfo ->
                   // Do something with the service
                },
                failure =
                { exception ->
                   // Do something with the exception 
                }
            )
 
 
# How do I use NSDKelper?

Creating the NSDKelper object

        nsdKelper = NsdKelper(this) //Create the Kelper only using the context

        nsdKelper.loggingEnabled = true // Enable logging for easier debugging


Registering a service

     nsdKelper.registerService(serviceName,
                serviceType,
                80,
                success = { nsdServiceInfo ->
                   // Do something with the service
                },
                failure =
                { exception ->
                   // Do something with the exception 
                }
            )
            
Start service discovery


        nsdKelper.startServiceDiscovery("_http._tcp", "name",
                serviceFound =
                { service: NsdServiceInfo? ->
                   // Do something with the found service
                },
                serviceLost = { NsdServiceInfo ->
                   // Do something when the service is lost
                },
                failure =
                { exception ->
                  // Do something when it fails
                })

Resolve a service

        nsdKelper.resolveService(service,
           success =  { serviceInfo->
                // Do something with the resolved service
            },
            failure = {exception ->
                // Do something with the exception
            }
            )
