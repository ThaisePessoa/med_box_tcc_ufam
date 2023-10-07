package com.ufam.thaise.medbox

import com.ufam.thaise.medbox.model.banco.DadosDao
import com.ufam.thaise.medbox.repository.MedBoxRepository
import com.ufam.thaise.medbox.repository.MedBoxRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {
    @Provides
    fun provideMedBoxRepository(dadosDao: DadosDao): MedBoxRepositoryInterface {
        return MedBoxRepository(dadosDao)
    }
}

