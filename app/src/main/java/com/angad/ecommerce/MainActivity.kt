package com.angad.ecommerce

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angad.ecommerce.databinding.ActivityMainBinding
import com.angad.ecommerce.ui.adapter.ImageSliderAdapter
import com.angad.ecommerce.ui.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //    Creating an instance of binding
    private lateinit var binding: ActivityMainBinding

    //    Initialised the viewModel
    private val viewModel: ProductViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        Initialised the binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Call the ViewModel function to fetch product details
        viewModel.fetchProductDetails("6701", "253620")

        // Collect state from ViewModel
        lifecycleScope.launch {
            viewModel.getProductDetails.collectLatest { state ->
                if (state.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else if (state.error != null) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, state.error, Toast.LENGTH_SHORT).show()
                } else{
                    binding.progressBar.visibility = View.GONE
                    val productResponse = state.data
//                    binding..text = productResponse?.data.toString()
                    Log.d(TAG, "onCreate: ${state.data}")

                    val imageUrls = productResponse?.data?.images ?: emptyList()
                    val imageAdapter = ImageSliderAdapter(imageUrls)
                    binding.viewPagerImages.adapter = imageAdapter
                    binding.dotsIndicator.setViewPager2(binding.viewPagerImages)

                    binding.tvBrand.text = productResponse?.data?.brand_name
                    binding.tvPrice.text = productResponse?.data?.price
                    binding.tvProductName.text = productResponse?.data?.name
                    binding.skuText.text = "SKU: ${productResponse?.data?.sku}"


                    binding.tvProductInfo.text = Html.fromHtml(productResponse?.data?.description,Html.FROM_HTML_MODE_COMPACT)

                }
            }
        }
    }
}