package com.hootsuite.krrxd

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.hootsuite.krrxd.persistence.User

/**
 * Basic adapter for a [RecyclerView] representing [User] objects.
 */
class CurrentUsersRecyclerAdapter(val context: Context) : RecyclerView.Adapter<CurrentUsersRecyclerAdapter.CurrentUserViewHolder>() {

    // kotlin: setter
    var users = listOf<User>()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    // kotlin: property access syntax
    override fun onBindViewHolder(holder: CurrentUserViewHolder, position: Int) {
        holder.textView.text = context.getString(R.string.user_display, users[position].userName, users[position].password)
    }

    // kotlin: single expression function
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrentUserViewHolder(TextView(parent.context))

    // kotlin: type inference
    override fun getItemCount() = users.size

    /**
     * [RecyclerView.ViewHolder] class for this [CurrentUsersRecyclerAdapter] that holds a [TextView]
     */
    inner class CurrentUserViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}