package dev.mizoguche.beziercurvedimageview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import dev.mizoguche.beziercurvedimageview.ui.BezierCurvedImageViewTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BezierCurvedImageViewTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AvatarList()
                }
            }
        }
    }
}

@Composable
fun Avatar(url: String) {
    Image(
        rememberGlidePainter(
            request = url,
            fadeIn = true,
        ),
        null,
        modifier = Modifier
            .width(64.dp)
            .height(64.dp)
            .padding(4.dp)
            .clip(
                shape = object : Shape {
                    override fun createOutline(
                        size: Size,
                        layoutDirection: LayoutDirection,
                        density: Density
                    ): Outline {
                        val k = 0.75f
                        val path = Path()
                        val handleX = size.width * k / 2
                        val handleY = size.height * k / 2
                        val left = Pair(0f, size.height / 2)
                        val top = Pair(size.width / 2, 0f)
                        val right = Pair(size.width, size.height / 2)
                        val bottom = Pair(size.width / 2, size.height)
                        path.moveTo(left.first, left.second)
                        path.cubicTo(
                            x1 = left.first,
                            y1 = left.second - handleY,
                            x2 = top.first - handleX,
                            y2 = top.second,
                            x3 = top.first,
                            y3 = top.second,
                        )
                        path.cubicTo(
                            x1 = top.first + handleX,
                            y1 = top.second,
                            x2 = right.first,
                            y2 = right.second - handleY,
                            x3 = right.first,
                            y3 = right.second,
                        )
                        path.cubicTo(
                            x1 = right.first,
                            y1 = right.second + handleY,
                            x2 = bottom.first + handleX,
                            y2 = bottom.second,
                            x3 = bottom.first,
                            y3 = bottom.second,
                        )
                        path.cubicTo(
                            x1 = bottom.first - handleX,
                            y1 = bottom.second,
                            x2 = left.first,
                            y2 = left.second + handleY,
                            x3 = left.first,
                            y3 = left.second,
                        )
                        path.close()
                        return Outline.Generic(path)
                    }
                },
            ),
    )
}

@Composable
fun AvatarList() {
    Column {
        LazyColumn {
            items(30) {
                Row {
                    Avatar(url = "https://dummyimage.com/128x128//0f8/000?text=${it * 5 + 1}")
                    Avatar(url = "https://dummyimage.com/128x128//0f8/000?text=${it * 5 + 2}")
                    Avatar(url = "https://dummyimage.com/128x128//0f8/000?text=${it * 5 + 3}")
                    Avatar(url = "https://dummyimage.com/128x128//0f8/000?text=${it * 5 + 4}")
                    Avatar(url = "https://dummyimage.com/128x128//0f8/000?text=${it * 5 + 5}")
                }
            }
        }
    }
}

// Can't preview because of render problem when Glide initialization
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BezierCurvedImageViewTheme {
        AvatarList()
    }
}