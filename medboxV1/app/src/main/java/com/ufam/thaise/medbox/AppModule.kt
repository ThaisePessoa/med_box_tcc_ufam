package com.ufam.thaise.medbox

import android.app.Application
import androidx.room.Room
import com.ufam.thaise.medbox.model.banco.AppDatabase
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
    fun provideDatabase(application: Application): AppDatabase{
        return Room.databaseBuilder(application, AppDatabase::class.java,"med_box.db").fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideUserDao(database: AppDatabase): DadosDao{
        return database.medBoxDao()
    }
    @Provides
    fun provideMedBoxRepository(dadosDao: DadosDao): MedBoxRepositoryInterface {
        return MedBoxRepository(dadosDao)
    }
}

