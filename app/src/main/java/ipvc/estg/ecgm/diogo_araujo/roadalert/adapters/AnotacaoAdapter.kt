package ipvc.estg.ecgm.diogo_araujo.roadalert.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.ecgm.diogo_araujo.roadalert.CellClickListener
import ipvc.estg.ecgm.diogo_araujo.roadalert.EditarAnotacao
import ipvc.estg.ecgm.diogo_araujo.roadalert.R
import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao
import ipvc.estg.ecgm.diogo_araujo.roadalert.viewModel.AnotacaoViewModel
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class AnotacaoAdapter internal constructor(
    context: Context, private val cellClickListener: CellClickListener
    ) : RecyclerView.Adapter<AnotacaoAdapter.AnotacaoViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var anotacoes = emptyList<Anotacao>()
    private lateinit var intent: Intent

    class AnotacaoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val anotacaoTitulo: TextView = itemView.findViewById(R.id.AnotacaoTitulo)
        val anotacaoDescricao: TextView = itemView.findViewById(R.id.AnotacaoDescricao)
        val anotacaoAtualizada: TextView = itemView.findViewById(R.id.AnotacaoAtualizada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnotacaoAdapter.AnotacaoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return AnotacaoViewHolder(itemView)
    }

    override fun getItemCount() = anotacoes.size

    override fun onBindViewHolder(holder: AnotacaoViewHolder, position: Int){
        val current = anotacoes[position]
        holder.anotacaoTitulo.text = current.titulo
        holder.anotacaoDescricao.text = current.descricao
        //holder.anotacaoAtualizada.text = current.atualizada
        val strs = current.atualizada?.split("T")!!.toTypedArray()
        if(strs.size > 1) {
            val stt = strs[0]?.split("-")!!.toTypedArray()
            //val Rnow = LocalDateTime.now().toString()
                //val strsRN = Rnow?.split("T")!!.toTypedArray()
                //val sttRN = strs[1]?.split(":")!!.toTypedArray()
            holder.anotacaoAtualizada.text = stt[2]+"-"+stt[1]+"-"+stt[0]
        }

        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(current)
        }
    }

    internal fun setAnotacoes(anotacoes: List<Anotacao>){
        this.anotacoes = anotacoes
        notifyDataSetChanged()
    }

}