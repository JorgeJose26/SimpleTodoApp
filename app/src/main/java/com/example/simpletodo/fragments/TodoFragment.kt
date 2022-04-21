package com.example.simpletodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.adapters.TodoAdapter
import com.example.simpletodo.data.Todo


class TodoFragment() : Fragment(){
    companion object{
        fun newInstance(todo:Int):TodoFragment{
            val args = Bundle()
            args.putInt("todd",todo)
            val fragment = TodoFragment()
            fragment.arguments = args
            return fragment

        }
    }
    private var todoList : List<Todo> = ArrayList()
    private lateinit var addButton: Button
    private lateinit var editText: EditText
    private lateinit var recyclerView:RecyclerView
    private val ARG_TODO : String = "Todo"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        addButton = addButton.findViewById(R.id.addButton)
        editText = editText.findViewById(R.id.todoEditText)
        recyclerView = recyclerView.findViewById(R.id.titleNameList)
        return inflater.inflate(R.layout.fragment_todo, container, false)

    }
    private fun setAdapter(adapter: TodoAdapter) {
        val adapter:TodoAdapter = TodoAdapter(context,todoList,this)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

    }
    private fun createListView(){
        addButton.setOnClickListener(View.OnClickListener {
            fun onClick(v:View){
                val str :String = editText.text.toString()
                val todo:Todo = Todo


            }
        })
    }



}




