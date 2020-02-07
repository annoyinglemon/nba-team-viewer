package capatan.kurt.nbateamviewer

import capatan.kurt.nbateamviewer.core.BaseNbaTeamViewerApp
import capatan.kurt.nbateamviewer.datasource.TeamService
import capatan.kurt.nbateamviewer.repository.TeamRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NbaTeamViewerApp: BaseNbaTeamViewerApp() {

    override fun initializeTeamRepository(): TeamRepository = TeamRepository(createTeamService())

    private fun createTeamService(): TeamService {
        val retrofit = createRetrofit()
        return retrofit.create(TeamService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        val okHttpClient = createOkHttpClient()

        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.ENDPOINT_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB cache
        val cache = Cache(cacheDir, cacheSize)

        return OkHttpClient
            .Builder()
            .cache(cache)
            .build()
    }

}