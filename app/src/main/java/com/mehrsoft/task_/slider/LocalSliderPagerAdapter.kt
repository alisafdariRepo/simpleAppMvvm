package com.example.moviemvvm.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.mehrsoft.task_.R
import com.mehrsoft.task_.slider.Slide
import com.squareup.picasso.Picasso

class LocalSliderPagerAdapter(private val context: Context, private val mList: List<Slide>) :
    PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.slide_item, null)

        val imageView: ImageView =view.findViewById(R.id.slide_img)
        val textView: TextView =view.findViewById(R.id.slide_title)

        textView.visibility=View.GONE
        Picasso.get().load(mList[position].img).into(imageView);



        container.addView(view)

        return view

    }
}