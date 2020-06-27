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
