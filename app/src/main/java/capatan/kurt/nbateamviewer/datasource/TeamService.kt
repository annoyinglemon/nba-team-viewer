package capatan.kurt.nbateamviewer.datasource

import retrofit2.http.GET

interface TeamService {

    @GET("input.json")
    suspend fun getTeams(): List<Team>

}