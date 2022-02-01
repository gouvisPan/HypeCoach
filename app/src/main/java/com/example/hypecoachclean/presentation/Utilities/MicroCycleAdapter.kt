package com.example.hypecoachclean.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hypecoachclean.R
import com.example.hypecoachclean.data.POJOs.Session
import com.example.hypecoachclean.databinding.SessionTableRowBinding
import com.example.hypecoachclean.presentation.Utilities.ListAction


class MicroCycleAdapter (
    val sessions: ArrayList<Session>,
    val actions: ListAction

) : RecyclerView.Adapter<MicroCycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = SessionTableRowBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSessions(theSessions: ArrayList<Session>){
        sessions.clear()
        sessions.addAll(theSessions)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sessions[position],position.toLong())
    }



    override fun getItemCount(): Int {
        return sessions.size
    }



    inner class ViewHolder(val binding: SessionTableRowBinding) : RecyclerView.ViewHolder(binding.root){


        val tvSessionName = binding.tvSessionName
        val cvSessionTableRow = binding.cvSessionTableRow
        val layout = binding.sessionRowLayout

        fun bind(session: Session, position: Long){
            tvSessionName.text = session.name

            if(session.isDone()){
                cvSessionTableRow.setBackgroundResource(R.drawable.next_session)
            }else {
                cvSessionTableRow.setBackgroundResource(R.drawable.not_this_session_bg)
            }
            layout.setOnClickListener { actions.onClick(position) }
        }
    }

}