package gaur.himanshu.search.screens.recipe_list

import android.text.Editable
import android.text.TextWatcher
import gaur.himanshu.common.utils.NetworkResult
import gaur.himanshu.common.utils.UiText
import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.model.RecipeDetails
import gaur.himanshu.search.domain.use_cases.GetAllRecipeUseCase
import gaur.himanshu.search.screens.details.MainDispatcherRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RecipeListViewModelTest {
    val useCase: GetAllRecipeUseCase = mock()

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun test_success() = runTest {
        `when`(useCase.invoke("chicken")).thenReturn(flowOf(NetworkResult.Success(getRecipeResponse())))
        val viewmodel = RecipeListViewModel(useCase)
        viewmodel.onEvent(RecipeList.Event.SearchRecipe("chicken"))
        assertEquals(getRecipeResponse(), viewmodel.uiState.value.data)
    }

    @Test
    fun test_failed() = runTest {
        `when`(useCase.invoke("chicken")).thenReturn(flowOf(NetworkResult.Error("error")))
        val viewModel = RecipeListViewModel(useCase)
        viewModel.onEvent(RecipeList.Event.SearchRecipe("chicken"))
        assertEquals(UiText.RemoteString("error"), viewModel.uiState.value.error)
    }

    @Test
    fun test_navigate_recipe_details() = runTest {
        val viewModel = RecipeListViewModel(useCase)
        viewModel.onEvent(RecipeList.Event.GoToRecipeDetails("chicken"))
        val list = mutableListOf<RecipeList.Navigation>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.navigation.collectLatest {
                list.add(it)
            }

        }

        assert(list.first() is RecipeList.Navigation.GoToRecipeDetails)
    }

    @Test
    fun test_insert()= runTest {

    }

//    @get:Rule(order = 1)
//    val mainDispatcherRule = MainDispatcherRule()
//
//    @Before
//    fun before(){
//
//    }
//
//    @After
//    fun after(){
//
//    }
//
//
//    private val getAllRecipeUseCase: GetAllRecipeUseCase = mock()
//
//    @Test
//    fun test_success() = runTest {
//        `when`(getAllRecipeUseCase.invoke("chicken"))
//            .thenReturn(flowOf(NetworkResult.Success(data = getRecipeResponse())))
//        val viewModel = RecipeListViewModel(getAllRecipeUseCase)
//        viewModel.onEvent(RecipeList.Event.SearchRecipe("chicken"))
//        assertEquals(getRecipeResponse(), viewModel.uiState.value.data)
//    }
//
//    @Test
//    fun test_failed() = runTest {
//        `when`(getAllRecipeUseCase.invoke("chicken"))
//            .thenReturn(flowOf(NetworkResult.Error(message = "error")))
//        val viewModel = RecipeListViewModel(getAllRecipeUseCase)
//        viewModel.onEvent(RecipeList.Event.SearchRecipe("chicken"))
//        assertEquals(UiText.RemoteString("error"),viewModel.uiState.value.error)
//
//    }
//
//
//    @Test
//    fun test_navigate_recipe_details() = runTest {
//        val viewModel = RecipeListViewModel(getAllRecipeUseCase)
//        viewModel.onEvent(RecipeList.Event.GoToRecipeDetails("id"))
//        val list = mutableListOf<RecipeList.Navigation>()
//        backgroundScope.launch(UnconfinedTestDispatcher()) {
//            viewModel.navigation.collectLatest {
//                list.add(it)
//            }
//        }
//        assert(list.first() is RecipeList.Navigation.GoToRecipeDetails)
//
//    }
//
//    @Test
//    fun test_navigate_to_favorite_screen()= runTest {
//        val viewModel = RecipeListViewModel(getAllRecipeUseCase)
//        viewModel.onEvent(RecipeList.Event.FavoriteScreen)
//        val list = mutableListOf<RecipeList.Navigation>()
//        backgroundScope.launch (UnconfinedTestDispatcher()){
//            viewModel.navigation.collectLatest {
//                list.add(it)
//            }
//        }
//        assert(list.first() is RecipeList.Navigation.GoToFavoriteScreen)
//
//    }
//
//
//}
//
//class MainDispatcherRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
//    TestWatcher() {
//    override fun starting(description: Description?) {
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    override fun finished(description: Description?) {
//        Dispatchers.resetMain()
//    }
//
//}

    class MainDispatcherRule(val dispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
        TestWatcher() {

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun starting(description: Description?) {
            Dispatchers.setMain(dispatcher)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun finished(description: Description?) {
            Dispatchers.resetMain()
        }

    }


    private fun getRecipeResponse(): List<Recipe> {
        return listOf(
            Recipe(
                idMeal = "idMeal",
                strArea = "India",
                strCategory = "category",
                strYoutube = "strYoutube",
                strTags = "tag1,tag2",
                strMeal = "Chicken",
                strMealThumb = "strMealThumb",
                strInstructions = "12",
            ),
            Recipe(
                idMeal = "idMeal",
                strArea = "India",
                strCategory = "category",
                strYoutube = "strYoutube",
                strTags = "tag1,tag2",
                strMeal = "Chicken",
                strMealThumb = "strMealThumb",
                strInstructions = "123",
            )
        )

    }

    private fun getRecipeDetails(): RecipeDetails {
        return RecipeDetails(
            idMeal = "idMeal",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag1,tag2",
            strMeal = "Chicken",
            strMealThumb = "strMealThumb",
            strInstructions = "strInstructions",
            ingredientsPair = listOf(Pair("Ingredients", "Measure"))
        )
    }
}


