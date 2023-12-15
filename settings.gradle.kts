pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
            credentials {
                username = "prathamesh.kondaskar@gmail.com"
                password = "Patya007.pk"
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            credentials {
                username = "prathamesh.kondaskar@gmail.com"
                password = "Patya007.pk"
            }
        }
        maven { url=uri ("https://maven.google.com/")
        }
    }
}

rootProject.name = "SocietyApp"
include(":app")
