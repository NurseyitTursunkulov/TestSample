package com.example.testrussia

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.testrussia.data.DefaultNewsRepository
import com.example.testrussia.data.NewsRepository
import com.example.testrussia.data.source.local.LocalDataSource
import com.example.testrussia.data.source.local.NewsDao
import com.example.testrussia.data.source.local.NewsDataBase
import com.example.testrussia.data.source.remote.NewsServiceApi
import com.example.testrussia.data.source.remote.RemoteDataSource
import com.example.testrussia.news.NewsViewModel
import com.example.testrussia.news.model.NewsModel
import com.example.testrussia.news.model.Title
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {

    // Subject under test
    private lateinit var newsViewModel: NewsViewModel
    // Use a fake repository to be injected into the viewmodel
    private lateinit var newsRepository: FakeRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        // We initialise the tasks to 3, with one active and two completed
        newsRepository = FakeRepository()
        val newsModel = NewsModel(title = Title("Title1"),id = 1)
        val newsModel1 = NewsModel(title = Title("Title1"),id = 2)
        val newsModel2 = NewsModel(title = Title("Title1"),id = 3)
        val newsModel3 = NewsModel(title = Title("Title1"),id = 4)
        newsRepository.addNews(newsModel, newsModel1, newsModel2, newsModel3)

        newsViewModel = NewsViewModel(
            newsRepository
        )
    }

    @Test
    fun loadAllNewsFromRepository_loadingTogglesAndDataLoaded() = runBlocking<Unit> {
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()


        // Trigger loading of tasks
        newsViewModel.loadNews(true)

        // Then progress indicator is shown
        Truth.assertThat(LiveDataTestUtil.getValue(newsViewModel.dataLoading)).isTrue()

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        Truth.assertThat(LiveDataTestUtil.getValue(newsViewModel.dataLoading)).isFalse()

        // And data correctly loaded
        Truth.assertThat(LiveDataTestUtil.getValue(newsViewModel.newsLiveList)).hasSize(4)
    }

    @Test
    fun clickOnOpenNewsDetail_setsEvent() {

        val newsModel = NewsModel(title = Title("Title1"), id = 1)
        newsViewModel.openDetails(newsModel)

        // Then the event is triggered
        assertLiveDataEventTriggered(newsViewModel.openDetailsEvent, newsModel)
    }

    class MainScreen : Screen<MainScreen>() {
        val recycler: KRecyclerView = KRecyclerView({ withId(R.id.news_list) }, itemTypeBuilder = {
            itemType(::MainItem)
        })
    }

    class MainItem(parent: Matcher<View>) : KRecyclerItem<MainItem>(parent) {
        val title: KTextView = KTextView(parent) { withId(R.id.title) }
    }
}