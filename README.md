# MvvmSdkLibrary

Sample of an sdk library module imported in an android application

Sdk dependencies used:
----------------------
- kotlin coroutines
- constraint layout
- retrofit (for htttp remote call
- glide for images loading
- mockito for unit tests

Sdk integration:
----------------
- after cloning the repository create your own mobile application with Android Studio
- then click Menu -> New > Import module
- select the poke directory and click next
- leave the fields as is and click open
- now the module is imported in your app
- add the following line to your build.gradle file of the app module: 
  implementation project(path: ':poke')
- after sync build.gradle file you're ready to use the sdk

Because of sdk is "kotlin coroutine friendly", you have to use kotlin coroutines.

Usage:
To get the Poke Interface:
val myService = PokeServiceFactory.createPokeService()

Methods:

To get the shakesperian description:
myService.getPokemonShakespeareanDescription(name)

To get the pokemon image:
myService.getPokemonSpritesUrls(pokemonName: String)

Example of usage:

val pokeService = PokeServiceFactory.createPokeService()
lifecycleScope.launch {
  withContext(Dispatchers.IO) {
    val result = pokeService.getPokemonShakespeareanDescription("ivysaur")
  }
}
        
ShakespeareView integration
----------------------------
Sample of usage:
<com.shakespeare.poke.sdk.widget.ShakespeareView
        android:id="@+id/detail"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:showLoading="true"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"/>
        
Custom attributes:
showLoading: if set to true a progressbar has shown
description: set the shakespearean translation
image: load an image




