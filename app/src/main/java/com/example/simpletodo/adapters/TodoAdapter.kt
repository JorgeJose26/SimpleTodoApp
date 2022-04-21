package com.example.simpletodo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.data.Todo
import com.example.simpletodo.fragments.TodoFragment

class TodoAdapter(context: Context?, todoList: List<Todo>, todoFragment: TodoFragment) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {
    private lateinit var todoList: List<Todo>
    private lateinit var todoContext : Context
    private lateinit var clickListener: OnItemClickListener
    private var ITEM_KEY = "item_key"

    fun TodoAdapter(context: Context,todoList: List<Todo>,clickListener: OnItemClickListener) {
        this.todoContext = context
        this.todoList = todoList
        this.clickListener = clickListener
    }
    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var titleText: TextView
        lateinit var mView : View
        fun MyViewHolder (itemView : View) {
            titleText = itemView.findViewById(R.id.todoTitle)
            mView = itemView

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.todo_items,parent,false)
        return MyViewHolder(itemView)

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var todo:Todo = todoList.get(position)
        holder.titleText.text = todo.title
        holder.mView.setOnClickListener(/* l = */ View.OnClickListener() {
            fun onClick(v: View) {
                clickListener.onItemClick(todoList.get(position))
            }
            })


    }

    override fun getItemCount(): Int {
       return todoList.size
    }


}

    fun OnItemClickListener.onItemClick(todo: Todo) {

}
