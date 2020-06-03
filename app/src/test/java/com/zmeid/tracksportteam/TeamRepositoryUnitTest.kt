package com.zmeid.tracksportteam

import com.zmeid.tracksportteam.model.Team
import com.zmeid.tracksportteam.model.TeamEventHistory
import com.zmeid.tracksportteam.model.TeamEventHistoryResponseModel
import com.zmeid.tracksportteam.model.TeamsResponseModel
import com.zmeid.tracksportteam.repository.TeamRepository
import com.zmeid.tracksportteam.repository.webservice.TrackSportTeamService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Contains unit tests for [TeamRepository].
 */
@RunWith(MockitoJUnitRunner::class)
class TeamRepositoryUnitTest {

    @Mock
    lateinit var teamList: List<Team>

    @Mock
    lateinit var teamEventHistoryList: List<TeamEventHistory>

    @Mock
    lateinit var trackSportTeamService: TrackSportTeamService

    lateinit var teamRepository: TeamRepository
    private val team = "TestTeam"
    private val teamId = 9999

    /**
     * Initialize mocking.
     */
    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        teamRepository = TeamRepository(trackSportTeamService)
    }

    @Test
    fun searchTeamTest() {
        runBlocking {
            Mockito.`when`(trackSportTeamService.searchTeam(team))
                .thenReturn(TeamsResponseModel(teamList))

            assertEquals(teamRepository.searchTeam(team), teamList)
        }
    }

    @Test
    fun getTeamEventHistoryTest() {
        runBlocking {
            Mockito.`when`(trackSportTeamService.getTeamEventHistory(teamId))
                .thenReturn(TeamEventHistoryResponseModel(teamEventHistoryList))

            assertEquals(teamRepository.getTeamEventHistory(teamId), teamEventHistoryList)
        }
    }
}