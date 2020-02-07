package capatan.kurt.nbateamviewer.core

import android.app.Application
import capatan.kurt.nbateamviewer.repository.TeamRepository

abstract class BaseNbaTeamViewerApp: Application() {

    val teamRepository: TeamRepository by lazy {
        initializeTeamRepository()
    }

    protected abstract fun initializeTeamRepository(): TeamRepository

}