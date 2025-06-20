import di.TestDependencyInjector
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.usecase.PopularMoviesUseCase
import edu.dyds.movies.presentation.home.HomeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

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

    private val fakeUseCase = object : PopularMoviesUseCase {
        override suspend fun execute(): List<QualifiedMovie> {
            yield()
            return listOf(
                QualifiedMovie(
                    movie = TestDependencyInjector.getTestMovie(),
                    isGoodMovie = true
                )
            )
        }
    }

    @Test
    fun `getAllMovies should emit loading and then data state`() = runTest {
        // Arrange
        val homeViewModel = HomeViewModel(fakeUseCase)
        val emittedStates = mutableListOf<HomeViewModel.MoviesUiState>()
        val initialValue = false
        val loadingValue = true
        val finishedLoadingValue = false


        testScope.launch {
            homeViewModel.moviesStateFlow.collect { state ->
                emittedStates.add(state)
            }
        }

        // Act
        homeViewModel.getAllMovies()

        // Assert
        assertEquals(initialValue, emittedStates[0].isLoading)
        assertEquals(loadingValue, emittedStates[1].isLoading)
        assertEquals(finishedLoadingValue, emittedStates[2].isLoading)
        assertEquals(TestDependencyInjector.getTestMovie(), emittedStates[2].movies.first().movie)
    }
}