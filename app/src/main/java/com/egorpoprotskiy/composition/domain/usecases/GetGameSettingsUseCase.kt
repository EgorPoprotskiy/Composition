package com.egorpoprotskiy.composition.domain.usecases

import com.egorpoprotskiy.composition.domain.GameRepository
import com.egorpoprotskiy.composition.domain.entity.GameSettings
import com.egorpoprotskiy.composition.domain.entity.Level

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}