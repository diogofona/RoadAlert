package ipvc.estg.ecgm.diogo_araujo.roadalert

import ipvc.estg.ecgm.diogo_araujo.roadalert.entities.Anotacao

interface CellClickListener {
    fun onCellClickListener(anotacao: Anotacao)
}