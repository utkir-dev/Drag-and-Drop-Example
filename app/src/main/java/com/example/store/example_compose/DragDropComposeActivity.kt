package com.example.store.example_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.store.R
import com.example.store.ui.theme.StoreTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DragDropComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreTheme {
                var parentOffset by remember { mutableStateOf(Offset.Zero) }
                var currentOffset by remember { mutableStateOf(Offset.Zero) }
                var isDragging by remember { mutableStateOf(false) }
                var canScroll by remember { mutableStateOf(true) }
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val foodList = remember {
                        mutableStateListOf(
                            FoodItem(1, "Pizza", 20.0, R.drawable.food_pizza),
                            FoodItem(2, "French toast", 10.05, R.drawable.food_toast),
                            FoodItem(3, "Chocolate", 12.99, R.drawable.food_cake),

                            )
                    }
                    val listState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()
                    LongPressDraggable(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .onGloballyPositioned {
                                    val recRoot = it.boundsInRoot()
                                    val recWindow = it.boundsInWindow()
                                    Log.d("rectangle", "recRoot: $recRoot")
                                    Log.d("rectangle", "recWindow: $recWindow")
                                    val positionInParent: Offset = it.positionInParent()
                                    val positionInRoot: Offset = it.positionInRoot()
                                    val positionInWindow: Offset = it.positionInWindow()

                                    parentOffset = positionInRoot
                                },
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            items(items = foodList) { food ->
                                FoodItemCard(foodItem = food) { changingOffset, ondrag ->
                                    isDragging = ondrag
                                    currentOffset = parentOffset + changingOffset
                                    Log.d("offset", "currentOffset : $currentOffset")
                                }
                            }
                            if (isDragging) {
                                if ((currentOffset.x / 10).toInt() < -10) {
                                    coroutineScope.launch {
                                        val pos = listState.firstVisibleItemIndex
                                        Log.d("listState", "firstVisibleItemIndex : $pos")
                                        if (pos > 0 && canScroll) {
                                            canScroll = false
                                            listState.animateScrollToItem(pos - 1)
                                            delay(500)
                                            canScroll = true
                                        }
                                    }
                                } else if ((currentOffset.x / 10).toInt() > 40) {
                                    coroutineScope.launch {
                                        val pos1 = listState.firstVisibleItemIndex
                                        Log.d("listState", "firstVisibleItemIndex : $pos1")

                                        val pos = listState.layoutInfo.visibleItemsInfo.size + pos1
                                        val count = listState.layoutInfo.totalItemsCount
                                        if (count > pos && canScroll) {
                                            canScroll = false
                                            listState.animateScrollToItem(pos1 + 1)
                                            delay(500)
                                            canScroll = true
                                        }
                                    }
                                }
                            }
                        }
                        PersonListContainer(listState)
                    }

                }
            }
        }
    }
}

@Composable
fun BoxScope.PersonListContainer(listState: LazyListState) {
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
            )
            .padding(vertical = 10.dp)
            .align(Alignment.BottomCenter),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        items(items = persons, key = { it.hashCode() }) { person ->
            PersonCard(person)
        }
    }

}

@Composable
fun PersonCard(person: Person) {
    val foodItems = remember {
        mutableStateMapOf<Int, FoodItem>()
    }

    DropTarget<FoodItem>(
        modifier = Modifier
            .padding(6.dp)
            .width(width = 120.dp)
            .fillMaxHeight(0.8f)
    ) { isInBound, foodItem ->
        val bgColor = if (isInBound) {
            Color.Red
        } else {
            Color.White
        }

        foodItem?.let {
            if (isInBound)
                foodItems[foodItem.id] = foodItem
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    val recRoot = it.boundsInRoot()
                    val recWindow = it.boundsInWindow()
                    Log.d("rectangle", "PersonCard recRoot: $recRoot")
                    Log.d("rectangle", "PersonCard recWindow: $recWindow")
                }
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .background(
                    bgColor,
                    RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = person.profile), contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = person.name,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (foodItems.isNotEmpty()) {
                Text(
                    text = "$${foodItems.values.sumOf { it.price }}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "${foodItems.size} Items",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemCard(foodItem: FoodItem, changingOffset: (Offset, Boolean) -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .padding(8.dp)
            .onGloballyPositioned {
                val recRoot = it.boundsInRoot()
                val recWindow = it.boundsInWindow()
                Log.d("FoodItemCard", "FoodItemCard recRoot: $recRoot")
                Log.d("FoodItemCard", "FoodItemCard recWindow: $recWindow")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            DragTarget(
                modifier = Modifier.size(130.dp),
                dataToDrop = foodItem,
                changingOffset = { it, ondrag -> changingOffset(it, ondrag) },
            ) {
                Image(
                    painter = painterResource(id = foodItem.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = foodItem.name,
                    fontSize = 22.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$${foodItem.price}",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}
