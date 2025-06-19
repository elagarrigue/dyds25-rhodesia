import di.TestDependencyInjector
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.usecase.MovieDetailsUseCase
import edu.dyds.movies.presentation.detail.DetailViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = CoroutineScope(testDispatcher)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val fakeUseCase = object : MovieDetailsUseCase {
        override suspend fun execute(id: Int): Movie {
            yield()
            return TestDependencyInjector.getTestMovie()
        }
    }

    @Test
    fun `getMovieDetail should emit loading and then data state`() = runTest {
        // Arrange
        val detailViewModel = DetailViewModel(fakeUseCase)
        val emittedStates = mutableListOf<DetailViewModel.MovieDetailUiState>()
        val initialValue = false
        val loadingValue = true
        val finishedLoadingValue = false

        testScope.launch {
            detailViewModel.movieDetailStateFlow.collect {
                    state -> emittedStates.add(state)
            }
        }

        // Act
        detailViewModel.getMovieDetail(1)

        // Assert
        assertEquals(initialValue, emittedStates[0].isLoading)
        assertEquals(loadingValue, emittedStates[1].isLoading)
        assertEquals(finishedLoadingValue, emittedStates[2].isLoading)
        assertEquals(TestDependencyInjector.getTestMovie(), emittedStates[2].movie)
    }
}