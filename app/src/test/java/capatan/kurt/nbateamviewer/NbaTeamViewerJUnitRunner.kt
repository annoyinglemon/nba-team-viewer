package capatan.kurt.nbateamviewer

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class NbaTeamViewerJUnitRunner: AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestNbaTeamViewerApp::class.java.name, context)
    }

}