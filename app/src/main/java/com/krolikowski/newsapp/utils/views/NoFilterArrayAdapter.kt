package com.krolikowski.newsapp.utils.views

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class NoFilterArrayAdapter : ArrayAdapter<Any?> {

    constructor(context: Context, resource: Int) : super(context, resource)
    constructor(context: Context, resource: Int, objects: List<Any?>) : super(
        context,
        resource,
        objects
    )

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                return null
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
        }
    }
}