package com.example.bookkaro.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_review.view.*

data class Review(
        val reviewerName: String,
        val reviewerIcon: String?,
        val reviewContent: String?,
        val reviewRating: Long
)

class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textName: TextView = view.reviewer_name_text
    val imageUser: ImageView = view.reviewer_icon_image
    val textContent: TextView = view.review_content_text
    val textRating: TextView = view.review_rating_text
}

class ReviewAdapter(private val items: List<Review>, private val context: Context) : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = items[position]
        if (!review.reviewerIcon.isNullOrEmpty())
            Picasso.get().load(review.reviewerIcon).fit().into(holder.imageUser)
        holder.textName.text = review.reviewerName
        holder.textContent.text = review.reviewContent
        val rating = "${review.reviewRating}/5"
        holder.textRating.text = rating
    }

}