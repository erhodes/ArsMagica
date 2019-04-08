package com.erhodes.arsmagica.dagger

import android.app.Application
import com.erhodes.arsmagica.model.CharacterRepository
import com.erhodes.arsmagica.view.CharacterFragment
import com.erhodes.arsmagica.view.MagicFragment
import com.erhodes.arsmagica.view.SpellFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class))
@Singleton
interface AppComponent {
    fun getCharacterRepo(): CharacterRepository;
    fun inject(application: Application)

    fun inject(fragment: CharacterFragment)
    fun inject(fragment: SpellFragment)
    fun inject(fragment: MagicFragment)
}