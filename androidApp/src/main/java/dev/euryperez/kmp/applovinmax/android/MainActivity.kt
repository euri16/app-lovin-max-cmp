package dev.euryperez.kmp.applovinmax.android

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.euryperez.kmp.applovinmax.Greeting
import dev.euryperez.kmp.applovinmax.ads.components.banner.AppLovinBanner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.safeDrawingPadding(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        var showBannerAd by remember { mutableStateOf(false) }
                        GreetingView(Greeting().greet())

                        Button(onClick = { showBannerAd = true }) {
                            Text(text = "Load Banner Ad")
                        }

                        if(showBannerAd) {
                            AppLovinBanner("YOUR_AD_UNIT_ID", onAdEvent = {
                                Log.d("AppLovinBanner", "Ad Event: $it")
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
