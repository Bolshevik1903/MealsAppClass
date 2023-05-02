package pe.edu.upc.mealscompose.ui.screeens.meals

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pe.edu.upc.mealscompose.data.model.Meal
import pe.edu.upc.mealscompose.data.remote.ApiClient
import pe.edu.upc.mealscompose.data.remote.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Meals(modifier: Modifier = Modifier) {

    val meals = remember { mutableStateListOf<Meal>() }

    val mealService = ApiClient.getMealService()
    val fetchMealsByCategory = ApiClient.getMealService().fetchMealByCategory("seafood")

    fetchMealsByCategory.enqueue(object : Callback<MealResponse> {
        override fun onResponse(
            call: Call<MealResponse>,
            response: Response<MealResponse>
        ) {
            if (response.isSuccessful) {
                meals.addAll(response.body()!!.meals)
            }
        }

        override fun onFailure(call: Call<MealResponse>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })

    LazyColumn(modifier = modifier) {
        items(meals) {
            MealCard(it)
        }
    }
}

@Composable
fun MealCard(meal: Meal, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MealImage(meal)
            MealItem(meal)
        }
    }
}

@Composable
fun MealImage(meal: Meal, modifier: Modifier = Modifier) {
    AsyncImage(model = meal.url, contentDescription = null)
}

@Composable
fun MealItem(meal: Meal, modifier: Modifier = Modifier) {
    Text(text = meal.name)
}