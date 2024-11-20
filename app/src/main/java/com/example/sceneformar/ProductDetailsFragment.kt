package com.example.sceneformar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProductDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)

        val nameText = view.findViewById<TextView>(R.id.txtOneD)
        val priceText = view.findViewById<TextView>(R.id.txtTwoD)
        val warrantyText = view.findViewById<TextView>(R.id.txtThreeD)
        val ratingText = view.findViewById<TextView>(R.id.txtFourD)
        val photoImage = view.findViewById<ImageView>(R.id.photoD)
        val buttonAr = view.findViewById<Button>(R.id.buttonOneD)
        val buttonBuyNow = view.findViewById<Button>(R.id.buttonTwoD)

        // Get arguments passed to this fragment
        val photo = arguments?.getInt("photo")
        val name = arguments?.getString("name") ?: "Unknown Product"
        val rating = arguments?.getString("rating") ?: "No Rating"
        val price = arguments?.getString("price") ?: "Not Available"
        val warranty = arguments?.getString("warranty") ?: "No Warranty"

        // Update UI with product details
        photo?.let { photoImage.setImageResource(it) }
        nameText.text = "Product name: $name"
        priceText.text = "Price: $price"
        ratingText.text = "Ratings: $rating"
        warrantyText.text = "Warranty: $warranty"

        // Handle "View in AR" button click
        buttonAr.setOnClickListener {
            val intent = Intent(activity, ARActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        // Handle "Buy Now" button click
        buttonBuyNow.setOnClickListener {
            val intent = Intent(requireContext(), OrderPlacing::class.java)  // use `requireContext()` to avoid null issues
            startActivity(intent)
        }

        return view
    }
}
