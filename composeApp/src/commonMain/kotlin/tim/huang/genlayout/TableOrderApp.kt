package tim.huang.genlayout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import genlayout.composeapp.generated.resources.Res
import genlayout.composeapp.generated.resources.baseline_add
import genlayout.composeapp.generated.resources.baseline_add_circle_outline
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import tim.huang.genlayout.data.Dish
import tim.huang.genlayout.data.provideData

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TableOrderApp() {
    val dishes = provideData()

    MenuDraftScreen(dishes)
}

@Composable
fun MenuDraftScreen(dishes: List<Dish>) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(dishes.size) { index ->
            DishItem(dishes[index])
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DishItem(dish: Dish) {

    Row() {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(100.dp).padding(16.dp),
            border = BorderStroke(1.dp, Color.Black)
        ){
            Image(
                painterResource(Res.drawable.baseline_add_circle_outline),
                null
            )
        }

        Column(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
            Text(dish.name)
            Text(dish.description)
            Text(dish.price.toString())
        }

        Image(
            painterResource(Res.drawable.baseline_add),
            null
        )

    }


}
