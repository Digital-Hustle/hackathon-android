package com.example.profile.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.ApiResponse
import com.example.profile.data.mapper.toReports
import com.example.profile.domain.repository.ProfileRepository
import com.example.profile.presentation.ProfileContract
import com.example.profile.presentation.model.HistoryReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class HistoryPagingSource(
    private val repository: ProfileRepository,
    private val onEventSent: (event: ProfileContract.Event) -> Unit
) : PagingSource<Int, HistoryReport>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryReport> {
        val page = params.key ?: 0

        return try {
            val response = repository.getHistory(page, params.loadSize)
                .filter { it !is ApiResponse.Loading }
                .first()
            when (response) {
                is ApiResponse.Success -> {
                    var history: List<HistoryReport>

                    withContext(Dispatchers.IO) {
                        history = response.data.data.toReports()

                    }
                    onEventSent(
                        if (history.isEmpty() && page == 0) {
                            ProfileContract.Event.OnEmptyDataLoaded
                        } else {
                            ProfileContract.Event.OnDataLoaded
                        }
                    )


                    delay(500)

                    LoadResult.Page(
                        data = history,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (history.isEmpty()) null else page + 1
                    )
                }

                is ApiResponse.Failure -> {
                    onEventSent(ProfileContract.Event.OnError(response.errorMessage))
                    LoadResult.Error(Exception(response.errorMessage))
                }

                is ApiResponse.Loading -> {
                    onEventSent(ProfileContract.Event.OnLoading)
                    LoadResult.Page(emptyList(), null, null)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HistoryReport>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}