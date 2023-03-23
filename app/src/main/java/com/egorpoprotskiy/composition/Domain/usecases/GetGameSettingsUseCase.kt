package com.egorpoprotskiy.composition.Domain.usecases

import com.egorpoprotskiy.composition.Domain.GameRepository
import com.egorpoprotskiy.composition.Domain.entity.GameSettings
import com.egorpoprotskiy.composition.Domain.entity.Level

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}